package com.example.BerthaApp.Challenges;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.BerthaApp.Constants;
import com.example.BerthaApp.EmailSender.GMailSender;
import com.example.BerthaApp.Pattern.Singleton;
import com.example.BerthaApp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



/// Create a group
public class Select_friends extends AppCompatActivity {

    private EditText group_name;

    private Singleton singleton = Singleton.getInstance(this);

    private ListView listView_fbFriends;

    //User and car from shared preferences
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String id_user = "id_user";
    public static final String id_car = "id_car";

    private String id_userLogged;
    private String id_carDef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friends);



        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        id_userLogged = sharedPreferences.getString(id_user,"");
        id_carDef = sharedPreferences.getString(id_car,"");



        FloatingActionButton float_createGroup = findViewById(R.id.fab_button_createGroup);
        group_name = findViewById(R.id.editGroup_name);

        listView_fbFriends = findViewById(R.id.listView_friends);


        float_createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(group_name.getText().toString().equals("")){
                    Toast.makeText(context, "Por favor digite un nombre", Toast.LENGTH_SHORT).show();
                }else{
                    getData();
                }
            }
        });


        ArrayList<String> fb_friends = new ArrayList<>();
        fb_friends.add("Facebook friend");fb_friends.add("Facebook friend");fb_friends.add("Facebook friend");fb_friends.add("Facebook friend");fb_friends.add("Facebook friend");


        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fb_friends);
        listView_fbFriends.setAdapter(itemsAdapter);

    }

    private String m_Text = "";
    ArrayList<String> list_emails = new ArrayList<>();
    public void agregarCorreo(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Añadir correo");

        // Set up the input
        final EditText input = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ( ! (input.getText().toString().equals(""))) {
                    //go on here and dismiss dialog
                    m_Text = input.getText().toString();
                    list_emails.add(m_Text);
                    showstatesList();
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

    public void getData(){
        String group_nameStr =  group_name.getText().toString();
        String description_default = "Descripcion grupo...";

        if(!list_emails.isEmpty()) {
            GMailSender sender = new GMailSender(this, list_emails, Constants.SUBJECT_EMAIL, Constants.BODY_EMAIL);
            sender.execute();
        }

        String[] params = new String[4];

        params[0] = id_userLogged;
        params[1] = id_carDef;
        params[2] = group_nameStr;
        params[3] = description_default;

        postGroup(params);
    }


    final Context context = this;
    public void showstatesList() {

        LinearLayout.LayoutParams params = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        View linearLayout =  findViewById(R.id.lista);
        if(((LinearLayout) linearLayout).getChildCount() > 0)
            ((LinearLayout) linearLayout).removeAllViews();

        for(int i = 0; i<list_emails.size(); i++){
            final TextView valueTV = new TextView(this);
            valueTV.setTextColor(ContextCompat.getColor(this,R.color.black_overlay));
            //valueTV.setTextColor(Color.parseColor("#000000"));
            valueTV.setTextSize(18);
            valueTV.setId(((LinearLayout) linearLayout).getChildCount());
            params.setMargins(0,25,0,0);
            valueTV.setText("  " + list_emails.get(i));
            valueTV.setBackgroundResource(R.color.color_green);
            //valueTV.setAlpha((float)0.2);
            valueTV.getBackground().setAlpha(80);
            valueTV.isClickable();

            valueTV.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("¿Desea eliminar el correo?");


                    // Set up the buttons
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            list_emails.remove(valueTV.getId());
                            showstatesList();
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
            });
            valueTV.setLayoutParams(params);
            ((LinearLayout) linearLayout).addView(valueTV);
        }
    }

    //POST GROUP

    public void postGroup(final String[] arg0){

        String url = "https://evening-oasis-22037.herokuapp.com/groups/create_group/";


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int id = jsonObject.getInt("@id_out_group;");

                            Singleton.getInstance(getApplicationContext()).addList_groups(new Groups(id,arg0[2],arg0[3]));
                            Intent intent = new Intent(getApplicationContext(), ChallengesFragment.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                Map<String, String> params = new HashMap<>();
                params.put("id_user", arg0[0]);
                params.put("id_car", arg0[1]);
                params.put("name_group", arg0[2]);
                params.put("description_group", arg0[3]);

                return params;
            }
        };
        Singleton.getInstance(Select_friends.this).addToRequestQueue(postRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Select_friends.this, ChallengesFragment.class);
        startActivity(intent);
        finish();
    }


}


















/*String[] TO = list_emails.toArray(new String[0]);
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Invitacion");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Acceda al siguiente link para ingresar al grupo: ");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            String[] params = new String[]{"122","2",group_nameStr,description_default};

            postGroup(params);
            finish();
            Log.i("Finished ", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Select_friends.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
*/