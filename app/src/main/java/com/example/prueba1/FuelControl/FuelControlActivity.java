package com.example.prueba1.FuelControl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.example.prueba1.R;
import com.example.prueba1.StartDrive.Main4Activity;
import com.example.prueba1.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class FuelControlActivity extends AppCompatActivity {

    private Context mContext = FuelControlActivity.this;

    private static final int ACTIVITY_NUM = 1;

    private final String[] values_firstGridstr = {"AV L/100 km", "LAST L/100 km","BEST L/100 km","Total km Tracked","Fuel logs","Total Liters"};

    private final String[] values_firstGridint = {"6.7","7.5","6.4","1887","5","171"};

    private final String[] values_secondGridstr = {"Avg. Price/Liter", "Avg. Fuel-Up Cost","Avg. Price/km"};

    private final String[] values_secondGridint = {"$677","$23.16","$45.42"};

    private GridView gridView_stats,gridView_prices;

    private FloatingActionButton floatingActionButton_addLog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuelcontrol);

        setupBottomNavigationView();

        gridView_stats = findViewById(R.id.gridviewHorizontal);
        gridView_prices = findViewById(R.id.gridview_prices);

        floatingActionButton_addLog = findViewById(R.id.fab_button_addFuelLog);

        Items_adapter items_adapter = new Items_adapter(this,values_firstGridint,values_firstGridstr);
        gridView_stats.setAdapter(items_adapter);

        Items_adapter items_adapterPrices = new Items_adapter(this,values_secondGridint,values_secondGridstr);
        gridView_prices.setAdapter(items_adapterPrices);


        floatingActionButton_addLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FuelControlActivity.this,Fuel_log.class));
            }
        });


    }

    private void setupBottomNavigationView(){

        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Main4Activity.class);
        startActivity(intent);
        finish();
    }
}
