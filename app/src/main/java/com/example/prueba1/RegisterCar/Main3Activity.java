package com.example.prueba1.RegisterCar;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prueba1.Login.MainActivity;
import com.example.prueba1.Profile.ProfileActivity;
import com.example.prueba1.R;
import com.example.prueba1.StartDrive.Main4Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Main3Activity extends AppCompatActivity {

    private Spinner spinner_fuel, spinner_transmission, spinner_car_make, spinner_car_model, spinner_car_year,
            spinner_car_engine, spinner_drive_cond;

    private final static String url_make = "https://www.carqueryapi.com/api/0.3/?callback=?&cmd=getMakes";

    private final static String url_modelBy_make = "https://www.carqueryapi.com/api/0.3/?callback=?&cmd=getModels&make=";

    private final static String url_model_trim = "https://www.carqueryapi.com/api/0.3/?callback=?&cmd=getTrims&make=" ;

    private final static String[] year = new String[] {"Choose year", "2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010"
            ,"2011","2012","2013","2014","2015","2016","2017","2018"};

    private final static String[] fuel_type = new String[] {"Gasoline", "Diesel"};

    private final static String[] transmission_type = new String[] {"Automatic", "Manual"};

    private final static String [] drive_cond = new String[] { "Urban","Rural"};


    private Button save_carButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        spinner_fuel = findViewById(R.id.spinner_fuel);
        spinner_transmission= findViewById(R.id.spinner_transmission);
        spinner_car_make = findViewById(R.id.spinner_make);
        spinner_car_model = findViewById(R.id.spinner_model);
        spinner_car_year = findViewById(R.id.spinner_year);
        spinner_car_engine = findViewById(R.id.spinner_engine);
        spinner_drive_cond = findViewById(R.id.spinner_driveCond);

        save_carButton = findViewById(R.id.save_carButton);

        save_carButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        getAllMake();

        ArrayAdapter<String> spinner_fuelAdapter = new ArrayAdapter<>(this,R.layout.spinner_item,fuel_type);
        spinner_fuelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_fuel.setAdapter(spinner_fuelAdapter);

        ArrayAdapter<String> spinner_transmissionAdapter = new ArrayAdapter<>(this,R.layout.spinner_item,transmission_type);
        spinner_transmissionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_transmission.setAdapter(spinner_transmissionAdapter);

        ArrayAdapter<String> spinner_year = new ArrayAdapter<>(this,R.layout.spinner_item,year);
        spinner_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_car_year.setAdapter(spinner_year);

        ArrayAdapter<String> spinner_condDrive = new ArrayAdapter<>(this,R.layout.spinner_item,drive_cond);
        spinner_condDrive.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_drive_cond.setAdapter(spinner_condDrive);

        ArrayAdapter<String> spinner_modelAdapter = new ArrayAdapter<>(this,R.layout.spinner_item,list_models);
        spinner_modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_car_model.setAdapter(spinner_modelAdapter);

        ArrayAdapter<String> spinner_makeAdapter = new ArrayAdapter<>(this,R.layout.spinner_item,listaMarcas);
        spinner_makeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_car_make.setAdapter(spinner_makeAdapter);

        spinner_car_make.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Choose make")){}else{
                    getModelByMake(spinner_car_make.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner_car_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!spinner_car_model.getSelectedItem().toString().equals("Choose model") && !spinner_car_make.getSelectedItem().toString().equals("Choose make")){
                    Log.e("adsfaf","sdfasfd");
                    getEngine(spinner_car_make.getSelectedItem().toString(), spinner_car_model.getSelectedItem().toString(), spinner_car_year.getSelectedItem().toString());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorGreen));
    }

    ArrayList<String> list_engineMod = new ArrayList<>();
    public void getEngine(String make, String model, String year){

        String model_weighKg, engine_cc, model_length_mm,model_width_mm,model_height_mm,model_mpg_hwy,model_mpg_city,model_mpg_mixed,
        body, door_number, drive, engine_position, engine_type;

        list_engineMod.clear();

        if(make.contains(" ") || model.contains(" ")){
            make = make.replaceAll("\\s","-");
            model = model.replaceAll("\\s","-");

        }

        Log.e("Response is22223: ", url_model_trim + make + "&year=" + year + "&model=" + model);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_model_trim + make + "&year=" + year + "&model=" + model,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        String json = response.replaceAll("[(?);]","");


                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            JSONArray jsonArray = jsonObject.getJSONArray("Trims");

                            for (int i=0; i < jsonArray.length(); i++)
                            {
                                try {
                                    JSONObject oneObject = jsonArray.getJSONObject(i);
                                    // Pulling items from the array
                                    String model_trim = oneObject.getString("model_trim");

                                    list_engineMod.add(model_trim);
                                    Log.e("Response is123: ", model_trim);
                                } catch (JSONException e) {
                                    // Oops
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ArrayAdapter<String> spinner_engineAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,list_engineMod);
                        spinner_engineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_car_engine.setAdapter(spinner_engineAdapter);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "NOP", Toast.LENGTH_SHORT).show();
            }

        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), Main4Activity.class);
        startActivity(intent);
        finish();
    }

    ArrayList<String> listaMarcas = new ArrayList<String>(Arrays.asList("Choose make"));

    public void getAllMake(){
        listaMarcas.clear();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_make,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        String json = response.replaceAll("[(?);]","");
                        listaMarcas.add("Choose make");
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            JSONArray jsonArray = jsonObject.getJSONArray("Makes");

                            for (int i=0; i < jsonArray.length(); i++)
                            {
                                try {
                                    JSONObject oneObject = jsonArray.getJSONObject(i);
                                    // Pulling items from the array
                                    String oneObjectsItem = oneObject.getString("make_display");

                                    listaMarcas.add(oneObjectsItem);
                                    Log.e("Response is: ", oneObjectsItem);
                                } catch (JSONException e) {
                                    // Oops
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ArrayAdapter<String> spinner_makeAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,listaMarcas);
                        spinner_makeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_car_make.setAdapter(spinner_makeAdapter);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "NOP", Toast.LENGTH_SHORT).show();
            }

        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    ArrayList<String> list_models = new ArrayList<>(Arrays.asList("Choose model"));

    public void getModelByMake(String make){
        list_models.clear();

        if(make.contains(" ")){
            make = make.replaceAll("\\s","-");

        }

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_modelBy_make + make,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        String json = response.replaceAll("[(?);]","");
                        list_models.add("Choose model");
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            JSONArray jsonArray = jsonObject.getJSONArray("Models");

                            for (int i=0; i < jsonArray.length(); i++)
                            {
                                try {
                                    JSONObject oneObject = jsonArray.getJSONObject(i);
                                    // Pulling items from the array
                                    String oneObjectsItem = oneObject.getString("model_name");

                                    list_models.add(oneObjectsItem);
                                    Log.e("Response is: ", oneObjectsItem);
                                } catch (JSONException e) {
                                    // Oops
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ArrayAdapter<String> spinner_modelAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,list_models);
                        spinner_modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_car_model.setAdapter(spinner_modelAdapter);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "NOP", Toast.LENGTH_SHORT).show();
            }

        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
