package com.example.prueba1.FuelControl;

public class Log_object {
    private int id;
    private String date;
    private String time;
    private float odometer_current;
    private float liters_qtty;
    private float total_price;
    private float price_perLiter;
    private String fuel_type;
    private float km_traveled;

    public Log_object(int id, String date, String time, float odometer_current, float liters_qtty, float total_price, float price_perLiter, String fuel_type, float km_traveled) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.odometer_current = odometer_current;
        this.liters_qtty = liters_qtty;
        this.total_price = total_price;
        this.price_perLiter = price_perLiter;
        this.fuel_type = fuel_type;
        this.km_traveled = km_traveled;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getOdometer_current() {
        return odometer_current;
    }

    public void setOdometer_current(float odometer_current) {
        this.odometer_current = odometer_current;
    }

    public float getLiters_qtty() {
        return liters_qtty;
    }

    public void setLiters_qtty(float liters_qtty) {
        this.liters_qtty = liters_qtty;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public float getPrice_perLiter() {
        return price_perLiter;
    }

    public void setPrice_perLiter(float price_perLiter) {
        this.price_perLiter = price_perLiter;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public float getKm_traveled() {
        return km_traveled;
    }

    public void setKm_traveled(float km_traveled) {
        this.km_traveled = km_traveled;
    }
}
