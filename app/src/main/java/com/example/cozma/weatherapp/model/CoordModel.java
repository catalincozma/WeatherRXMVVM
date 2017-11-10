package com.example.cozma.weatherapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cozma on 03.11.2017.
 */

public class CoordModel {

    @SerializedName("lat")
    private Double lat;
    @SerializedName("lon")
    private Double lon;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

}
