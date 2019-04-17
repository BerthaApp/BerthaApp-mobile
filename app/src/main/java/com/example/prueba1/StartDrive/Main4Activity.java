package com.example.prueba1.StartDrive;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class Main4Activity extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 2;

    private ViewPager viewPager;
    private Adapter adapter;
    private List<Model> models;


    private NumberPicker numberPicker;





    private Context mContext = Main4Activity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
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

        String idUser = getIntent().getStringExtra("idUser");
        String idCar = getIntent().getStringExtra("idCar");

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
                            Log.e("here1","here1");
                            JSONArray jsonArray = response.getJSONArray(0);
                            JSONArray jsonArray4 = jsonArray.getJSONArray(0);
                            JSONObject jsonObject = jsonArray4.getJSONObject(0);
                            String string = jsonObject.getString("name");
                            Log.e("here2",string);
                            JSONArray jsonArray2 = response.getJSONArray(0).getJSONArray(1);
                            Log.e("here3","here3");
                            JSONArray jsonArray3 = response.getJSONArray(0).getJSONArray(2);

                            Log.e("jsonArray",jsonArray.toString());
                            Log.e("jsonArray2",jsonArray2.toString());
                            Log.e("jsonArray3",jsonArray3.toString());

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
