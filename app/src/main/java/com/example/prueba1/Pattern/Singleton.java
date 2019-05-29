package com.example.prueba1.Pattern;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.prueba1.Challenges.Challenges;
import com.example.prueba1.Challenges.Groups;
import com.example.prueba1.FuelControl.Log_object;
import com.example.prueba1.Maps.Road_mark;
import com.example.prueba1.Profile.My_Cars;
import com.example.prueba1.TripLog.Trip;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Singleton {

    private static final String TAG = "Singleton";

    private static Singleton mInstance;
    private RequestQueue requestQueue;
    private static Context mContext;
    private static ArrayList<Groups> list_groups = new ArrayList<>();
    private static ArrayList<Challenges> list_challenges = new ArrayList<>();
    private static ArrayList<Road_mark> list_roadMarkers= new ArrayList<>();
    private static ArrayList<My_Cars> list_myCars= new ArrayList<>();
    private static ArrayList<Trip> list_myTrips= new ArrayList<>();
    private static ArrayList<Log_object> list_fuelLogs= new ArrayList<>();



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
        Log.e(TAG, "getInstance: " );
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

    public static ArrayList<My_Cars> getList_myCars() {
        return list_myCars;
    }

    public static ArrayList<Trip> getList_myTrips() {
        return list_myTrips;
    }

    public static ArrayList<Log_object> getList_fuelLogs() {
        return list_fuelLogs;
    }

    public void addList_groups(Groups groups){
       list_groups.add(groups);
    }

    public void addList_trips(Trip trip){
        list_myTrips.add(trip);
    }

    public void addList_challenges(Challenges challenges){
        list_challenges.add(challenges);
    }

    public void addList_cars(My_Cars car){
        list_myCars.add(car);
    }

    public void addList_fuelLogs(Log_object log){
        list_fuelLogs.add(log);
    }


    public void clearList_groups(){
        list_groups.clear();
    }

    public void clearList_challenges(){
        list_challenges.clear();
    }

    public void clearList_cars(){
        list_myCars.clear();
    }

    public void clearList_trips(){
        list_myTrips.clear();
    }

    public void clearList_fuelLogs(){
        list_fuelLogs.clear();
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

    public ArrayList<String> getList_myCarsString(){
        ArrayList<String> listString_myCars = new ArrayList<>();
        for(My_Cars i: list_myCars){
            listString_myCars.add(i.getMake()+" - "+i.getModel());
        }
        return listString_myCars;
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

    public int getPositionIdCar(int idCar){
        int i = 0;
        for(My_Cars my_cars : list_myCars){
            if(my_cars.getId() == idCar){
                return i;
            }
            i++;
        }
        return -1;
    }


    public void default_data(){
        list_roadMarkers.add(new Road_mark(0,"First Mark",9.944366, -84.149213,10));
        list_roadMarkers.add(new Road_mark(0,"Second Mark",9.939440, -84.140825,10));
        list_roadMarkers.add(new Road_mark(0,"Third Mark",9.936281, -84.132213,10));
        list_roadMarkers.add(new Road_mark(0,"Fourth Mark",9.935464, -84.121021,10));

        list_myTrips.add(new Trip("Sep 21", "6:00am","7:00am", "San Rafael Escazu", "Av. 10 San Jose", "11.2", "12", "$3.9", "60"));
        list_myTrips.add(new Trip("Sep 21", "6:00am","7:00am", "San Rafael Escazu", "Av. 10 San Jose", "11.2", "12", "$3.9", "60"));
        list_myTrips.add(new Trip("Sep 21", "6:00am","7:00am", "San Rafael Escazu", "Av. 10 San Jose", "11.2", "12", "$3.9", "60"));
        list_myTrips.add(new Trip("Sep 21", "6:00am","7:00am", "San Rafael Escazu", "Av. 10 San Jose", "11.2", "12", "$3.9", "60"));
        list_myTrips.add(new Trip("Sep 21", "6:00am","7:00am", "San Rafael Escazu", "Av. 10 San Jose", "11.2", "12", "$3.9", "60"));
        list_myTrips.add(new Trip("Sep 21", "6:00am","7:00am", "San Rafael Escazu", "Av. 10 San Jose", "11.2", "12", "$3.9", "60"));

    }
}
