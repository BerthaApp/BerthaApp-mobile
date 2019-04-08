package com.example.prueba1.Challenges;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prueba1.Pattern.Singleton;
import com.example.prueba1.R;
import com.example.prueba1.StartDrive.Main4Activity;
import com.example.prueba1.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class ChallengesActivity extends AppCompatActivity {

    private Context mContext = ChallengesActivity.this;

    private static final int ACTIVITY_NUM = 3;

    private FloatingActionButton float_plus,float_add_group,float_linkChallenge;

    private Animation fab_open, fab_close, fab_clockwise, fab_anticlockwise;

    private boolean isOpen = false;

    private Singleton singleton = Singleton.getInstance(this);

    private ListView listView_groups;

    private TextView create_gruopTv, link_challengeTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);


        setupBottomNavigationView();

        listView_groups = findViewById(R.id.groups_listview);

        create_gruopTv = findViewById(R.id.create_gruopTv);
        link_challengeTv = findViewById(R.id.link_challengeTv);


        float_plus = findViewById(R.id.fab_button_challenge);
        float_add_group = findViewById(R.id.fab_button_create_group);
        float_linkChallenge = findViewById(R.id.fab_button_link_challenges);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        fab_clockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        fab_anticlockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);

        float_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen){
                    float_add_group.startAnimation(fab_close);
                    float_linkChallenge.startAnimation(fab_close);
                    float_plus.startAnimation(fab_anticlockwise);
                    float_add_group.setClickable(false);
                    float_linkChallenge.setClickable(false);
                    create_gruopTv.startAnimation(fab_close);
                    link_challengeTv.startAnimation(fab_close);
                    isOpen = false;
                }
                else{
                    float_add_group.startAnimation(fab_open);
                    float_linkChallenge.startAnimation(fab_open);
                    float_plus.startAnimation(fab_clockwise);
                    float_add_group.setClickable(true);
                    float_linkChallenge.setClickable(true);
                    create_gruopTv.startAnimation(fab_open);
                    link_challengeTv.startAnimation(fab_open);
                    isOpen = true;
                }
            }
        });

        float_add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChallengesActivity.this,Select_friends.class);
                startActivity(intent);
                finish();
            }
        });

        float_linkChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChallengesActivity.this,link_challenges.class);
                startActivity(intent);
                finish();
            }
        });

        Log.e("LENGTH LIST GROUPS",String.valueOf(singleton.getList_groups().size()));

        singleton.setList_groups(new Groups(0,"Nombre","Descripcion"));

        Groups_adapter adapter = new Groups_adapter(this, R.layout.group_item,singleton.getList_groups());
        listView_groups.setAdapter(adapter);


        listView_groups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Groups group = (Groups) listView_groups.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(),Group_chat.class);
                intent.putExtra("name_group",group.getName());
                startActivity(intent);
            }
        });
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Main4Activity.class);
        startActivity(intent);
        finish();
    }

    public void update_LV(){
        Groups_adapter adapter = new Groups_adapter(this, R.layout.group_item,singleton.getList_groups());
        listView_groups.setAdapter(adapter);
    }
}
