package com.example.BerthaApp.Maps;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;

public class UserLocation {

    private GeoPoint geoPoint;
    private @ServerTimestamp Date timestamp;
    private String idUser;

    public UserLocation(GeoPoint geoPoint, Date timestamp, String idUser) {
        this.geoPoint = geoPoint;
        this.timestamp = timestamp;
        this.idUser = idUser;
    }

    public UserLocation() {
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
