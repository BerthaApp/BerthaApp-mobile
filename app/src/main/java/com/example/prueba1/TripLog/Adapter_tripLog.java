package com.example.prueba1.TripLog;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prueba1.R;

public class Adapter_tripLog extends BaseAdapter {

    private Context context;
    private final String[] tripLog_value;
    private final Integer[] list_images = {R.drawable.ic_action_avgspeed,
                                            R.drawable.ic_action_avgliters,
                                            R.drawable.ic_action_avgfuel,
                                            R.drawable.ic_action_avgtime};
    View view;
    LayoutInflater layoutInflater;


    private final String[] tripLog_measures = {"km/h", "L/100km","Fuel","Hours"};

    public Adapter_tripLog(Context context, String[] tripLog_value) {
        this.context = context;
        this.tripLog_value = tripLog_value;
    }

    @Override
    public int getCount() {
        return tripLog_value.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = new View(context);
            view = layoutInflater.inflate(R.layout.trip_log_items, null);
            TextView textViewValue = view.findViewById(R.id.textview_value);
            TextView textViewMeasureType = view.findViewById(R.id.textview_measureType);
            ImageView imageView = view.findViewById(R.id.imageview_triplog);

            textViewValue.setText(tripLog_value[position]);
            textViewMeasureType.setText(tripLog_measures[position]);
            imageView.setImageResource(list_images[position]);
        }

        return view;
    }
}



