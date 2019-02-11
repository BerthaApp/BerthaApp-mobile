package com.example.prueba1;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button login_btn;
    private TextView sign_upBtn;

    private Spinner spinner_fuel, spinner_transmission, spinner_car_make, spinner_car_model, spinner_car_year,
    spinner_car_engine, spinner_drive_cond;


    //URL API

    private final static String url_make = "https://www.carqueryapi.com/api/0.3/?callback=?&cmd=getMakes";

    private final static String url_modelBy_make = "https://www.carqueryapi.com/api/0.3/?callback=?&cmd=getModels&make=";

    private final static String[] year = new String[] {"Choose year", "2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010"
                                                        ,"2011","2012","2013","2014","2015","2016","2017","2018"};

    private ArrayList<String> trim_model = new ArrayList<>();

    private final static String[] fuel_type = new String[] {"Choose fuel type","Gasoline", "Diesel"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Buttons linked to layout

        login_btn = findViewById(R.id.login_btn);
        sign_upBtn = findViewById(R.id.sign_upBtn);


        //spinner link

        spinner_fuel = findViewById(R.id.spinner_fuel);
        spinner_transmission= findViewById(R.id.spinner_transmission);
        spinner_car_make = findViewById(R.id.spinner_make);
        spinner_car_model = findViewById(R.id.spinner_model);
        spinner_car_year = findViewById(R.id.spinner_year);
        spinner_car_engine = findViewById(R.id.spinner_engine);
        spinner_drive_cond = findViewById(R.id.spinner_driveCond);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
                startActivity(intent);
                finish();
            }
        });

        sign_upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(intent);
                finish();
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
}
