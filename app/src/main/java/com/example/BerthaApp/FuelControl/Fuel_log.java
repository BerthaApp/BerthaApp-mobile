package com.example.BerthaApp.FuelControl;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.BerthaApp.Pattern.Singleton;
import com.example.BerthaApp.R;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Fuel_log extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private final static String[] fuel_type = new String[] {"Gasoline", "Diesel"};
    private final static String[] time = new String[] {"Now", "Other time"};
    private final static String[] date = new String[] {"Today", "Other day"};

    private Spinner spinner_fuel, spinner_time,spinner_date;

    private EditText odometer_now, odometer_last, odometer_travelValue, liter_cost, liter_qtty, liter_totalPrice,date_fuelLog,time_fuelLog,place_fuelUp;

    private Button save_fuelLog;

    //User and car from shared preferences
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String id_user = "id_user";
    public static final String id_car = "id_car";

    private String id_userLogged;
    private String id_carDef;

    //ListView logs

    private ArrayList<Log_object> list_fuelLogs = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_log);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        id_userLogged = sharedPreferences.getString(id_user,"");
        id_carDef = sharedPreferences.getString(id_car,"");
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

        place_fuelUp = findViewById(R.id.editText_place);

        //Button
        save_fuelLog = findViewById(R.id.save_fuelUp);

        time_fuelLog.setKeyListener(null);
        date_fuelLog.setKeyListener(null);

        //Adapters

        ArrayAdapter<String> spinner_fuelAdapter = new ArrayAdapter<>(this,R.layout.spinner_item,fuel_type);
        spinner_fuelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_fuel.setAdapter(spinner_fuelAdapter);


        getNowTimeandDate();


        list_fuelLogs = Singleton.getInstance(this).getList_fuelLogs();

        if(list_fuelLogs.isEmpty()){
            odometer_last.setText("0");
        }else{
            odometer_last.setText(String.valueOf(list_fuelLogs.get(list_fuelLogs.size()-1).getOdometer_current()));
        }

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

                    float last_odometer = Float.valueOf(odometer_last.getText().toString());
                    float now_odometer = Float.valueOf(odometer_now.getText().toString());
                    float substract = now_odometer - last_odometer;
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

        save_fuelLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getData()){
                    Toast.makeText(Fuel_log.this, "Registrado exitosamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Fuel_log.this,FuelControlActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(Fuel_log.this, "Hubo un error o no completaste los campos necesarios", Toast.LENGTH_SHORT).show();
                }
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

    final static String ZERO = "0";
    final static String TWO_POINTS = ":";
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String horaFormateada =  (hourOfDay > 12)? String.valueOf(hourOfDay - 12) : String.valueOf(hourOfDay);
        //Formateo el minuto obtenido: antepone el 0 si son menores de 10
        String minutoFormateado = (minute < 10)? (ZERO + minute):String.valueOf(minute);
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
        int am_pm = calendar.get(Calendar.AM_PM);

        String horaFormateada =  (hour > 12)? String.valueOf(hour - 12) : String.valueOf(hour);
        //Formateo el minuto obtenido: antepone el 0 si son menores de 10
        String minutoFormateado = (minutes < 10)? (ZERO + minutes):String.valueOf(minutes);
        //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
        String AM_PM = (am_pm == 0)? "a.m." : "p.m.";

        //Muestro la hora con el formato deseado
        time_fuelLog.setText(horaFormateada + TWO_POINTS + minutoFormateado + " " + AM_PM);

        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        date_fuelLog.setText(currentDate);

    }

    private boolean getData(){

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dateDef = new Date(System.currentTimeMillis());

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        id_userLogged = sharedPreferences.getString(id_user,"");
        id_carDef = sharedPreferences.getString(id_car,"");
        String[] params = new String[13];

        String time = time_fuelLog.getText().toString();
        String date = date_fuelLog.getText().toString();
        String current_odometer = odometer_now.getText().toString();
        String km_travel = odometer_travelValue.getText().toString();
        String liter_costStr= liter_cost.getText().toString();
        String liters = liter_qtty.getText().toString();
        String total_cost = liter_totalPrice.getText().toString();
        String fuel_type = spinner_fuel.getSelectedItem().toString();
        String place_fuel = place_fuelUp.getText().toString();



        SimpleDateFormat format =new SimpleDateFormat("MMM dd, yyyy");
        try {
            dateDef = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(current_odometer.equals("") || liter_costStr.equals("") || liters.equals("") || id_carDef.equals(""))
            return false;

        String formatedDate = formatter.format(dateDef);

        params[0] = id_userLogged;
        params[1] = id_carDef;
        params[2] = formatedDate;
        params[3] = convert_TimeToSql(time);
        params[4] = current_odometer;
        params[5] = km_travel;
        params[6] = liters;
        params[7] = total_cost;
        params[8] = liter_costStr;
        params[9] = fuel_type;
        params[10] = place_fuel;
        params[11] = "50";
        params[12] = String.valueOf(false);

        postFuelUp(params);

        return true;

    }


    public String convert_TimeToSql(String strDate){
        String[] arrayHoursAndTime = strDate.split(" ");
        String[] array = arrayHoursAndTime[0].split(":");
        int hour = Integer.valueOf(array[0]);
        if(strDate.contains("p.m.") && (Integer.valueOf(array[0]) != 12)){
            hour = 12 + Integer.valueOf(array[0]);
        }

        return (hour) + TWO_POINTS + array[1]+TWO_POINTS+"00";

    }


    public void postFuelUp(final String[] arg0){

        String url = "https://evening-oasis-22037.herokuapp.com/fuelLog/fuel_log/";


        StringRequest postRequestFuel = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Error.Response", response);
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
                Log.e(TAG, "getParams: " );
                Map<String, String> params = new HashMap<String, String>();
                params.put("idUser", arg0[0]);
                params.put("idCar", arg0[1]);
                params.put("date", arg0[2]);
                params.put("time", arg0[3]);
                params.put("odometer_current", arg0[4]);
                params.put("km_traveled", arg0[5]);
                params.put("liters_qtty", arg0[6]);
                params.put("total_price", arg0[7]);
                params.put("price_perLiter", arg0[8]);
                params.put("fuel_type", arg0[9]);
                params.put("place_fuelUp", arg0[10]);
                params.put("city_drivingPrctg", arg0[11]);
                params.put("partial_fuelUp", arg0[12]);

                return params;
            }
        };
        Singleton.getInstance(Fuel_log.this).addToRequestQueue(postRequestFuel);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
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