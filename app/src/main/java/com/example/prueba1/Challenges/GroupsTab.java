package com.example.prueba1.Challenges;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prueba1.Pattern.Singleton;
import com.example.prueba1.R;

import static com.facebook.FacebookSdk.getApplicationContext;


public class GroupsTab extends Fragment{

    private FloatingActionButton float_plus,float_add_group,float_linkChallenge, float_all_challenges;

    private Animation fab_open, fab_close, fab_clockwise, fab_anticlockwise;

    private boolean isOpen = false;

    private Singleton singleton = Singleton.getInstance(getApplicationContext());

    private ListView listView_groups;

    private TextView create_gruopTv, link_challengeTv, all_challengesTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_challenges_groups, container, false);

        listView_groups = rootView.findViewById(R.id.groups_listview);

        create_gruopTv = rootView.findViewById(R.id.create_gruopTv);
        link_challengeTv = rootView.findViewById(R.id.link_challengeTv);


        float_plus = rootView.findViewById(R.id.fab_button_challenge);
        float_add_group = rootView.findViewById(R.id.fab_button_create_group);
        float_linkChallenge = rootView.findViewById(R.id.fab_button_link_challenges);


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
                Intent intent = new Intent(getActivity(),Select_friends.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        float_linkChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),link_challenges.class);
                startActivity(intent);
                getActivity().finish();
            }
        });



        Log.e("LENGTH LIST GROUPS",String.valueOf(singleton.getList_groups().size()));





        Groups_adapter adapter = new Groups_adapter(getActivity(), R.layout.group_item,singleton.getList_groups());
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
        return rootView;
    }
}
