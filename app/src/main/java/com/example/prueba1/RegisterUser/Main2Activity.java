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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prueba1.Login.MainActivity;
import com.example.prueba1.Pattern.Singleton;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
            //new SendPostRequest().execute(params);
            postUser(params);
        }

        return false;
    }




    public void postUser(final String[] arg0){

        String url = "https://evening-oasis-22037.herokuapp.com/users/user_create/";


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", arg0[0]);
                params.put("last_name", arg0[1]);
                params.put("email", arg0[2]);
                params.put("phone_num", arg0[3]);
                params.put("password", arg0[4]);
                params.put("salt", arg0[5]);
                params.put("drive_mode_def", "eco");
                params.put("car_def_id", "-1");

                return params;
            }
        };
        Singleton.getInstance(Main2Activity.this).addToRequestQueue(postRequest);
    }

}
