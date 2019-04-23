package com.example.prueba1.Challenges;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.prueba1.Pattern.Singleton;
import com.example.prueba1.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyChallengesTab extends Fragment {

    private ListView allChallenge_listView;



    private Singleton singleton = Singleton.getInstance(getApplicationContext());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_challenges_mychallenges, container, false);


        allChallenge_listView = rootView.findViewById(R.id.listview_allChallenges);

        Log.e("LEN",String.valueOf(singleton.getList_challenges().size()));

        Challenges_adapter adapter = new Challenges_adapter(getApplicationContext(), R.layout.challenge_item,singleton.getList_MyChallenges());
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

    @Override
    public void onStart() {
        super.onStart();

        Challenges_adapter adapter = new Challenges_adapter(getApplicationContext(), R.layout.challenge_item,singleton.getList_MyChallenges());
        allChallenge_listView.setAdapter(adapter);

    }


}
