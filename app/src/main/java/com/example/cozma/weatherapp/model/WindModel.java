package com.example.cozma.weatherapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cozma on 03.11.2017.
 */

public class WindModel {

    @SerializedName("speed")
    private Double speed;
    @SerializedName("deg")
    private Double deg;

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDeg() {
        return deg;
    }

    public void setDeg(Double deg) {
        this.deg = deg;
    }
}
