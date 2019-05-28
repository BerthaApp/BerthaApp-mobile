package com.example.prueba1.TripLog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.prueba1.R;

import java.util.ArrayList;

import javax.annotation.Nonnull;

public class Adapter_trips extends ArrayAdapter<Trip> {


    private Context context;
    int mResource;

    public Adapter_trips(Context context, int resource, ArrayList<Trip> objects) {
        super(context,resource,objects);
        this.context = context;
        this.mResource = resource;
    }


    @Nonnull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String date = getItem(position).getDate();
        String departure_hour = getItem(position).getDeparture_hour();
        String arrive_hour = getItem(position).getArrive_hour();
        String departure_place = getItem(position).getDeparture_place();
        String arrive_place = getItem(position).getArrive_place();
        String avg_speed = getItem(position).getAvg_speed();
        String avg_literKm = getItem(position).getAvg_literKm();
        String avg_fuelCost = getItem(position).getAvg_fuelCost();
        String time_travel = getItem(position).getTime_travel();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource,parent,false);


        TextView tv_date = convertView.findViewById(R.id.date);
        TextView tv_departureHour = convertView.findViewById(R.id.departure_hour);
        TextView tv_arriveHour = convertView.findViewById(R.id.arrive_hour);
        TextView tv_departurePlace = convertView.findViewById(R.id.departure_place);
        TextView tv_arrivePlace = convertView.findViewById(R.id.arrive_place);
        TextView tv_avgSpeed = convertView.findViewById(R.id.avg_speed);
        TextView tv_avgLiterKm = convertView.findViewById(R.id.avg_literKm);
        TextView tv_avgFuelCost = convertView.findViewById(R.id.avg_fuelCost);
        TextView tv_timeTravel = convertView.findViewById(R.id.avg_timeTravel);

        tv_date.setText(date);
        tv_departureHour.setText(departure_hour);
        tv_arriveHour.setText(arrive_hour);
        tv_departurePlace.setText(departure_place);
        tv_arrivePlace.setText(arrive_place);
        tv_avgSpeed.setText(avg_speed);
        tv_avgLiterKm.setText(avg_literKm);
        tv_avgFuelCost.setText(avg_fuelCost);
        tv_timeTravel.setText(time_travel);



        return convertView;
    }
}
