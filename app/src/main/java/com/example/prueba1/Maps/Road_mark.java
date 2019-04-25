package com.example.prueba1.Maps;

import com.google.firebase.firestore.GeoPoint;

public class Road_mark {

    int id;
    String name_marker;
    Double latitud;
    Double longitude;
    int tokens;

    public Road_mark(int id, String name_marker, Double latitud, Double longitude, int tokens) {
        this.id = id;
        this.name_marker = name_marker;
        this.latitud = latitud;
        this.longitude = longitude;
        this.tokens = tokens;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_marker() {
        return name_marker;
    }

    public void setName_marker(String name_marker) {
        this.name_marker = name_marker;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }
}
