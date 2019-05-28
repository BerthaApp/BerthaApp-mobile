package com.example.prueba1.FuelControl;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.prueba1.R;

import java.text.DateFormat;
import java.util.Calendar;

public class Fuel_log extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private final static String[] fuel_type = new String[] {"Gasoline", "Diesel"};
    private final static String[] time = new String[] {"Now", "Other time"};
    private final static String[] date = new String[] {"Today", "Other day"};

    private Spinner spinner_fuel, spinner_time,spinner_date;

    private EditText odometer_now, odometer_last, odometer_travelValue, liter_cost, liter_qtty, liter_totalPrice,date_fuelLog,time_fuelLog;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_log);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Spinners

        spinner_fuel = findViewById(R.id.spinner_fuel_log);

        //EditText

        odometer_last = findViewById(R.id.editText_lastOdometer);
        odometer_now = findViewById(R.id.editText_odometerNow);
        odometer_travelValue = findViewById(R.id.editText_valueTravel);

        liter_cost = findViewById(R.id.editText_literCost);
        liter_qtty = findViewById(R.id.editText_liters);
        liter_totalPrice = findViewById(R.id.editText_totalPrice);

        date_fuelLog = findViewById(R.id.dateFuelLog);
        time_fuelLog = findViewById(R.id.timeFuelLog);

        time_fuelLog.setKeyListener(null);
        date_fuelLog.setKeyListener(null);

        //Adapters

        ArrayAdapter<String> spinner_fuelAdapter = new ArrayAdapter<>(this,R.layout.spinner_item,fuel_type);
        spinner_fuelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_fuel.setAdapter(spinner_fuelAdapter);

        odometer_last.setText("75000");
        getNowTimeandDate();



        date_fuelLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        time_fuelLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        liter_qtty.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                liter_qtty.setFocusable(true);
                return false;
            }
        });

        liter_totalPrice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                liter_totalPrice.setFocusable(true);
                return false;
            }
        });


        //Listener to text change
        liter_cost.addTextChangedListener(literTextWatcher);
        liter_qtty.addTextChangedListener(literTextWatcher);
        //liter_totalPrice.addTextChangedListener(totalPriceWatcher);

        odometer_now.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!odometer_now.getText().toString().equals("")){

                    long last_odometer = Integer.valueOf(odometer_last.getText().toString());
                    long now_odometer = Integer.valueOf(odometer_now.getText().toString());
                    long substract = now_odometer - last_odometer;
                /*if(now_odometer < last_odometer){
                    Toast.makeText(Fuel_log.this, "El valor no puede ser menor", Toast.LENGTH_SHORT).show();
                }*/
                    if(substract > 0){
                        odometer_travelValue.setText(String.valueOf(substract));
                    }else{
                        odometer_travelValue.setText("");
                    }
                }else{
                    odometer_travelValue.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private boolean log_control = false;
    private TextWatcher literTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


            Log.e(TAG, "onTextChanged: liter qtty");
            if(!liter_qtty.getText().toString().equals("") && !liter_cost.getText().toString().equals("")) {
                long liters = Integer.valueOf(liter_qtty.getText().toString());
                long cost_perLiter = Integer.valueOf(liter_cost.getText().toString());

                liter_totalPrice.setText(String.valueOf(liters * cost_perLiter));
            }else {
                liter_totalPrice.setText("");
            }

           /* if(!liter_cost.getText().toString().equals("") && !liter_qtty.getText().toString().equals("")){
                int liters = Integer.valueOf(liter_qtty.getText().toString());
                int cost_perLiter = Integer.valueOf(liter_cost.getText().toString());

                liter_totalPrice.setText(String.valueOf(liters * cost_perLiter));
            }
            else{
                liter_totalPrice.setText("");
            }
*/

            /*
            if(!liter_totalPrice.getText().toString().equals("") && liter_qtty.getText().toString().equals(""))
                log_control = true;

            if(liter_totalPrice.getText().toString().equals("") && !liter_qtty.getText().toString().equals(""))
                log_control = false;

            if(log_control){

                if(!liter_totalPrice.getText().toString().equals("") && !liter_cost.getText().toString().equals("")){
                    int total_cost = Integer.valueOf(liter_totalPrice.getText().toString());
                    int cost_perLiter = Integer.valueOf(liter_cost.getText().toString());

                    liter_qtty.setText(String.valueOf(total_cost / cost_perLiter));
                }else{
                    liter_totalPrice.setText("");
                }

            }else{
                if(!liter_cost.getText().toString().equals("") && !liter_qtty.getText().toString().equals("")){
                    int cost = Integer.valueOf(liter_cost.getText().toString());
                    int quantity = Integer.valueOf(liter_qtty.getText().toString());

                    liter_totalPrice.setText(String.valueOf(cost*quantity));
                }else{

                }
            }*/


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private static String TAG = "Fuel_log";
    private TextWatcher totalPriceWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

                liter_qtty.setFocusable(false);
                Log.e(TAG, "onTextChanged: total price");
                if(!liter_totalPrice.getText().toString().equals("") && !liter_cost.getText().toString().equals("")){
                    long total_cost = Integer.valueOf(liter_totalPrice.getText().toString());
                    long cost_perLiter = Integer.valueOf(liter_cost.getText().toString());

                    liter_qtty.setText(String.valueOf(total_cost / cost_perLiter));
                }else {
                    liter_totalPrice.setText("");
                }

                //Toast.makeText(Fuel_log.this, "Total price ypup", Toast.LENGTH_SHORT).show();

/*
            if(liter_qtty.getText().toString().equals("") && !liter_cost.getText().toString().equals("") && !liter_totalPrice.getText().toString().equals("")){
                int total_cost = Integer.valueOf(liter_totalPrice.getText().toString());
                int cost_perLiter = Integer.valueOf(liter_cost.getText().toString());

                liter_qtty.setText(String.valueOf(total_cost / cost_perLiter));
            }



            if(!liter_totalPrice.getText().toString().equals("") && liter_qtty.getText().toString().equals(""))
                log_control = true;

            if(liter_totalPrice.getText().toString().equals("") && !liter_qtty.getText().toString().equals(""))
                log_control = false;

            if(log_control){

                if(!liter_totalPrice.getText().toString().equals("") && !liter_cost.getText().toString().equals("")){
                    int total_cost = Integer.valueOf(liter_totalPrice.getText().toString());
                    int cost_perLiter = Integer.valueOf(liter_cost.getText().toString());

                    liter_qtty.setText(String.valueOf(total_cost / cost_perLiter));
                }else{
                    liter_totalPrice.setText("");
                }

            }else{
                if(!liter_cost.getText().toString().equals("") && !liter_qtty.getText().toString().equals("")){
                    int cost = Integer.valueOf(liter_cost.getText().toString());
                    int quantity = Integer.valueOf(liter_qtty.getText().toString());

                    liter_totalPrice.setText(String.valueOf(cost*quantity));
                }else{

                }
            }*/


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        date_fuelLog.setText(currentDate);
    }

    final static int ZERO = 0;
    final static String TWO_POINTS = ":";
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String horaFormateada =  (hourOfDay > 12)? String.valueOf(hourOfDay - 12) : String.valueOf(hourOfDay);
        //Formateo el minuto obtenido: antepone el 0 si son menores de 10
        String minutoFormateado = (minute < 10)? String.valueOf(ZERO + minute):String.valueOf(minute);
        //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
        String AM_PM;
        if(hourOfDay < 12) {
            AM_PM = "a.m.";
        } else {
            AM_PM = "p.m.";
        }
        //Muestro la hora con el formato deseado
        time_fuelLog.setText(horaFormateada + TWO_POINTS + minutoFormateado + " " + AM_PM);

    }

    public void getNowTimeandDate(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);

        String horaFormateada =  (hour > 12)? String.valueOf(hour - 12) : String.valueOf(hour);
        //Formateo el minuto obtenido: antepone el 0 si son menores de 10
        String minutoFormateado = (minutes < 10)? String.valueOf(ZERO + minutes):String.valueOf(minutes);
        //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
        String AM_PM;
        if(hour < 12) {
            AM_PM = "a.m.";
        } else {
            AM_PM = "p.m.";
        }
        //Muestro la hora con el formato deseado
        time_fuelLog.setText(horaFormateada + TWO_POINTS + minutoFormateado + " " + AM_PM);

        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        date_fuelLog.setText(currentDate);

    }


    private void getData(){}



}










/*spinner_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @RequiresApi(api = Build.VERSION_CODES.N)
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               Toast.makeText(Fuel_log.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
               if(parent.getItemAtPosition(position).equals("Other day")){
                   DialogFragment datePicker = new DatePickerFragment();
                   datePicker.show(getSupportFragmentManager(), "date picker");
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

        spinner_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Fuel_log.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                if(parent.getItemAtPosition(position).equals("Other time")){
                    DialogFragment timePicker = new TimePickerFragment();
                    timePicker.show(getSupportFragmentManager(), "time picker");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

*/