package com.example.prueba1.Challenges;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.prueba1.R;

import java.util.ArrayList;
import java.util.List;

public class Groups_adapter extends ArrayAdapter {

    private ArrayList<Groups> list_groups   ;

    private Context mContext;
    int mResource;


    public Groups_adapter(Context context,int resource,  ArrayList<Groups> objects) {
        super(context ,resource, objects);
        this.list_groups = objects;
        mResource = resource;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        Groups group = list_groups.get(position);

        TextView group_name = convertView.findViewById(R.id.textView_nameGroup);

        group_name.setText(group.getName());

        return convertView;

    }

}
