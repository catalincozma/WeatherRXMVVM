package com.example.cozma.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cozma on 03.11.2017.
 */

public class DetailsModel {

    @SerializedName("dt")
    private Integer dt;
    @SerializedName("main")
    private MainModel main;
    @SerializedName("weather")
    private java.util.List<WeatherModel> weather = null;
    @SerializedName("clouds")
    private CloudsModel clouds;
    @SerializedName("wind")
    private WindModel wind;
    @SerializedName("rain")
    private RainModel rain;
    @SerializedName("sys")
    private SysModel sys;
    @SerializedName("dt_txt")
    private String dtTxt;

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public MainModel getMain() {
        return main;
    }

    public void setMain(MainModel main) {
        this.main = main;
    }

    public List<WeatherModel> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherModel> weather) {
        this.weather = weather;
    }

    public CloudsModel getClouds() {
        return clouds;
    }

    public void setClouds(CloudsModel clouds) {
        this.clouds = clouds;
    }

    public WindModel getWind() {
        return wind;
    }

    public void setWind(WindModel wind) {
        this.wind = wind;
    }

//    public RainModel getRain() {
//        return rain;
//    }
//
//    public void setRain(RainModel rain) {
//        this.rain = rain;
//    }
//
//    public SysModel getSys() {
//        return sys;
//    }
//
//    public void setSys(SysModel sys) {
//        this.sys = sys;
//    }
//
//    public String getDtTxt() {
//        return dtTxt;
//    }
//
//    public void setDtTxt(String dtTxt) {
//        this.dtTxt = dtTxt;
//    }

}
