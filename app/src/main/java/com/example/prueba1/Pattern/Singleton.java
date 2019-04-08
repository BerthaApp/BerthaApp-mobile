package com.example.prueba1.Pattern;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.prueba1.Challenges.Challenges;
import com.example.prueba1.Challenges.Groups;

import java.util.ArrayList;

public class Singleton {

    private static Singleton mInstance;
    private RequestQueue requestQueue;
    private static Context mContext;
    private static ArrayList<Groups> list_groups = new ArrayList<>();
    private static ArrayList<Challenges> list_challenges = new ArrayList<>();


    private Singleton(Context context){
        mContext = context;
        requestQueue = getRequestQueue();

    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized Singleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new Singleton(context);
        }

        return mInstance;
    }

    public<T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }

    public ArrayList<Groups> getList_groups(){
        return list_groups;
    }

    public void setList_groups(Groups groups){
       list_groups.add(groups);
    }

    public ArrayList<String> return_groups_name(){
        ArrayList<String> list_groupsNames = new ArrayList<>();

        for(Groups i : list_groups){
            list_groupsNames.add(i.getName());
        }

        return list_groupsNames;
    }

    public ArrayList<String> return_challenges_name(){
        ArrayList<String> list_challengesNames = new ArrayList<>();

        for(Challenges i : list_challenges){
            list_challengesNames.add(i.getName());
        }

        return list_challengesNames;
    }
}
