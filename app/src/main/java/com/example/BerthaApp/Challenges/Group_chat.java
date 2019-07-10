package com.example.BerthaApp.Challenges;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.BerthaApp.Constants;
import com.example.BerthaApp.EmailSender.GMailSender;
import com.example.BerthaApp.Login.MainActivity;
import com.example.BerthaApp.Pattern.Singleton;
import com.example.BerthaApp.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.acl.Group;
import java.util.ArrayList;


//Chat of the group
public class Group_chat extends AppCompatActivity {

    private final String TAG = "Group_Chat";

    private TextView name_group;

    private Toolbar toolbar;

    private ImageButton add_people;

    ArrayList<Integer> list_id_challengesLinked = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        list_id_challengesLinked.clear();

        final ArrayList<Challenges> list_challengesTemp = Singleton.getInstance(this).getList_challenges();

        String name = getIntent().getStringExtra("name_group");
        int id_group = getIntent().getIntExtra("id_group",-1);

        name_group = findViewById(R.id.name_group);

        add_people = findViewById(R.id.add_people_toolbar);
        if(name != null && id_group != -1) {
            name_group.setText(name);
            get_groupChallenges(String.valueOf(id_group));
        }
        toolbar = findViewById(R.id.toolbar_chat);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        add_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,TAG);
                add_people_dialog();
            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_challenges = "Challenges en este grupo: \n";
                for(Integer i : list_id_challengesLinked){
                    name_challenges += Singleton.getInstance(getApplicationContext()).getChallengeById(i).getName() + "\n";
                }
                Log.e(TAG,"TOOLBAR");
                Toast.makeText(Group_chat.this, name_challenges, Toast.LENGTH_SHORT).show();
            }
        });



    }


    ArrayList<String> list_emailInvitation = new ArrayList<>();
    public void add_people_dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ingrese el correo:");

        // Set up the input
        final EditText input = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Mandar invitación", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ( ! (input.getText().toString().equals(""))) {
                    list_emailInvitation.clear();
                    //go on here and dismiss dialog
                    String m_Text = input.getText().toString();
                    list_emailInvitation.add(m_Text);
                    GMailSender gMailSender = new GMailSender(getApplicationContext(),list_emailInvitation, Constants.SUBJECT_EMAIL,Constants.BODY_EMAIL);
                    gMailSender.execute();

                }

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void get_groupChallenges(final String id_group){
        list_id_challengesLinked.clear();
        String url_get_challenges = "https://evening-oasis-22037.herokuapp.com/challenges/get_challengesxgroup/";
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url_get_challenges + id_group,null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, "onResponse: "+response );

                         try {
                             int len = response.length();
                             for(int i = 0; i<len;i++){
                                 JSONObject jsonObject = response.getJSONObject(i);
                                 int id_challenge = jsonObject.getInt("FK_id_challenge");
                                 list_id_challengesLinked.add(id_challenge);
                                 Log.e(TAG, "onResponse: "+id_challenge );
                             }

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: Null response" );
                //Toast.makeText(getApplicationContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
            }


        });

// Add the request to the RequestQueue.
        Singleton.getInstance(Group_chat.this).addToRequestQueue(stringRequest);
    }


}
