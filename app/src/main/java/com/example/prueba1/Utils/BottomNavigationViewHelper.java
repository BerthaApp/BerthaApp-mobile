package com.example.prueba1.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.prueba1.Challenges.ChallengesActivity;
import com.example.prueba1.FuelControl.FuelControlActivity;
import com.example.prueba1.Profile.ProfileActivity;
import com.example.prueba1.TripLog.TripLogActivity;
import com.example.prueba1.StartDrive.Main4Activity;
import com.example.prueba1.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationBar";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.e("TAG", "onCreate: starting.33");
        bottomNavigationViewEx.enableAnimation(false);

        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
        bottomNavigationViewEx.setIconSize(35);
        bottomNavigationViewEx.setItemHeight(150);

    }

    public static void enableNavigation(final Context context,BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.ic_tripLog:
                        Intent intent = new Intent(context, TripLogActivity.class);
                        context.startActivity(intent);

                        break;
                    case R.id.ic_fuelControl:
                        Intent intent2 = new Intent(context, FuelControlActivity.class);
                        context.startActivity(intent2);
                        break;
                    case R.id.ic_startDrive:
                        Intent intent3 = new Intent(context, Main4Activity.class);
                        context.startActivity(intent3);

                        break;
                    case R.id.ic_challenges:
                        Intent intent4 = new Intent(context, ChallengesActivity.class);
                        context.startActivity(intent4);
                        break;
                    case R.id.ic_profile:
                        Intent intent5 = new Intent(context, ProfileActivity.class);
                        context.startActivity(intent5);

                        break;
                }((Activity)(context)).finish();

                return false;
            }
        });
    }

}
