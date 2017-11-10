package com.example.cozma.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cozma on 03.11.2017.
 */

public class WeatherDataModel {
    @SerializedName("cod")
    private String cod;
    @SerializedName("message")
    private Double message;
    @SerializedName("cnt")
    private Integer cnt;
    @SerializedName("list")
    private java.util.List<DetailsModel> list = new ArrayList<>();
    @SerializedName("city")
    private CityModel cityModel;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public List<DetailsModel> getList() {
        return list;
    }

    public void setList(List<DetailsModel> list) {
        this.list = list;
    }

    public CityModel getCity() {
        return cityModel;
    }

    public void setCity(CityModel city) {
        this.cityModel = city;
    }
}





