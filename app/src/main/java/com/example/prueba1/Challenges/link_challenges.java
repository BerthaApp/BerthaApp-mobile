package com.example.prueba1.Challenges;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.prueba1.Pattern.Singleton;
import com.example.prueba1.R;

import java.util.ArrayList;

public class link_challenges extends AppCompatActivity {


    private Spinner spinner_challenges;

    private ListView listView_gruops;

    private Button link_challengeBtn;
    ArrayList<String> list_temp = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_challenges);

        list_temp.clear();

        spinner_challenges = findViewById(R.id.spinner_challengeList);
        listView_gruops = findViewById(R.id.listView_groups);
        link_challengeBtn = findViewById(R.id.button_linkChallenge);

        ArrayAdapter<String> adapter_challenges = new ArrayAdapter<>(this,R.layout.spinner_item, Singleton.getInstance(link_challenges.this).return_challenges_name());
        adapter_challenges.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_challenges.setAdapter(adapter_challenges);

        list_temp = Singleton.getInstance(link_challenges.this).return_groups_name();

        ArrayAdapter<String> list_groupsAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_multiple_choice,list_temp);
        list_groupsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listView_gruops.setAdapter(list_groupsAdapter);

        listView_gruops.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        link_challengeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              link_challenges();

            }
        });
    }

    public void link_challenges() {
        Log.e("Test", "asd");
        int len = listView_gruops.getCount();
        String name = null;
        SparseBooleanArray checked = listView_gruops.getCheckedItemPositions();
        for (int i = 0; i < len; i++) {
            if (checked.get(i)){
                name = list_temp.get(i);
                Log.e("Tag",name);
            }
        }
        Intent intent = new Intent(link_challenges.this,ChallengesTemp.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(link_challenges.this,ChallengesTemp.class);
        startActivity(intent);
        finish();
    }
}
