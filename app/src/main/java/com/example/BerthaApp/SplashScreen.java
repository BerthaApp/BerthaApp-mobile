package com.example.BerthaApp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.example.BerthaApp.Login.MainActivity;
import com.example.BerthaApp.Pattern.Singleton;
import com.example.BerthaApp.StartDrive.Main4Activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME = 2200;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String is_Logged = "isLogged";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Singleton.getInstance(this).default_data();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Boolean isLogged = sharedPreferences.getBoolean(is_Logged, false);

        if (isLogged) {
            Intent intent = new Intent(SplashScreen.this, Main4Activity.class);
            startActivity(intent);
            finish();
        } else {



            Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_green));

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

        }
    }




}
