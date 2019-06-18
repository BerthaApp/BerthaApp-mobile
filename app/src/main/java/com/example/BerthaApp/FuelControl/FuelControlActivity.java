package com.example.BerthaApp.FuelControl;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.BerthaApp.Challenges.ChallengesTemp;
import com.example.BerthaApp.Pattern.Singleton;
import com.example.BerthaApp.Profile.ProfileActivity;
import com.example.BerthaApp.R;
import com.example.BerthaApp.StartDrive.Main4Activity;
import com.example.BerthaApp.Utils.BottomNavigationViewHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FuelControlActivity extends AppCompatActivity {

    private static final String TAG = "FuelControlActivity";

    private Context mContext = FuelControlActivity.this;

    private static final int ACTIVITY_NUM = 1;

    private final String[] values_firstGridstr = {"AV L/100 km", "LAST L/100 km","BEST L/100 km","Total km Tracked","Fuel logs","Total Liters"};

    private final float[] values_firstGridint = {0,0,0,0,0,0};

    private final String[] values_secondGridstr = {"Avg. Price/Liter", "Avg. Fuel-Up Cost","Avg. Price/km"};

    private final float[] values_secondGridint = {0,0,0};

    private GridView gridView_stats,gridView_prices;

    private FloatingActionButton floatingActionButton_addLog;

    private TextView txtView_total_spend;

    private BarChart barChart;
    private CombinedChart combinedChart;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String id_user = "id_user";
    public static final String id_car = "id_car";

    private String id_userLogged;
    private String id_carDef;

    protected String[] mMonths = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "June"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuelcontrol);

        barChart = findViewById(R.id.barChart);
        combinedChart = findViewById(R.id.combinedChart);

        setupBottomNavigationView();

        gridView_stats = findViewById(R.id.gridviewHorizontal);
        gridView_prices = findViewById(R.id.gridview_prices);


        txtView_total_spend = findViewById(R.id.total_fuel_cost);
        floatingActionButton_addLog = findViewById(R.id.fab_button_addFuelLog);

        //Get user id and car id

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        id_userLogged = sharedPreferences.getString(id_user,"");
        id_carDef = sharedPreferences.getString(id_car,"");

        if(!id_carDef.equals("-1"))
            getFuelLogs();
        else{
            set_defaultData();
        }


        //BarChart
        //PieChart
        //CombinedChart

        floatingActionButton_addLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(id_carDef.equals("-1")){
                    final Dialog dialog = new Dialog(FuelControlActivity.this);
                    dialog.setContentView(R.layout.alert_dialog);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                    final Handler handler  = new Handler();
                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                                startActivity(new Intent(FuelControlActivity.this, ProfileActivity.class));
                                finish();
                            }
                        }
                    };

                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            handler.removeCallbacks(runnable);
                        }
                    });

                    handler.postDelayed(runnable, 2000);
                }else{
                    startActivity(new Intent(FuelControlActivity.this,Fuel_log.class));
                }

            }
        });


        /// BAR CHART



        /// COMBINED CHART

        combinedChart.getDescription().setEnabled(false);
        combinedChart.setDrawGridBackground(true);

        combinedChart.setDrawBarShadow(true);
        combinedChart.setHighlightFullBarEnabled(true);

        // draw bars behind lines
        combinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,  CombinedChart.DrawOrder.LINE
        });

        LegendEntry[] legendEntry = new LegendEntry[2];

        LegendEntry entry = new LegendEntry();
        entry.label="Costo del tanque";
        entry.formColor = ContextCompat.getColor(this,R.color.color_green);
        legendEntry[0] = entry;

        LegendEntry entry2 = new LegendEntry();
        entry2.label="Km Recorridos";
        entry2.formColor = Color.rgb(240, 238, 70);
        legendEntry[1] = entry2;


        Legend l = combinedChart.getLegend();
        l.setEnabled(true);
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setCustom(legendEntry);

        YAxis rightAxis = combinedChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMaximum(50000f);
        rightAxis.setAxisMinimum(5000f); // this replaces setStartAtZero(true)

        YAxis leftAxis = combinedChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMaximum(50000f);
        leftAxis.setAxisMinimum(5000f); // this replaces setStartAtZero(true)

        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(0.5f);
        //xAxis.setGranularity(1f);

        /*xAxis.setValueFormatter(new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                return mMonths[(int) value % mMonths.length];
            }
        });*/

        CombinedData dataCombined = new CombinedData();

        dataCombined.setData( generateLineData());
        dataCombined.setData(generateBarData());

        xAxis.setAxisMaximum(dataCombined.getXMax() + 0.50f);

        combinedChart.setData(dataCombined);
        combinedChart.invalidate();
    }

    private float[] getValuesBarChart(float[] values){
        ArrayList<Log_object> list_logs = Singleton.getInstance(this).getList_fuelLogs();
        switch (list_logs.size()){
            case 0: case 1:
                Log.e(TAG, "getValuesBarChart: 0-1" );
                values[0] = 0;
                values[1] = 0;
                values[2] = 0;
                values[3] = 0;
                break;
            case 2:
                Log.e(TAG, "getValuesBarChart: 2" );
                values[0] = list_logs.get(0).getLiters_qtty() / (list_logs.get(1).getKm_traveled() / 100);
                values[1] = 0;
                values[2] = 0;
                values[3] = 0;
                break;
            case 3:
                Log.e(TAG, "getValuesBarChart: 3" );
                values[0] = list_logs.get(0).getLiters_qtty() / (list_logs.get(1).getKm_traveled() / 100);
                values[1] = list_logs.get(1).getLiters_qtty() / (list_logs.get(2).getKm_traveled() / 100);
                values[2] = 0;
                values[3] = 0;
                break;
            case 4:
                Log.e(TAG, "getValuesBarChart: 4" );
                values[0] = list_logs.get(0).getLiters_qtty() / (list_logs.get(1).getKm_traveled() / 100);
                values[1] = list_logs.get(1).getLiters_qtty() / (list_logs.get(2).getKm_traveled() / 100);
                values[2] = list_logs.get(2).getLiters_qtty() / (list_logs.get(3).getKm_traveled() / 100);
                values[3] = 0;
                break;
            default:
                Log.e(TAG, "getValuesBarChart: Def" );
                int len = list_logs.size()-5;
                values[0] = list_logs.get(len).getLiters_qtty() / (list_logs.get(len+1).getKm_traveled() / 100);
                values[1] = list_logs.get(len+1).getLiters_qtty() / (list_logs.get(len+2).getKm_traveled() / 100);
                values[2] = list_logs.get(len+2).getLiters_qtty() / (list_logs.get(len+3).getKm_traveled() / 100);
                values[3] = list_logs.get(len+3).getLiters_qtty() / (list_logs.get(len+4).getKm_traveled() / 100);
        }

       /*
        float best_l_per_100km = 10000000;

        for(Log_object i : list_logs){
            float best_l_per100km_temp = i.getLiters_qtty() / (i.getKm_traveled() / 100);
            if(best_l_per100km_temp < best_l_per_100km)
                best_l_per_100km = best_l_per100km_temp;
        }*/

        return values;
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
        Intent intent = new Intent(this, Main4Activity.class);
        startActivity(intent);
        finish();
    }

    private ArrayList<Entry> getLineEntriesData(ArrayList<Entry> entries){
        //entries.add(new Entry(0, 20));
        entries.add(new Entry(1, 15000));
        entries.add(new Entry(2, 16000));
        entries.add(new Entry(3, 17000));
        entries.add(new Entry(4, 18000));

        return entries;
    }

    private ArrayList<BarEntry> getBarEnteries(ArrayList<BarEntry> entries){
        //entries.add(new BarEntry(0, 25));
        entries.add(new BarEntry(1, 19000));
        entries.add(new BarEntry(2, 20000));
        entries.add(new BarEntry(3, 18000));
        entries.add(new BarEntry(4, 19000));
        return  entries;
    }

    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        entries = getLineEntriesData(entries);

        LineDataSet set = new LineDataSet(entries, "Line");
        //set.setColor(Color.rgb(240, 238, 70));
        set.setColor(ContextCompat.getColor(this,R.color.color_blue));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private BarData generateBarData() {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        entries = getBarEnteries(entries);

        BarDataSet set1 = new BarDataSet(entries, "Bar");
        //set1.setColor(Color.rgb(60, 220, 78));
        set1.setColors(ContextCompat.getColor(this,R.color.color_green));
        set1.setValueTextColor(ContextCompat.getColor(this,R.color.black_overlay));
        set1.setBarShadowColor(ContextCompat.getColor(this,R.color.color_green_very_light));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);


        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.50f; // x2 dataset

        BarData d = new BarData(set1);
        d.setBarWidth(barWidth);

        return d;
    }

    //Download data from BD
    public void getFuelLogs(){

        Singleton.getInstance(this).clearList_fuelLogs();
        String url_get_fuelLogs = "https://evening-oasis-22037.herokuapp.com/fuelLog/getLogsxUser/" + id_userLogged+
                                                                                                        "/"+id_carDef;
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url_get_fuelLogs, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG,response.toString());
                        try{
                            int len = response.length();
                            for(int i = 0 ;i<len; i++){
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String date = jsonObject.getString("date");
                                String time = jsonObject.getString("time");
                                float odometer_current = jsonObject.getInt("odometer_current_value");
                                float liters_qtty = jsonObject.getInt("liters_quantity");
                                float total_price = jsonObject.getInt("total_price");
                                float price_perLiter = jsonObject.getInt("price_per_liter");
                                String fuel_type = jsonObject.getString("fuel_type");
                                float km_traveled = jsonObject.getInt("km_traveled");

                                Singleton.getInstance(getApplicationContext()).addList_fuelLogs(new Log_object(id,date,time,odometer_current,liters_qtty,total_price,price_perLiter,fuel_type,km_traveled));

                            }
                            statistics();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_SHORT).show();

            }
        });

        Singleton.getInstance(FuelControlActivity.this).addToRequestQueue(getRequest);
    }


    public void statistics(){
        ArrayList<Log_object> list_logs = Singleton.getInstance(this).getList_fuelLogs();
        float km_traveled = 0;
        float last_l_per_100km = 0;
        int total_logs = list_logs.size();
        float total_spend = 0;
        int total_liters = 0;
        float total_price_liter = 0;
        float avg_l_per_100km = 0;
        float best_l_per_100km = 10000000;
        int len_listLogs = list_logs.size();

        switch (len_listLogs){
            case 0:
                Items_adapter items_adapter = new Items_adapter(this,values_firstGridint,values_firstGridstr);
                gridView_stats.setAdapter(items_adapter);

                Items_adapter items_adapterPrices = new Items_adapter(this,values_secondGridint,values_secondGridstr);
                gridView_prices.setAdapter(items_adapterPrices);

                Toast.makeText(mContext, "No ha registrado ningun llenado de tanque (Informacion por defecto)", Toast.LENGTH_LONG).show();
                return;
            case 1:
                km_traveled = list_logs.get(0).getOdometer_current();
                break;
            default:
                km_traveled = list_logs.get(list_logs.size()-1).getOdometer_current() - list_logs.get(0).getOdometer_current();
                last_l_per_100km = list_logs.get(len_listLogs-2).getLiters_qtty() / (list_logs.get(len_listLogs-1).getKm_traveled() / 100);
                break;
        }





        for(int i = 0 ; i<len_listLogs;i++){

            total_spend += list_logs.get(i).getTotal_price();
            total_liters += list_logs.get(i).getLiters_qtty();
            total_price_liter += list_logs.get(i).getPrice_perLiter();
            try{
                if(len_listLogs > 1) {
                    float best_l_per100km_temp = list_logs.get(i).getLiters_qtty() / (list_logs.get(i+1).getKm_traveled() / 100);
                    if (best_l_per100km_temp < best_l_per_100km)
                        best_l_per_100km = best_l_per100km_temp;
                }else{
                    best_l_per_100km = 0;
                }
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }

        float avg_fuelUp_cost =  (total_spend/total_logs);

        float avg_price_liter =  (total_price_liter/total_logs);

        float avg_price_km = total_spend / km_traveled;

        avg_l_per_100km = (total_liters / km_traveled) * 100;

        Log.e(TAG, "statistics: totalSpend: "+total_spend);
        Log.e(TAG, "statistics: total_liters: "+total_liters);
        Log.e(TAG, "statistics: avg_price_liter: "+avg_price_liter);
        Log.e(TAG, "statistics: totalLogs: "+total_logs);
        Log.e(TAG, "statistics: totalSpend: "+total_spend);
        Log.e(TAG, "statistics: avg_fuelUp_cost: "+avg_fuelUp_cost);
        Log.e(TAG, "statistics: avg_price_km: "+avg_price_km);
        Log.e(TAG, "statistics: avg_l_per_100km: "+avg_l_per_100km);
        Log.e(TAG, "statistics: last_l_per_100km: "+last_l_per_100km);
        Log.e(TAG, "statistics: km_traveled: "+km_traveled);

        values_firstGridint[0] = avg_l_per_100km;
        values_firstGridint[1] = last_l_per_100km;
        values_firstGridint[2] = best_l_per_100km;

        values_firstGridint[3] = km_traveled;
        values_firstGridint[4] = total_logs;
        values_firstGridint[5] = total_liters;

        Items_adapter items_adapter = new Items_adapter(this,values_firstGridint,values_firstGridstr);
        gridView_stats.setAdapter(items_adapter);

        values_secondGridint[0] = avg_price_liter;
        values_secondGridint[1] = avg_fuelUp_cost;
        values_secondGridint[2] = avg_price_km;

        Items_adapter items_adapterPrices = new Items_adapter(this,values_secondGridint,values_secondGridstr);
        gridView_prices.setAdapter(items_adapterPrices);

        txtView_total_spend.setText("Total fuel cost: ₡ "+total_spend);

        //BAR CHART

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        barChart.getDescription().setEnabled(false);

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        float[] valuesGraph = new float[4];

        valuesGraph = getValuesBarChart(valuesGraph);

        barEntries.add(new BarEntry(1,valuesGraph[0]));
        barEntries.add(new BarEntry(2,valuesGraph[1]));
        barEntries.add(new BarEntry(3,valuesGraph[2]));
        barEntries.add(new BarEntry(4,valuesGraph[3]));

        BarDataSet barDataSet = new BarDataSet(barEntries,"L/100km Recientes");
        barDataSet.setColor(ContextCompat.getColor(this,R.color.color_green));

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.7f);

        barChart.setData(data);
        barChart.invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //statistics();
    }


    public void set_defaultData(){
        Items_adapter items_adapter = new Items_adapter(this,values_firstGridint,values_firstGridstr);
        gridView_stats.setAdapter(items_adapter);

        Items_adapter items_adapterPrices = new Items_adapter(this,values_secondGridint,values_secondGridstr);
        gridView_prices.setAdapter(items_adapterPrices);


        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1,1f));
        barEntries.add(new BarEntry(2,2f));
        barEntries.add(new BarEntry(3,3f));
        barEntries.add(new BarEntry(4,4f));

        BarDataSet barDataSet = new BarDataSet(barEntries,"L/100km Recientes");
        barDataSet.setColor(ContextCompat.getColor(this,R.color.color_green));

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.7f);

        barChart.setData(data);
        barChart.invalidate();
    }
}
