package com.example.prueba1.Challenges;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.prueba1.Pattern.Singleton;
import com.example.prueba1.R;

public class link_challenges extends AppCompatActivity {


    private Spinner spinner_challenges;

    private ListView listView_gruops;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_challenges);

        spinner_challenges = findViewById(R.id.spinner_challengeList);
        listView_gruops = findViewById(R.id.listView_groups);

        ArrayAdapter<String> adapter_challenges = new ArrayAdapter<>(this,R.layout.spinner_item, Singleton.getInstance(link_challenges.this).return_challenges_name());
        adapter_challenges.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_challenges.setAdapter(adapter_challenges);

        ArrayAdapter<String> list_groupsAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_multiple_choice,Singleton.getInstance(link_challenges.this).return_groups_name());
        list_groupsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listView_gruops.setAdapter(list_groupsAdapter);

        listView_gruops.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


    }
}
