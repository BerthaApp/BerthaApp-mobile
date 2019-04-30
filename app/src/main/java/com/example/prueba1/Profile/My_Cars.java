package com.example.prueba1.Profile;

public class My_Cars {

    private int id;
    private String make;
    private String model;
    private String engine;

    public My_Cars(int id, String make, String model, String engine) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.engine = engine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }
}
