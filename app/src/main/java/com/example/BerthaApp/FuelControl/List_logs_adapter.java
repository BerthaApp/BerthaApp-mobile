package com.example.BerthaApp.FuelControl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.BerthaApp.R;

import java.util.ArrayList;

public class List_logs_adapter extends ArrayAdapter {

    private ArrayList<Log_object> list_logs;

    private Context mContext;
    int mResource;


    public List_logs_adapter(Context context,int resource,  ArrayList<Log_object> objects) {
        super(context ,resource, objects);
        this.list_logs = objects;
        mResource = resource;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        Log_object log_object = list_logs.get(position);

        TextView date = convertView.findViewById(R.id.textview_date);
        TextView place = convertView.findViewById(R.id.textview_place);
        TextView liters = convertView.findViewById(R.id.textview_liters);
        TextView price = convertView.findViewById(R.id.textview_price);

        date.setText(log_object.getDate().split("T")[0]);
        place.setText(log_object.getPlace_fuelUp());
        liters.setText("Lts: "+log_object.getLiters_qtty());
        price.setText("₡"+log_object.getTotal_price());


        return convertView;

    }
}
