package com.example.prueba1.Challenges;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.prueba1.Login.MainActivity;
import com.example.prueba1.Pattern.Singleton;
import com.example.prueba1.R;
import com.example.prueba1.RegisterUser.Main2Activity;

import java.util.HashMap;
import java.util.Map;

public class ChallengeDescription extends AppCompatActivity {

    private TextView challenge_title, challenge_description,challenge_tokens,challenge_routeFrom,challenge_routeTo,challenge_expirationDate, challenge_scoreMin;
    private FloatingActionButton floatingActionButton_addChallenge, floatingActionButton_dropChallenge;

    private static final String TAG = "Challenge_Description";

    private Challenges challenge = null;

    private String id_userLocal = "";

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String id_user = "id_user";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_description);

        challenge_title = findViewById(R.id.challenge_name);
        challenge_description = findViewById(R.id.description_challenge);
        challenge_tokens = findViewById(R.id.textViewNumberTokens);
        challenge_routeFrom = findViewById(R.id.textView_departure);
        challenge_routeTo = findViewById(R.id.textView_destination);
        challenge_expirationDate = findViewById(R.id.textViewExpirationDate);
        challenge_scoreMin = findViewById(R.id.textView_scoremin);

        floatingActionButton_addChallenge = findViewById(R.id.fab_button_addChallenge);
        floatingActionButton_dropChallenge = findViewById(R.id.fab_button_dropChallenge);

        int id =  getIntent().getIntExtra("id_challenge",0);
        challenge = Singleton.getInstance(ChallengeDescription.this).getChallengeById(Integer.valueOf(id));

        if(challenge.isMyChallenge()){
            floatingActionButton_dropChallenge.setVisibility(View.VISIBLE);
        }else{
            floatingActionButton_addChallenge.setVisibility(View.VISIBLE);
        }
        Log.e(TAG,TAG);
        if(challenge != null){
            challenge_title.setText(challenge.getName());
            challenge_description.setText(challenge.getDescription());
            challenge_tokens.setText(String.valueOf(challenge.getTokens()));
            challenge_routeFrom.setText(challenge.getRoute_from());
            challenge_routeTo.setText(challenge.getRoute_to());
            challenge_expirationDate.setText(challenge.getEnd_date());
            challenge_scoreMin.setText(String.valueOf(challenge.getScore_min()));
        }
        else{
            Toast.makeText(this, "Reto no existente", Toast.LENGTH_SHORT).show();
            finish();
        }

        floatingActionButton_addChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ChallengeDescription.this)
                        .setTitle("Agregar reto")
                        .setMessage("Desea ingresar al reto?")
                        .setIcon(R.drawable.group40x)
                        .setPositiveButton("Sí",
                                new DialogInterface.OnClickListener() {
                                    @TargetApi(11)
                                    public void onClick(DialogInterface dialog, int id) {
                                        Toast.makeText(ChallengeDescription.this, "Reto agregado", Toast.LENGTH_SHORT).show();
                                        addChallenge();
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(ChallengeDescription.this, "Ok :(", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        }).show();
            }
        });

        floatingActionButton_dropChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ChallengeDescription.this)
                        .setTitle("Salirse de reto")
                        .setMessage("Desea salirse del reto actual?")
                        .setIcon(R.drawable.group40x)
                        .setPositiveButton("Sí",
                                new DialogInterface.OnClickListener() {
                                    @TargetApi(11)
                                    public void onClick(DialogInterface dialog, int id) {
                                        Toast.makeText(ChallengeDescription.this, "Reto borrado", Toast.LENGTH_SHORT).show();
                                        dropChallenge();
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(ChallengeDescription.this, "Sigue intentando :)", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        }).show();
            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        id_userLocal = sharedPreferences.getString(id_user,"");
    }

    private static final String url_dropChallenge = "https://evening-oasis-22037.herokuapp.com/challenges/drop_challenge/";
    public void dropChallenge(){
        Log.e(TAG ,"Drop Challenge");
        challenge.setMyChallenge(false);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url_dropChallenge,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response Drop Challenge", response);
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
            protected Map<String, String> getParams()
            {
                Log.e(TAG + "id",String.valueOf(challenge.getId()));
                Log.e(TAG + "id",id_userLocal);
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_challenge", String.valueOf(challenge.getId()));
                params.put("id_user", id_userLocal);

                return params;
            }
        };
        Singleton.getInstance(ChallengeDescription.this).addToRequestQueue(postRequest);
        finish();
    }

    private static final String url_link = "https://evening-oasis-22037.herokuapp.com/challenges/link_challenge/";
    public void addChallenge(){

        Log.e(TAG ,"Add Challenge");

        challenge.setMyChallenge(true);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url_link,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response Link", response);
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
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user",id_userLocal);
                params.put("id_challenge", String.valueOf(challenge.getId()));

                return params;
            }
        };
        Singleton.getInstance(ChallengeDescription.this).addToRequestQueue(postRequest);


        finish();
    }
}
