package com.example.BerthaApp.StartDrive;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.BerthaApp.Challenges.Challenges;
import com.example.BerthaApp.Challenges.Groups;
import com.example.BerthaApp.Maps.Main_maps;
import com.example.BerthaApp.Pager_DriveMode.Adapter;
import com.example.BerthaApp.Pager_DriveMode.Model;
import com.example.BerthaApp.Pattern.Singleton;
import com.example.BerthaApp.Profile.My_Cars;
import com.example.BerthaApp.R;
import com.example.BerthaApp.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main4Activity extends AppCompatActivity {

    public static final String TAG = "Start new drive";

    private static final int ACTIVITY_NUM = 2;

    private ViewPager viewPager;
    private Adapter adapter;

    private List<Model> models;

    private Button btn_startDrive;

    private NumberPicker numberPicker;

    //private Singleton singleton = Singleton.getInstance(Main4Activity.this);

    //SHARED PREFERENCES
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String id_user = "id_user";
    public static final String id_car = "id_car";
    public static final String is_Logged = "isLogged";
    public static final String def_driveMode = "def_driveMode";

    private Context mContext = Main4Activity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String idUser = sharedPreferences.getString(id_user,"");
        String def_drive = sharedPreferences.getString(def_driveMode,"");

        setupBottomNavigationView();

        models = new ArrayList<>();
        models.add(new Model(R.drawable.group1902x));
        models.add(new Model(R.drawable.group1912x));
        models.add(new Model(R.drawable.group1922x));

        adapter = new Adapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(350,0,200,0);

        if(def_drive != null) {

            switch (def_drive) {
                case "eco":
                    viewPager.setCurrentItem(0);
                    break;
                case "normal":
                    viewPager.setCurrentItem(1);
                    break;
                case "aggresive":
                    viewPager.setCurrentItem(2);
                    break;
                default:
                    viewPager.setCurrentItem(0);
                    break;
            }
        }

        numberPicker = findViewById(R.id.numberPicker);

        btn_startDrive = findViewById(R.id.button_startDrive);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(7);


        Singleton.getInstance(getApplicationContext()).clearList_groups();
        Singleton.getInstance(getApplicationContext()).clearList_challenges();
        Singleton.getInstance(getApplicationContext()).clearList_cars();

        //DESCARGA LA INFORMACION DEL USUARIO DE CHALLENGES Y GRUPOS
        downloadUserData(idUser);

        //DESCARGA LA INFORMACION DEL USUARIO DE AUTOS
        downloadCarUserData(idUser);

        btn_startDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main_maps.class);
                startActivity(intent);

            }
        });


    }

    private void setupBottomNavigationView(){

        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(Main4Activity.this)
                .setTitle("Salir de App")
                .setMessage("Desea salir de la aplicacion?")
                .setIcon(R.drawable.group40x)
                .setPositiveButton("Sí",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(Main4Activity.this, "Hasta pronto!", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                                finish();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(Main4Activity.this, "Ok", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                }).show();

    }


    public void downloadUserData(String idUser){

        String url_get_data = "https://evening-oasis-22037.herokuapp.com/users/userGroup&Challenges/";
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url_get_data + idUser,null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //All challenges

                            JSONArray jsonArrayAllChallenges = response.getJSONArray(0);
                            JSONArray jsonArray4AllChallenges = jsonArrayAllChallenges.getJSONArray(0);
                            int len = jsonArray4AllChallenges.length();

                            for(int i = 0; i<len; i++){

                                try {

                                    JSONObject jsonObjectAllChallenges = jsonArray4AllChallenges.getJSONObject(i);
                                    int id = jsonObjectAllChallenges.getInt("id");
                                    String name = jsonObjectAllChallenges.getString("name");
                                    String description = jsonObjectAllChallenges.getString("description");
                                    int tokens = jsonObjectAllChallenges.getInt("tokens");
                                    String created_atStr = jsonObjectAllChallenges.getString("created_at");
                                    String end_dateStr = jsonObjectAllChallenges.getString("end_date");
                                    String score_min = jsonObjectAllChallenges.getString("score_min");
                                    String route_from = jsonObjectAllChallenges.getString("route_from");
                                    String route_to = jsonObjectAllChallenges.getString("route_to");

                                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    Date created_at = inputFormat.parse(created_atStr);
                                    Date end_date = inputFormat.parse(end_dateStr);
                                    created_atStr = outputFormat.format(created_at);
                                    end_dateStr = outputFormat.format(end_date);

                                   Singleton.getInstance(getApplicationContext()).addList_challenges (new Challenges(id,name,description,tokens,created_atStr,end_dateStr,score_min,route_from,route_to,false));


                                }catch (JSONException e){
                                    //Something went wrong
                                    e.printStackTrace();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }

                            //My challenges
                            JSONArray jsonArrayMyChallenges = response.getJSONArray(1);
                            JSONArray jsonArray2MyChallenges = jsonArrayMyChallenges.getJSONArray(0);
                            len = jsonArray2MyChallenges.length();
                            ArrayList<Integer> id_MyChallenges = new ArrayList<>();
                            for(int i = 0; i<len; i++){
                                try {
                                JSONObject jsonObjectMyChallenges = jsonArray2MyChallenges.getJSONObject(i);
                                    int id = jsonObjectMyChallenges.getInt("id");
                                    id_MyChallenges.add(id);


                                }catch (JSONException e){
                                    //Something went wrong
                                    e.printStackTrace();
                                }
                            }

                            Singleton.getInstance(getApplicationContext()).link_myChallenges(id_MyChallenges);


                            //My Groups
                            JSONArray jsonArrayMyGroups = response.getJSONArray(2);
                            JSONArray jsonArray4MyGroups = jsonArrayMyGroups.getJSONArray(0);
                            len = jsonArray4MyGroups.length();
                            for(int i = 0; i<len; i++){

                                try {
                                    JSONObject jsonObjectMyGroups = jsonArray4MyGroups.getJSONObject(i);
                                    int id = jsonObjectMyGroups.getInt("id");
                                    String name = jsonObjectMyGroups.getString("name");
                                    String description = jsonObjectMyGroups.getString("description");
                                    Singleton.getInstance(getApplicationContext()).addList_groups(new Groups(id,name,description));
                                }catch (JSONException e){
                                    //Something went wrong
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //boolean passwordMatch = PasswordUtils.verifyUserPassword(password, pass, salt);

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_SHORT).show();
            }

        });

// Add the request to the RequestQueue.
        Singleton.getInstance(Main4Activity.this).addToRequestQueue(stringRequest);

    }


    public void downloadCarUserData(String idUser){
        String url_get_carData = "https://evening-oasis-22037.herokuapp.com/cars/carsxuser/";
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url_get_carData + idUser, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {

                        try{
                            int len = response.length();
                            for(int i = 0 ;i<len; i++){
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String make = jsonObject.getString("make");
                                String model = jsonObject.getString("model");
                                String engine = jsonObject.getString("model_trim");

                                Singleton.getInstance(getApplicationContext()).addList_cars(new My_Cars(id,make,model,engine));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        Singleton.getInstance(Main4Activity.this).addToRequestQueue(getRequest);
    }
}