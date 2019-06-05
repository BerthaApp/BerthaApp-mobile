package com.example.BerthaApp.TripLog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;

import com.example.BerthaApp.Pattern.Singleton;
import com.example.BerthaApp.R;
import com.example.BerthaApp.StartDrive.Main4Activity;
import com.example.BerthaApp.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import at.grabner.circleprogress.CircleProgressView;

public class TripLogActivity  extends AppCompatActivity {

    private static final String TAG = "TripLogActivity";

    private Context mContext = TripLogActivity.this;

    private static final int ACTIVITY_NUM = 0;

    private GridView gridView_tripLog;


    private final String[] tripLog_values = {"149","14.8","$41.2","5:34"};

    private CircleProgressView circleProgressView;

    private ListView listView_trips;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triplog);

        setupBottomNavigationView();


        gridView_tripLog = findViewById(R.id.gridview_tripLog);
        circleProgressView = findViewById(R.id.circleView);

        listView_trips = findViewById(R.id.listView_trips);


        circleProgressView.setTextColor(getResources().getColor(R.color.colorGreen));
        circleProgressView.setTextSize(50);

        circleProgressView.setValueAnimated(0,88,2000);

        Adapter_tripLog adapter_tripLog = new Adapter_tripLog(this,tripLog_values);
        gridView_tripLog.setAdapter(adapter_tripLog);

        Adapter_trips adapter_trips = new Adapter_trips(this,R.layout.adapter_trips, Singleton.getList_myTrips());
        listView_trips.setAdapter(adapter_trips);

    }

    private void setupBottomNavigationView(){

        Log.e(TAG, "setupBottomNavigationView: starting.22");
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
