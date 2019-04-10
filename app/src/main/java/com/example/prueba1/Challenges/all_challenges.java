package com.example.prueba1.Challenges;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.prueba1.Pattern.Singleton;
import com.example.prueba1.R;

public class all_challenges extends AppCompatActivity {

    private ListView allChallenge_listView;

    private Singleton singleton = Singleton.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_challenges);

        allChallenge_listView = findViewById(R.id.listview_allChallenges);

        Log.e("LEN",String.valueOf(singleton.getList_challenges().size()));

        Challenges_adapter adapter = new Challenges_adapter(this, R.layout.challenge_item,singleton.getList_challenges());
        allChallenge_listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(all_challenges.this,ChallengesActivity.class);
        startActivity(intent);
        finish();
    }
}
