package com.example.prueba1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.toolbox.HttpResponse;
import com.example.prueba1.Login.MainActivity;
import com.example.prueba1.Pattern.Singleton;
import com.example.prueba1.StartDrive.Main4Activity;
import com.example.prueba1.Utils.PasswordUtils;
import com.example.prueba1.database.ConnectionSQL;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME = 2200;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String id_user = "id_user";
    public static final String id_car = "id_car";
    public static final String is_Logged = "isLogged";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Singleton.getInstance(this).default_data();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Boolean isLogged = sharedPreferences.getBoolean(is_Logged, false);
        Log.e("looged", String.valueOf(isLogged));
        if (isLogged) {
            Intent intent = new Intent(SplashScreen.this, Main4Activity.class);
            ///intent.putExtra("idUser", sharedPreferences.getString(id_user, ""));
            ///intent.putExtra("idCar", sharedPreferences.getString(id_car, ""));
            startActivity(intent);
            finish();
        } else {

            //SQLiteDatabase sqLiteDatabase = connectionSQL.getWritableDatabase();


            Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorGreen));

            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {

                    // run() method will be executed when 3 seconds have passed

                    //Time to start MainActivity
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);

                    finish();
                }
            }, SPLASH_TIME);


            //VcxCFWS2b2BdSUT2VZszC5Rei6oPk7qNd13CRq+Gq+I=
            //VcxCFWS2b2BdSUT2VZszC5Rei6oPk7qNd13CRq+Gq+I=
//        boolean passwordMatch = PasswordUtils.verifyUserPassword("123456", "OA/SxPT+7Qwb9551cNCwLCgtrrHsG14tQoyPXLmbGLA=", "tKnn6fntME7wMXkyN7M1XrLRCqeR70");
            //      Log.e("splashuser",String.valueOf(passwordMatch));
        }
    }





/*
    class BackgroundWorker extends AsyncTask<String,String,String> {
        public BackgroundWorker(Context ctx) {
            Context context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String myurl = "https://evening-oasis-22037.herokuapp.com/user_create";
            try {
                URL url = new URL(myurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode(name, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null)
                    result += line;
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }*/
}
