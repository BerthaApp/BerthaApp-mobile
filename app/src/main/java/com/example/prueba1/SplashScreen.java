package com.example.prueba1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.prueba1.Login.MainActivity;
import com.example.prueba1.database.ConnectionSQL;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME = 2200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        ConnectionSQL connectionSQL = new ConnectionSQL(this,"bd_bertha1",null,1);
        //SQLiteDatabase sqLiteDatabase = connectionSQL.getWritableDatabase();


        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorGreen));

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                // run() method will be executed when 3 seconds have passed

                //Time to start MainActivity
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent );

                finish();
            }
        }, SPLASH_TIME);
    }
}
