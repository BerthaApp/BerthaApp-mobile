package com.example.BerthaApp.Login;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.BerthaApp.Pattern.Singleton;
import com.example.BerthaApp.RegisterUser.Main2Activity;
import com.example.BerthaApp.R;
import com.example.BerthaApp.StartDrive.Main4Activity;
import com.example.BerthaApp.Utils.PasswordUtils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.clearcut.ClearcutLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button login_btn;
    private Button sign_upBtn;
    private EditText usernameEdit, passwordEdit;

    private CallbackManager callbackManager;

    private LoginButton login_btn_fb;
    //URL API



    //User and car from shared preferences

    private String id_userLogged;
    private String id_carDef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Buttons linked to layout

        login_btn = findViewById(R.id.login_btn);
        sign_upBtn = findViewById(R.id.sign_upBtn);

        usernameEdit = findViewById(R.id.email_edit);
        passwordEdit = findViewById(R.id.password_edit);
        usernameEdit.setText("maria@gmail.com");
        passwordEdit.setText("12345");


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        id_userLogged = sharedPreferences.getString(id_user,"");
        id_carDef = sharedPreferences.getString(id_car,"");

        Log.e(TAG, "onCreate: "+id_carDef );




        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email = usernameEdit.getText().toString();
                password = passwordEdit.getText().toString();
                if(checkFields(email,password)){
                    getUserRequested(email, password);
                }else{
                    Toast.makeText(MainActivity.this, "Completa ambos campos!", Toast.LENGTH_SHORT).show();
                    usernameEdit.setText("");
                    passwordEdit.setText("");

                }

            }
        });

        sign_upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        });




        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.color_green));



        //FACEBOOK

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();

        login_btn_fb = findViewById(R.id.login_button);

        login_btn_fb.setReadPermissions(Arrays.asList("email","public_profile"));


        // Callback registration
        login_btn_fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(MainActivity.this, exception.toString(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    public boolean checkFields(String user, String pass ){
        if(user.equals("") || pass.equals("")){
            return false;
        }

        return true;
    }


    private String url_get_user = "https://evening-oasis-22037.herokuapp.com/users/user/";




    public void getUserRequested(String username, final String password){

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url_get_user + username,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            String pass = response.getString("password");

                            String salt = response.getString("salt");

                            int id = response.getInt("id");

                            String id_car = response.getString("car_def_id");

                            String drive_mode_def = response.getString("drive_mode_def");


                            String generateSecure = PasswordUtils.generateSecurePassword(password,salt);

                            //boolean passwordMatch = PasswordUtils.verifyUserPassword(password, pass, salt);
                            boolean passwordMatch = generateSecure.equals(pass);
                            if(passwordMatch){
                                check_user(true,id,id_car,drive_mode_def);
                            }else{
                                check_user(false,id,id_car,drive_mode_def);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.toString() );
                Toast.makeText(getApplicationContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
            }


        });

        Singleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);

    }



    public void getUserRequestedByFb(final String username, final String first_name, final String last_name){

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url_get_user + username,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                    try {
                        if(response.getString("Response").equals("Not found!")){
                            String[] params = {first_name,last_name,username,"","",""};
                            postUser(params);


                        }else{
                            int id = response.getInt("id");

                            String id_car = response.getString("car_def_id");

                            String drive_mode_def = response.getString("drive_mode_def");

                            check_user(true,id,id_car,drive_mode_def);
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
        Singleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);

    }


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String id_user = "id_user";
    public static final String id_car = "id_car";
    public static final String is_Logged = "isLogged";
    public static final String def_driveMode = "def_driveMode";

    public void check_user(boolean isUser, int id,String id_carbd,String drive_mode_def){
        if(isUser){
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(id_user,String.valueOf(id));
            editor.putString(id_car,id_carbd);
            editor.putBoolean(is_Logged,true);
            editor.putString(def_driveMode,drive_mode_def);
            editor.apply();
            Intent intent = new Intent(getApplicationContext(), Main4Activity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(MainActivity.this, "Usuario no encontrado!", Toast.LENGTH_SHORT).show();
            usernameEdit.setText("");
            passwordEdit.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken  == null){
                Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
            }else{
                loadUserData(currentAccessToken);
            }
        }
    };

    private void loadUserData(AccessToken accessToken){
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try{
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    getUserRequestedByFb(email,first_name,last_name);
                    Log.e(TAG, "onCompleted: "+email );



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void postUser(final String[] arg0){

        String url = "https://evening-oasis-22037.herokuapp.com/users/user_create/";


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response", response);
                        getUserRequestedByFb(arg0[2],arg0[0],arg0[1]);
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
        Singleton.getInstance(MainActivity.this).addToRequestQueue(postRequest);
    }

}
