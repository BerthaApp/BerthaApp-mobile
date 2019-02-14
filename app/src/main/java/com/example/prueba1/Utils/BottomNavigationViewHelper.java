package com.example.prueba1.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.prueba1.ActivitiesMenu.ChallengesActivity;
import com.example.prueba1.ActivitiesMenu.FuelControlActivity;
import com.example.prueba1.ActivitiesMenu.ProfileActivity;
import com.example.prueba1.ActivitiesMenu.TripLogActivity;
import com.example.prueba1.Main4Activity;
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
                    case R.id.ic_profile:
                        Intent intent = new Intent(context, ProfileActivity.class);
                        context.startActivity(intent);
                        break;
                    case R.id.ic_challenges:
                        Intent intent2 = new Intent(context, ChallengesActivity.class);
                        context.startActivity(intent2);
                        break;
                    case R.id.ic_fuelControl:
                        Intent intent3 = new Intent(context, FuelControlActivity.class);
                        context.startActivity(intent3);
                        break;
                    case R.id.ic_tripLog:
                        Intent intent4 = new Intent(context, TripLogActivity.class);
                        context.startActivity(intent4);
                        break;
                    case R.id.ic_startDrive:
                        Intent intent5 = new Intent(context, Main4Activity.class);
                        context.startActivity(intent5);
                        break;
                }
                return false;
            }
        });
    }

}
