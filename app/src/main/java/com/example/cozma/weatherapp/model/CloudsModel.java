package com.example.cozma.weatherapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cozma on 03.11.2017.
 */

public class CloudsModel {
    @SerializedName("all")
    private Integer all;

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }
}
