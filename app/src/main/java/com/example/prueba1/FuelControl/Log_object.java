package com.example.prueba1.FuelControl;

public class Log_object {
    private int id;
    private String date;
    private String time;
    private int odometer_current;
    private int liters_qtty;
    private double total_price;
    private double price_perLiter;
    private String fuel_type;

    public Log_object(int id, String date, String time, int odometer_current, int liters_qtty, double total_price, double price_perLiter, String fuel_type) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.odometer_current = odometer_current;
        this.liters_qtty = liters_qtty;
        this.total_price = total_price;
        this.price_perLiter = price_perLiter;
        this.fuel_type = fuel_type;
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

    public int getOdometer_current() {
        return odometer_current;
    }

    public void setOdometer_current(int odometer_current) {
        this.odometer_current = odometer_current;
    }

    public int getLiters_qtty() {
        return liters_qtty;
    }

    public void setLiters_qtty(int liters_qtty) {
        this.liters_qtty = liters_qtty;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public double getPrice_perLiter() {
        return price_perLiter;
    }

    public void setPrice_perLiter(double price_perLiter) {
        this.price_perLiter = price_perLiter;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }
}
