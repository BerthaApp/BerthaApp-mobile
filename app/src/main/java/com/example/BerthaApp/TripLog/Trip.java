package com.example.BerthaApp.TripLog;

public class Trip {

    private String date;
    private String departure_hour;
    private String arrive_hour;
    private String departure_place;
    private String arrive_place;
    private String avg_speed;
    private String avg_literKm;
    private String avg_fuelCost;
    private String time_travel;

    public Trip(String date, String departure_hour, String arrive_hour, String departure_place, String arrive_place, String avg_speed, String avg_literKm, String avg_fuelCost, String time_travel) {
        this.date = date;
        this.departure_hour = departure_hour;
        this.arrive_hour = arrive_hour;
        this.departure_place = departure_place;
        this.arrive_place = arrive_place;
        this.avg_speed = avg_speed;
        this.avg_literKm = avg_literKm;
        this.avg_fuelCost = avg_fuelCost;
        this.time_travel = time_travel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeparture_hour() {
        return departure_hour;
    }

    public void setDeparture_hour(String departure_hour) {
        this.departure_hour = departure_hour;
    }

    public String getArrive_hour() {
        return arrive_hour;
    }

    public void setArrive_hour(String arrive_hour) {
        this.arrive_hour = arrive_hour;
    }

    public String getDeparture_place() {
        return departure_place;
    }

    public void setDeparture_place(String departure_place) {
        this.departure_place = departure_place;
    }

    public String getArrive_place() {
        return arrive_place;
    }

    public void setArrive_place(String arrive_place) {
        this.arrive_place = arrive_place;
    }

    public String getAvg_speed() {
        return avg_speed;
    }

    public void setAvg_speed(String avg_speed) {
        this.avg_speed = avg_speed;
    }

    public String getAvg_literKm() {
        return avg_literKm;
    }

    public void setAvg_literKm(String avg_literKm) {
        this.avg_literKm = avg_literKm;
    }

    public String getAvg_fuelCost() {
        return avg_fuelCost;
    }

    public void setAvg_fuelCost(String avg_fuelCost) {
        this.avg_fuelCost = avg_fuelCost;
    }

    public String getTime_travel() {
        return time_travel;
    }

    public void setTime_travel(String time_travel) {
        this.time_travel = time_travel;
    }
}
