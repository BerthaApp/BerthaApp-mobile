package com.example.prueba1.FuelControl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.prueba1.R;

import java.text.DecimalFormat;

public class Items_adapter extends BaseAdapter {

    private Context context;
    private final float [] statistcs_number;
    private final String[] statistcs_text;
    View view;
    LayoutInflater layoutInflater;

    public Items_adapter(Context context, float[] statistcs_number, String[] statistcs_text) {
        this.context = context;
        this.statistcs_number = statistcs_number;
        this.statistcs_text = statistcs_text;
    }

    @Override
    public int getCount() {
        return statistcs_number.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if(convertView == null) {
            view = new View(context);
            view = layoutInflater.inflate(R.layout.fuel_control_items,null);
            TextView textViewNum = view.findViewById(R.id.big_text);
            TextView textViewStr = view.findViewById(R.id.small_text);

            textViewNum.setText(df.format(statistcs_number[position]));
            textViewStr.setText(statistcs_text[position]);
        }

        return view;
    }
}
