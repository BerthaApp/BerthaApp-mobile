package com.example.BerthaApp.FuelControl;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.BerthaApp.Challenges.ChallengeDescription;
import com.example.BerthaApp.Challenges.Groups_adapter;
import com.example.BerthaApp.Pattern.Singleton;
import com.example.BerthaApp.R;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


///This class is in charge to list all logs..
///Also if the user keeps pressed a log it shows a message to delete it
public class list_logs extends AppCompatActivity {

    private static final String TAG = "list_logs";



    private ListView listView_allLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_logs);

        listView_allLogs = findViewById(R.id.listview_allLogs);
        Singleton.getInstance(this);
        List_logs_adapter adapter = new List_logs_adapter(this, R.layout.fuel_log_item, Singleton.getList_fuelLogs());
        listView_allLogs.setAdapter(adapter);


        listView_allLogs.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(list_logs.this)
                        .setTitle("Borrar registro")
                        .setMessage("Desea borrar el registro?")
                        .setIcon(R.drawable.group40x)
                        .setPositiveButton("Sí",
                                new DialogInterface.OnClickListener() {
                                    @TargetApi(11)
                                    public void onClick(DialogInterface dialog, int id) {
                                        Toast.makeText(list_logs.this, "Borrado exitosamente", Toast.LENGTH_SHORT).show();
                                        delete_log(Singleton.getList_fuelLogs().get(position));
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        }).show();
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(this, FuelControlActivity.class));
        finish();
    }


    private static final String url_link_deleteLog = "https://evening-oasis-22037.herokuapp.com/fuelLog/delete_log/";
    public void delete_log(final Log_object log_object){

        StringRequest postRequest = new StringRequest(Request.Method.POST, url_link_deleteLog,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response Link", response);
                        Singleton.delete_log(log_object);
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
                params.put("id_log",String.valueOf(log_object.getId()));

                return params;
            }
        };
        Singleton.getInstance(list_logs.this).addToRequestQueue(postRequest);
        onBackPressed();
    }
}
