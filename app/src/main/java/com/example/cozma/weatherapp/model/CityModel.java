package com.example.cozma.weatherapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cozma on 03.11.2017.
 */

public class CityModel {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("coord")
    private CoordModel coord;
    @SerializedName("country")
    private String country;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordModel getCoord() {
        return coord;
    }

    public void setCoord(CoordModel coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
