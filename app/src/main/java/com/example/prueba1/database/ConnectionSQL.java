package com.example.prueba1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConnectionSQL  extends SQLiteOpenHelper {


    final String table_users = "CREATE TABLE  Users ( id  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  name TEXT NOT NULL,  last_name TEXT NOT NULL,  email TEXT NOT NULL,  password  TEXT NOT NULL,  drive_mode_def TEXT );";

    private String table_cars = "CREATE TABLE Cars (id INTEGER NOT NULL PRIMARY KEY  AUTOINCREMENT,make  TEXT NOT NULL,model  TEXT NOT NULL,year NUMERIC NOT NULL,license_plate  TEXT,fuel_type  TEXT,weigh_kg TEXT,model_trim  TEXT,engine_cc TEXT,length_mm INTEGER,width_mm INTEGER,height_mm INTEGER,mpg_hwy INTEGER,mpg_city INTEGER,mpg_mixed INTEGER,body_style  TEXT,door_number integer,drive  TEXT,engine_position  TEXT,engine_type  TEXT);";

    private String table_challenges ="CREATE TABLE Challenges (id INTEGER NOT NULL PRIMARY KEY  AUTOINCREMENT,name  TEXT NOT NULL,created_at TEXT NOT NULL,end_TEXT TEXT NOT NULL,user_id INTEGER NOT NULL,FOREIGN KEY (user_id) REFERENCES Users(id));";

    private String table_user_car ="CREATE TABLE user_car (id INTEGER NOT NULL PRIMARY KEY  AUTOINCREMENT,id_user INTEGER NOT NULL,id_car INTEGER NOT NULL,FOREIGN KEY (id_user) REFERENCES Users(id),FOREIGN KEY (id_car) REFERENCES Cars(id));";

    private String table_trips ="CREATE TABLE Trips (id INTEGER NOT NULL PRIMARY KEY  AUTOINCREMENT,departure_place  TEXT,arrive_place  TEXT,departure_hour TEXT,arrived_hour TEXT,user_car_trip INTEGER NOT NULL, score INTEGER, total_distance_km TEXT, average_speed TEXT, fuel_consumption TEXT, tokens_collected TEXT, co2_emissions_kg TEXT, travel_alone numeric, number_passenger integer, cargo_roof numeric,cargo_back numeric, price_per_liter TEXT, FOREIGN KEY(user_car_trip) REFERENCES user_car(id));";

    private String table_alert_catalog ="CREATE TABLE Alert_catalog ( id INTEGER NOT NULL PRIMARY KEY  AUTOINCREMENT, description  TEXT, code TEXT );";

    private String table_group_catalog ="CREATE TABLE Group_catalog ( id INTEGER NOT NULL PRIMARY KEY  AUTOINCREMENT, name  TEXT, description  TEXT);";

    private String table_alerts_log ="CREATE TABLE Alerts_log ( id INTEGER NOT NULL PRIMARY KEY  AUTOINCREMENT, TEXT TEXT, TEXT TEXT, id_alert INTEGER, trip_id INTEGER, id_user_car INTEGER, km_number INTEGER, altitude TEXT, latitude TEXT, longitude TEXT, speed_at_alert TEXT, fuel_consumption TEXT, FOREIGN KEY(trip_id) REFERENCES Trips(id), FOREIGN KEY(id_user_car) REFERENCES user_car(id), FOREIGN KEY(id_alert) REFERENCES Alert_catalog(id));";

    private String table_challenges_x_trip ="CREATE TABLE challenges_X_trip ( id INTEGER NOT NULL PRIMARY KEY  AUTOINCREMENT, id_challenge INTEGER, id_trip INTEGER, tokens_collected INTEGER, group_id INTEGER, FOREIGN KEY(group_id) REFERENCES Group_catalog(id), FOREIGN KEY(id_challenge) REFERENCES Challenges(id), FOREIGN KEY(id_trip) REFERENCES Trips(id));";

    private String table_groups_per_user ="CREATE TABLE Groups_per_user ( id INTEGER NOT NULL PRIMARY KEY  AUTOINCREMENT, id_group_code INTEGER, id_user_car INTEGER, FOREIGN KEY(id_group_code) REFERENCES Group_catalog(id), FOREIGN KEY(id_user_car) REFERENCES user_car(id))";

    private String table_fuel_control_log ="CREATE TABLE Fuel_control_log ( id INTEGER NOT NULL PRIMARY KEY  AUTOINCREMENT, TEXT TEXT, TEXT TEXT,odometer_current_value INTEGER, liters_quantity TEXT, total_price TEXT, price_per_liter TEXT, fuel_type  TEXT, city_driving_percent  TEXT, ud_user_car INTEGER, FOREIGN KEY(ud_user_car) REFERENCES user_car(id));";

    private String table_new_drive_inputs ="CREATE TABLE new_drive_inputs ( passenger_weight TEXT, roof_cargo TEXT, back_cargo TEXT);";

    private String table_all_cars ="CREATE TABLE All_cars ( id INTEGER NOT NULL PRIMARY KEY  AUTOINCREMENT, make  TEXT, model  TEXT, year  TEXT, make_display  TEXT, model_display  TEXT);";
    
    public ConnectionSQL(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table_users);
        db.execSQL(table_cars);
        db.execSQL(table_challenges);
        db.execSQL(table_user_car);
        db.execSQL(table_trips);
        db.execSQL(table_alert_catalog);
        db.execSQL(table_group_catalog);
        db.execSQL(table_alerts_log);
        db.execSQL(table_challenges_x_trip);
        db.execSQL(table_groups_per_user);
        db.execSQL(table_fuel_control_log);
        db.execSQL(table_new_drive_inputs);
        db.execSQL(table_all_cars);

   }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists table_users");
        db.execSQL("drop table if exists table_cars");
        db.execSQL("drop table if exists table_challenges");
        db.execSQL("drop table if exists table_user_car");
        db.execSQL("drop table if exists table_all_cars");
        db.execSQL("drop table if exists table_trips");
        db.execSQL("drop table if exists table_group_catalog");
        db.execSQL("drop table if exists table_alert_catalog");
        db.execSQL("drop table if exists table_alerts_log");
        db.execSQL("drop table if exists table_challenges_x_trip");
        db.execSQL("drop table if exists table_groups_per_user");
        db.execSQL("drop table if exists table_fuel_control_log");
        db.execSQL("drop table if exists table_new_drive_inputs");
        db.execSQL("drop table if exists table_all_cars");



    }
}
