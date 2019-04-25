package com.example.prueba1.Maps;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import com.example.prueba1.Login.MainActivity;
import com.example.prueba1.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.prueba1.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.example.prueba1.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;


public class Main_maps extends AppCompatActivity {

    private static final String TAG = "Main_Maps_Activity";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    private boolean mLocationPermissionGranted = false;

    private FirebaseFirestore mDb;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_maps);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (isServicesOK()) {
            init();
        }
    }

    private void getLastKnownLocation() {
        Log.d(TAG, "getLastKnownLocation: called.");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();

                    GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                    Log.d(TAG, "onComplete: latitude: " + geoPoint.getLatitude());
                    Log.d(TAG, "onComplete: latitude: " + geoPoint.getLongitude());
                }
            }
        });

    }

    private void init(){
        Button btnMap = findViewById(R.id.btn_map);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getFragmentMaps();
                getLastKnownLocation();

            }
        });
    }


    /*public boolean isServicesOK(){
        Log.d(TAG,"isServicesOK: Checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Main_maps.this);

        if(available == ConnectionResult.SUCCESS){
            // Everything is fine
            Log.d(TAG, "isServicesOK: Google Play Services is working");

            return true;
        }

        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //An error ocurred
            Log.d(TAG,"isServicesOK: an error ocurred");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Main_maps.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();

        }else{
            Toast.makeText(this,"You can't make map requests",Toast.LENGTH_SHORT).show();
        }

        return false;
    }*/

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Esta aplicacion requiere GPS, desea encenderlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }



    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;

            getFragmentMaps();
            getLastKnownLocation();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Main_maps.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Main_maps.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "No se pueden realizar consultas al mapa", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted){

                    getFragmentMaps();
                    getLastKnownLocation();

                }
                else{
                    getLocationPermission();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkMapServices()){
            if(mLocationPermissionGranted){

                getFragmentMaps();
                getLastKnownLocation();
            }
            else{
                getLocationPermission();
            }
        }
    }

    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }

    private void getFragmentMaps(){

        map fragment = new map();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_mainMaps,fragment).commit();
/*
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mDb.setFirestoreSettings(settings);

        CollectionReference chatroomsCollection = mDb
                .collection(getString(R.string.collection_chatrooms));

        mChatroomEventListener = chatroomsCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                Log.d(TAG, "onEvent: called.");

                if (e != null) {
                    Log.e(TAG, "onEvent: Listen failed.", e);
                    return;
                }

                if(queryDocumentSnapshots != null){
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                        Chatroom chatroom = doc.toObject(Chatroom.class);
                        if(!mChatroomIds.contains(chatroom.getChatroom_id())){
                            mChatroomIds.add(chatroom.getChatroom_id());
                            mChatrooms.add(chatroom);
                        }
                    }
                    Log.d(TAG, "onEvent: number of chatrooms: " + mChatrooms.size());
                    mChatroomRecyclerAdapter.notifyDataSetChanged();
                }

            }
        });*/
    }





}
