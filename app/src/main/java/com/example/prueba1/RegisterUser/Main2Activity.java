package com.example.prueba1.RegisterUser;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prueba1.Login.MainActivity;
import com.example.prueba1.R;
import com.example.prueba1.Utils.PasswordUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class Main2Activity extends AppCompatActivity {

    private EditText full_name, email, phone_number, password;
    private Button sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorGreen));


        //link to layout

        full_name = findViewById(R.id.full_nameEdit);
        email = findViewById(R.id.email_edit);
        phone_number= findViewById(R.id.phone_numberEdit);
        password = findViewById(R.id.password_editReg);
        sign_in = findViewById(R.id.sign_inButton);


        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_fields(full_name.getText().toString(),email.getText().toString(),phone_number.getText().toString(),password.getText().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean check_fields(String full_name, String email, String phone_number, String password){
        String[] name = null;
        
        if(full_name.equals("") || email.equals("") || password.equals("")){
            Toast.makeText(this, "Por favor complete los datos", Toast.LENGTH_SHORT).show();
            return false;
        }else{

            if(full_name.contains(" ")){
                name =  full_name.split(" ");
            }else{
                name = new String[] {full_name, " "};
            }

            String salt = PasswordUtils.getSalt(30);
            String pass = PasswordUtils.generateSecurePassword(password,salt);
            String[] params = {name[0],name[1], email, phone_number, pass, salt};
            new SendPostRequest().execute(params);

        }

        return false;
    }

    public class SendPostRequest extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://evening-oasis-22037.herokuapp.com/user_create/"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("name", arg0[0]);
                postDataParams.put("last_name", arg0[1]);
                postDataParams.put("email", arg0[2]);
                postDataParams.put("phone_num", arg0[3]);
                postDataParams.put("password", arg0[4]);
                postDataParams.put("salt", arg0[5]);
                postDataParams.put("drive_mode_def", "eco");
                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    public String enccrypt_pass(String pass){
        return null;
    }
}
