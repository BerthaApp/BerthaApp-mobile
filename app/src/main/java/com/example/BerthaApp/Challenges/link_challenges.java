package com.example.BerthaApp.Challenges;

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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.BerthaApp.Pattern.Singleton;
import com.example.BerthaApp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


//This class is in charge
public class link_challenges extends AppCompatActivity {

    private static final String TAG = "link_challenges";


    private Spinner spinner_challenges;

    private ListView listView_gruops;

    ArrayList<String> list_temp = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_challenges);

        list_temp.clear();

        spinner_challenges = findViewById(R.id.spinner_challengeList);
        listView_gruops = findViewById(R.id.listView_groups);
        Button link_challengeBtn = findViewById(R.id.button_linkChallenge);

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
                set_link_challenges();

            }
        });
    }

    public void set_link_challenges() {

        JSONObject jsonObject=new JSONObject();

        int challenge_selected_id = Singleton.getInstance(this).getList_challenges().get(spinner_challenges.getSelectedItemPosition()).getId();

        int len = listView_gruops.getCount();
        SparseBooleanArray checked = listView_gruops.getCheckedItemPositions();
        String[] params = new String[2];
        params[0] = String.valueOf(challenge_selected_id);
        int count = 0;
        try {
            jsonObject.put("id_challenge",challenge_selected_id);
            for (int i = 0; i < len; i++) {
                if (checked.get(i)){
                    int id_group = Singleton.getInstance(this).getList_groups().get(i).getId();
                    jsonObject.put("id_group"+count,id_group);
                    count++;
                    Log.e(TAG, "set_link_challenges: "+params[1] );

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        post_link(jsonObject);

        /*Intent intent = new Intent(link_challenges.this,ChallengesFragment.class);
        startActivity(intent);
        finish();*/

    }


    public void post_link(final JSONObject jsonObject){

        String url = "https://evening-oasis-22037.herokuapp.com/challenges/link_group_challenge/";


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected HashMap<String, String> getParams()
            {
                HashMap<String ,String> params=new HashMap<>();
                params.put("params",jsonObject.toString());
                Log.e(TAG, "getParams: "+jsonObject );
                /*Map<String, String> params = new HashMap<>();
                Log.e(TAG, "getParams: "+arg0[1] );
                params.put("id_challenge", arg0[0]);
                params.put("id_group", arg0[1]);
*/
                return params;
            }


        };
        Singleton.getInstance(link_challenges.this).addToRequestQueue(postRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(link_challenges.this, ChallengesFragment.class);
        startActivity(intent);
        finish();
    }
}
