package com.example.BerthaApp.FuelControl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.BerthaApp.Challenges.Groups_adapter;
import com.example.BerthaApp.Pattern.Singleton;
import com.example.BerthaApp.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class list_logs extends AppCompatActivity {

    private static final String TAG = "list_logs";



    private ListView listView_allLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_logs);

        listView_allLogs = findViewById(R.id.listview_allLogs);
        List_logs_adapter adapter = new List_logs_adapter(this, R.layout.fuel_log_item,Singleton.getInstance(this).getList_fuelLogs());
        listView_allLogs.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(this, FuelControlActivity.class));
        finish();
    }
}
