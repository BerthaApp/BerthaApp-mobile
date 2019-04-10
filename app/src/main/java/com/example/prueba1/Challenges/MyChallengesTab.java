package com.example.prueba1.Challenges;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        Challenges_adapter adapter = new Challenges_adapter(getApplicationContext(), R.layout.challenge_item,singleton.getList_challenges());
        allChallenge_listView.setAdapter(adapter);
        return rootView;
    }
}
