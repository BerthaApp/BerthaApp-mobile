package com.example.BerthaApp.Challenges;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.BerthaApp.Pattern.Singleton;
import com.example.BerthaApp.R;


import static com.facebook.FacebookSdk.getApplicationContext;


//Fragment of GLOBAL CHALLENGES TAB
public class AllChallengesTab extends Fragment{

    private ListView allChallenge_listView;

    private Singleton singleton = Singleton.getInstance(getApplicationContext());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_challenges_allchallenges, container, false);

        allChallenge_listView = rootView.findViewById(R.id.listview_allChallenges);

        Log.e("LEN",String.valueOf(singleton.getList_challenges().size()));

        Challenges_adapter adapter = new Challenges_adapter(getApplicationContext(), R.layout.challenge_item,singleton.getList_challenges());
        allChallenge_listView.setAdapter(adapter);

        allChallenge_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Challenges challenges = (Challenges) allChallenge_listView.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(),ChallengeDescription.class);
                intent.putExtra("id_challenge",challenges.getId());
                startActivity(intent);
            }
        });
        return rootView;
    }
}
