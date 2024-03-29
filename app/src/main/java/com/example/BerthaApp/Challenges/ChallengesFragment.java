package com.example.BerthaApp.Challenges;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.BerthaApp.Profile.ProfileActivity;
import com.example.BerthaApp.R;
import com.example.BerthaApp.StartDrive.Main4Activity;
import com.example.BerthaApp.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;



//Fragment father of groups, my challenges and global challenges
public class ChallengesFragment extends AppCompatActivity {

    private static final String TAG = "ChallengesFragment";

    private Context mContext = ChallengesFragment.this;
    private static final int ACTIVITY_NUM = 3;

    //User and car from shared preferences
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String id_user = "id_user";
    public static final String id_car = "id_car";

    private String id_userLogged;
    private String id_carDef;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges_temp);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        id_userLogged = sharedPreferences.getString(id_user,"");
        id_carDef = sharedPreferences.getString(id_car,"");

        Log.e(TAG, "onCreate: "+id_carDef );

        if(id_carDef.equals("-1")){

            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alert_dialog);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            final Handler handler  = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                        startActivity(new Intent(ChallengesFragment.this, ProfileActivity.class));
                        finish();
                    }
                }
            };

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    handler.removeCallbacks(runnable);
                }
            });

            handler.postDelayed(runnable, 2500);

        }


        setupBottomNavigationView();

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_challenges_temp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    GroupsTab groupsTab = new GroupsTab();
                    return groupsTab;
                case 1:
                    MyChallengesTab myChallengesTab = new MyChallengesTab();
                    return myChallengesTab;
                case 2:
                    AllChallengesTab allChallengesTab = new AllChallengesTab();
                    return allChallengesTab;
                default:
                    return null;
            }
        }



        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Grupos";
                case 1:
                    return "Mis retos";
                case 2:
                    return "Retos globales";
            }
            return null;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Main4Activity.class);
        startActivity(intent);
        finish();
    }
}
