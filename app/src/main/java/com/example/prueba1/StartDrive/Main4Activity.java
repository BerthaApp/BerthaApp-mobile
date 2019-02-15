package com.example.prueba1.StartDrive;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.prueba1.R;
import com.example.prueba1.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class Main4Activity extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 2;


    private Context mContext = Main4Activity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", "onCreate: starting.");
        setContentView(R.layout.activity_main4);
        Log.e("TAG", "onCreate: starting.1324");
        setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){

        Log.e("TAG", "onCreate: starting.22");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

        //Intent intent1 = new Intent(Main4Activity.this, Main4Activity.class);
        //startActivity(intent1);
    }
}
