package com.example.prueba1.Pattern;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.prueba1.Challenges.Challenges;
import com.example.prueba1.Challenges.Groups;
import com.example.prueba1.Maps.Road_mark;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Singleton {

    private static Singleton mInstance;
    private RequestQueue requestQueue;
    private static Context mContext;
    private static ArrayList<Groups> list_groups = new ArrayList<>();
    private static ArrayList<Challenges> list_challenges = new ArrayList<>();
    private static ArrayList<Road_mark> list_roadMarkers= new ArrayList<>();



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

    public void addList_groups(Groups groups){
       list_groups.add(groups);
    }

    public void addList_challenges(Challenges challenges){
        list_challenges.add(challenges);
    }

    public void clearList_groups(){
        list_groups.clear();
    }

    public void clearList_challenges(){
        list_challenges.clear();
    }

    public static ArrayList<Road_mark> getList_roadMarkers() {
        return list_roadMarkers;
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

    public void link_myChallenges(ArrayList<Integer> list_myChallenges){
        if(!list_challenges.isEmpty()){
            for(Integer i : list_myChallenges){
                for(Challenges challenges : list_challenges){
                    if(challenges.getId() == i){
                        Log.e("lista id", "here + "+challenges.getName());
                        challenges.setMyChallenge(true);
                    }
                }
            }
        }
    }

    public ArrayList<Challenges> getList_MyChallenges(){
        ArrayList<Challenges> my_challenges = new ArrayList<>();
        if(!list_challenges.isEmpty()) {
            for (Challenges i : list_challenges) {
                if (i.isMyChallenge()) {
                    my_challenges.add(i);
                }
            }
        }

        return my_challenges;
    }

    public Challenges getChallengeById(int id){
        for(Challenges i : list_challenges){
            if(i.getId() == id){
                return i;
            }
        }
        return null;
    }


    public void default_data(){
        list_roadMarkers.add(new Road_mark(0,"First Mark",9.944366, -84.149213,10));
        list_roadMarkers.add(new Road_mark(0,"Second Mark",9.939440, -84.140825,10));
        list_roadMarkers.add(new Road_mark(0,"Third Mark",9.936281, -84.132213,10));
        list_roadMarkers.add(new Road_mark(0,"Fourth Mark",9.935464, -84.121021,10));
    }
}
