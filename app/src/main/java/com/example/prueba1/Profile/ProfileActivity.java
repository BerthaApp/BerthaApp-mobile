package com.example.prueba1.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.prueba1.R;
import com.example.prueba1.RegisterCar.Main3Activity;
import com.example.prueba1.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class ProfileActivity extends AppCompatActivity {

    private Context mContext = ProfileActivity.this;

    private static final int ACTIVITY_NUM = 4;

    //ListView
    private ListView listView_carList;

    //Button
    private Button button_addCar;

    private static String[] listCars = new String[] {"VW Golf","Land Cruiser", "Volkswagen","Land Cruiser", "Volkswagen","Land Cruiser", "Volkswagen","Land Cruiser", "Volkswagen"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        setupBottomNavigationView();


        listView_carList = findViewById(R.id.listView_cars);

        button_addCar = findViewById(R.id.button_newCar);


        button_addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
                startActivity(intent);
                finish();
            }
        });

        ArrayAdapter<String> list_carAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,listCars);
        list_carAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listView_carList.setAdapter(list_carAdapter);


    }

    private void setupBottomNavigationView(){

        Log.e("TAG", "onCreate: starting.22");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        Log.e("TAG", "onCreate: starting.44");
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
