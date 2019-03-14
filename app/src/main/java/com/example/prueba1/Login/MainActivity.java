package com.example.prueba1.Login;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba1.RegisterUser.Main2Activity;
import com.example.prueba1.RegisterCar.Main3Activity;
import com.example.prueba1.R;
import com.example.prueba1.StartDrive.Main4Activity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Button login_btn;
    private Button sign_upBtn;
    private EditText username, password;

    private CallbackManager callbackManager;

    private LoginButton login_btn_fb;
    //URL API


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Buttons linked to layout

        login_btn = findViewById(R.id.login_btn);
        sign_upBtn = findViewById(R.id.sign_upBtn);

        username = findViewById(R.id.email_edit);
        password = findViewById(R.id.password_edit);

        /*try{
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.prueba1", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:",Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/


        //spinner link



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main4Activity.class);
                startActivity(intent);
                finish();
            }
        });

        sign_upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });



        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorGreen));



        //FACEBOOK

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();

        login_btn_fb = findViewById(R.id.login_button);
        login_btn_fb.setReadPermissions(("friend_list"));



        // Callback registration
        login_btn_fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequest(loginResult.getAccessToken(), "/me/friends", null, HttpMethod.GET,
                        new GraphRequest.Callback() {
                            @Override
                            public void onCompleted(GraphResponse response) {
                                try{
                                    JSONArray rawName = response.getJSONObject().getJSONArray("data");
                                    Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        }).executeAsync();
                /*Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                AccessToken accessToken =loginResult.getAccessToken();

                GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        toast_caca(object);
                    }
                });
                Bundle bundle = new Bundle();
                bundle.putString("fields","email, id");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();*/
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

    public void toast(JSONObject object){
        Toast.makeText(this, object.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
