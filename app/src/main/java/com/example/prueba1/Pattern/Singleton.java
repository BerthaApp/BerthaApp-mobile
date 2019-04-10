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

    public ArrayList<Challenges> getList_challenges(){
        return list_challenges;
    }

    public void setList_groups(Groups groups){
       list_groups.add(groups);
    }

    public void setList_challenges(Challenges challenges){
        list_challenges.add(challenges);
    }

    public void clearList_groups(){
        list_groups.clear();
    }

    public void clearList_challenges(){
        list_challenges.clear();
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

    public void default_data(){
        list_groups.add(new Groups(0,"Nombre","Descripcion"));
        list_challenges.add(new Challenges(0,"Challenge1","30/05/19","Grupo"));
        list_challenges.add(new Challenges(1,"Challenge2","30/05/19","General"));
        list_challenges.add(new Challenges(2,"Challenge3","30/05/19","Grupo"));
        list_challenges.add(new Challenges(3,"Challenge4","30/05/19","General"));
        list_challenges.add(new Challenges(4,"Challenge5","30/05/19","Grupo"));
        list_challenges.add(new Challenges(5,"Challenge6","30/05/19","General"));
    }
}
