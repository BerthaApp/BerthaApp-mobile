package com.example.BerthaApp.RegisterUser;

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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.BerthaApp.Login.MainActivity;
import com.example.BerthaApp.Pattern.Singleton;
import com.example.BerthaApp.R;
import com.example.BerthaApp.Utils.PasswordUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {


    private static final String TAG = "Login Activity ";

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
                    /*Toast.makeText(Main2Activity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(Main2Activity.this, "Ha habido un problema registrando el usuario", Toast.LENGTH_SHORT).show();
                    full_name.setText("");
                    email.setText("");
                    phone_number.setText("");
                    password.setText("");

                }*/
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void check_fields(String full_name, String email, String phone_number, String password){
        String[] name = null;
        
        if(full_name.equals("") || email.equals("") || password.equals("")){
            Toast.makeText(this, "Por favor complete los datos", Toast.LENGTH_SHORT).show();

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
            

            finding_email(params);

        }
    }

    private String url_get_user = "https://evening-oasis-22037.herokuapp.com/users/user/";
    
    private static final int EMAIL_PARAM_INDEX = 2;
    public void finding_email(final String[] params){

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url_get_user + params[EMAIL_PARAM_INDEX],null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if(response.getString("Response").equals("Not found!")){
                                postUser(params);
                                Toast.makeText(Main2Activity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(Main2Activity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: Null response" );
                //Toast.makeText(getApplicationContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        Singleton.getInstance(Main2Activity.this).addToRequestQueue(stringRequest);

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
