package com.example.prueba1.StartDrive;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.prueba1.Challenges.Challenges;
import com.example.prueba1.Challenges.Groups;
import com.example.prueba1.Login.MainActivity;
import com.example.prueba1.Pager_DriveMode.Adapter;
import com.example.prueba1.Pager_DriveMode.Model;
import com.example.prueba1.Pattern.Singleton;
import com.example.prueba1.R;
import com.example.prueba1.Utils.BottomNavigationViewHelper;
import com.example.prueba1.Utils.PasswordUtils;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Main4Activity extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 2;

    private ViewPager viewPager;
    private Adapter adapter;
    private List<Model> models;


    private NumberPicker numberPicker;

    //private Singleton singleton = Singleton.getInstance(Main4Activity.this);

    //SHARED PREFERENCES
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String id_user = "id_user";
    public static final String id_car = "id_car";
    public static final String is_Logged = "isLogged";

    private Context mContext = Main4Activity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Log.e("here","here");
        setupBottomNavigationView();

        models = new ArrayList<>();
        models.add(new Model(R.drawable.group1902x));
        models.add(new Model(R.drawable.group1912x));
        models.add(new Model(R.drawable.group1922x));

        adapter = new Adapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(350,0,200,0);

        numberPicker = findViewById(R.id.numberPicker);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(7);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        //Boolean isLogged = sharedPreferences.getBoolean(is_Logged, false);
        String idUser = sharedPreferences.getString(id_user,"");
        //String idUser = getIntent().getStringExtra("idUser");
        //String idCar = getIntent().getStringExtra("idCar");
        Singleton.getInstance(getApplicationContext()).clearList_groups();
        Singleton.getInstance(getApplicationContext()).clearList_challenges();
        downloadUserData(idUser);


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
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private String url_get_data = "https://evening-oasis-22037.herokuapp.com/users/userGroup&Challenges/";

    public void downloadUserData(String idUser){

        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url_get_data + idUser,null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //All challenges

                            JSONArray jsonArrayAllChallenges = response.getJSONArray(0);
                            JSONArray jsonArray4AllChallenges = jsonArrayAllChallenges.getJSONArray(0);
                            int len = jsonArray4AllChallenges.length();
                            Log.e("largo",String.valueOf(len));
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
                            Log.e("lista id", Arrays.toString(id_MyChallenges.toArray()));
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
                        Log.e("Response",response.toString());
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
}
