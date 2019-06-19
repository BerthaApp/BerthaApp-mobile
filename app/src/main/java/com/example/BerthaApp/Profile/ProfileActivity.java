package com.example.BerthaApp.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.BerthaApp.Login.MainActivity;
import com.example.BerthaApp.Pattern.Singleton;
import com.example.BerthaApp.R;
import com.example.BerthaApp.RegisterCar.Main3Activity;
import com.example.BerthaApp.StartDrive.Main4Activity;
import com.example.BerthaApp.Utils.BottomNavigationViewHelper;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.facebook.login.LoginManager.getInstance;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity ";

    private Context mContext = ProfileActivity.this;

    private static final int ACTIVITY_NUM = 4;

    //ListView
    private ListView listView_carList;

    //Button
    private Button button_addCar;

    private ImageView drive_mode, hamburguer_menu;

    private TextView textView_noCars;


    //SHARED PREFERENCES
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String id_user = "id_user";
    public static final String id_car = "id_car";
    public static final String is_Logged = "isLogged";
    public static final String def_driveMode = "def_driveMode";


    private String id_actualUser = "";

    public static LoginManager loginManager =  getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        String def_drive = sharedPreferences.getString(def_driveMode,"");


        setupBottomNavigationView();

        drive_mode = findViewById(R.id.imageView_driveMode);

        hamburguer_menu = findViewById(R.id.hamburguerMenu);

        listView_carList = findViewById(R.id.listView_cars);

        button_addCar = findViewById(R.id.button_newCar);

        textView_noCars = findViewById(R.id.textView_noCars);

        if(def_drive != null) {

            switch (def_drive) {
                case "eco":
                    drive_mode.setImageResource(R.drawable.group1902x);
                    break;
                case "normal":
                    drive_mode.setImageResource(R.drawable.group1912x);
                    break;
                case "aggresive":
                    drive_mode.setImageResource(R.drawable.group1922x);
                    break;
                default:
                    drive_mode.setImageResource(R.drawable.group1902x);
                    break;
            }
        }


        button_addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
                startActivity(intent);
                finish();
            }
        });

        hamburguer_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final PopupMenu popupMenu = new PopupMenu(ProfileActivity.this,hamburguer_menu);
                popupMenu.getMenuInflater().inflate(R.menu.menu_profile,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String optionSelected = item.getTitle().toString();
                        if(optionSelected.equals("Preferences")){
                            Toast.makeText(mContext, "Developing functinality", Toast.LENGTH_SHORT).show();
                        }
                        else if(optionSelected.equals("Log out")){
                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(id_user,"");
                            editor.putString(id_car,"");
                            editor.putBoolean(is_Logged,false);
                            editor.putString(def_driveMode,"");
                            editor.apply();

                            loginManager.logOut();

                            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        return true;
                    }
                });

                popupMenu.show();
            }
        });

        ArrayList<String> list_myCars = Singleton.getInstance(this).getList_myCarsString();

        if(list_myCars.isEmpty()){
            textView_noCars.setVisibility(View.VISIBLE);
        }

        ArrayAdapter<String> list_carAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,list_myCars);
        list_carAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listView_carList.setAdapter(list_carAdapter);


        String id_carSelected = sharedPreferences.getString(id_car, "");
        id_actualUser = sharedPreferences.getString(id_user,"");

        if(!id_carSelected.equals("")) {
            listView_carList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            int pos = Singleton.getInstance(this).getPositionIdCar(Integer.valueOf(id_carSelected));
            Log.d(TAG, String.valueOf(pos));
            listView_carList.setItemChecked(pos, true);
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        final View view = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.set_driv_mode, null);

        final Button boton_eco = view.findViewById(R.id.eco_drive_mode);
        final Button boton_normal = view.findViewById(R.id.normal_drive_mode);
        final Button boton_aggresive = view.findViewById(R.id.aggresive_drive_mode);

        listView_carList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView_carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                My_Cars carSelected = Singleton.getInstance(getApplicationContext()).getList_myCars().get(position);

                String id_carbd = String.valueOf(carSelected.getId());


                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(id_car,id_carbd);
                editor.apply();

                set_newCar(id_carbd,id_actualUser);

                String item = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),"Selected car: "+item,Toast.LENGTH_SHORT).show();
            }
        });

        listView_carList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(ProfileActivity.this, "Developing functionality", Toast.LENGTH_SHORT).show();
                
                /*
                My_Cars carSelected = Singleton.getInstance(getApplicationContext()).getList_myCars().get(position);
                String id_carbd = String.valueOf(carSelected.getId());

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(id_car,"");
                editor.apply();

                delete_car(id_carbd,id_actualUser);*/

                return false;
            }
        });


        drive_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.getParent() != null) {
                    ((ViewGroup) view.getParent()).removeView(view);
                }
                builder.setTitle("Select the driving mode");


                builder.setView(view);
                final AlertDialog alert = builder.show();

                boton_eco.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(def_driveMode, "eco");
                        editor.apply();
                        drive_mode.setImageResource(R.drawable.group1902x);
                        set_newDriveMode("eco", id_actualUser);
                        alert.dismiss();
                    }
                });

                boton_normal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(def_driveMode, "normal");
                        editor.apply();
                        drive_mode.setImageResource(R.drawable.group1912x);
                        set_newDriveMode("normal", id_actualUser);
                        alert.dismiss();
                    }
                });

                boton_aggresive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(def_driveMode, "aggresive");
                        editor.apply();
                        drive_mode.setImageResource(R.drawable.group1922x);
                        set_newDriveMode("aggresive", id_actualUser);
                        alert.dismiss();
                    }
                });
            }
        });

    }

    private void setupBottomNavigationView(){

        Log.e("TAG", "onCreate: starting.22");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        Log.e("TAG", "onCreate: starting.44");
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Main4Activity.class);
        startActivity(intent);
        finish();
    }


    private static final String url_setNewCar = "https://evening-oasis-22037.herokuapp.com/users/set_newCar/";
    public void set_newCar(final String car_id,final String user_id){

        StringRequest postRequest = new StringRequest(Request.Method.POST, url_setNewCar,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response new car def", response);
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
                params.put("id_user", user_id);
                params.put("id_car", car_id);

                return params;
            }
        };
        Singleton.getInstance(ProfileActivity.this).addToRequestQueue(postRequest);
    }


    private static final String url_setNewDriveMode = "https://evening-oasis-22037.herokuapp.com/users/set_newDriveMode/";
    public void set_newDriveMode(final String drive_mode,final String user_id){

        StringRequest postRequest = new StringRequest(Request.Method.POST, url_setNewDriveMode,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response new drive mode", response);
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
                params.put("id_user", user_id);
                params.put("drive_mode", drive_mode);

                return params;
            }
        };
        Singleton.getInstance(ProfileActivity.this).addToRequestQueue(postRequest);
    }
    private static final String url_deleteCar = "https://evening-oasis-22037.herokuapp.com/users/set_newCar/";
    public void delete_car(final String car_id,final String user_id){

        StringRequest postRequest = new StringRequest(Request.Method.POST, url_setNewCar,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response new car def", response);
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
                params.put("id_user", user_id);
                params.put("id_car", car_id);

                return params;
            }
        };
        Singleton.getInstance(ProfileActivity.this).addToRequestQueue(postRequest);
    }

}
