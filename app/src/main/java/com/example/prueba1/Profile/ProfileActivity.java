package com.example.prueba1.Profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prueba1.R;
import com.example.prueba1.RegisterCar.Main3Activity;
import com.example.prueba1.StartDrive.Main4Activity;
import com.example.prueba1.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class ProfileActivity extends AppCompatActivity {

    private Context mContext = ProfileActivity.this;

    private static final int ACTIVITY_NUM = 4;

    //ListView
    private ListView listView_carList;

    //Button
    private Button button_addCar;

    private ImageView drive_mode;
    private static String[] listCars = new String[] {"VW Golf","Land Cruiser", "Volkswagen","Land Cruiser", "Volkswagen","Land Cruiser", "Volkswagen","Land Cruiser", "Volkswagen"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        setupBottomNavigationView();

        drive_mode = findViewById(R.id.imageView_driveMode);


        listView_carList = findViewById(R.id.listView_cars);

        button_addCar = findViewById(R.id.button_newCar);


        button_addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
                startActivity(intent);
                finish();
            }
        });

        ArrayAdapter<String> list_carAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,listCars);
        list_carAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listView_carList.setAdapter(list_carAdapter);

        final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        final View view = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.set_driv_mode, null);

        final Button boton_eco = view.findViewById(R.id.eco_drive_mode);
        final Button boton_normal = view.findViewById(R.id.normal_drive_mode);
        final Button boton_aggresive = view.findViewById(R.id.aggresive_drive_mode);



        listView_carList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView_carList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),"Selected car: "+item,Toast.LENGTH_SHORT).show();
            }
        });


        drive_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.getParent() != null) {
                    ((ViewGroup) view.getParent()).removeView(view);
                }
                builder.setTitle("Title");


                builder.setView(view);
                final AlertDialog alert = builder.show();

                boton_eco.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        drive_mode.setImageResource(R.drawable.group1902x);
                        alert.dismiss();
                    }
                });

                boton_normal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        drive_mode.setImageResource(R.drawable.group1912x);
                        alert.dismiss();
                    }
                });

                boton_aggresive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        drive_mode.setImageResource(R.drawable.group1922x);
                        alert.dismiss();
                    }
                });

       /*Dialog settingsDialog = new Dialog(getApplicationContext());
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.set_driv_mode
                , null));
        settingsDialog.show();*/
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
}
