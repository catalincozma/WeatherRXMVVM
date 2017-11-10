package com.example.cozma.weatherapp.model;

/**
 * Created by cozma on 05.11.2017.
 */

public class WeatherCityModel {
    WeatherDataModel weatherDataModel = new WeatherDataModel();
    public WeatherDataModel getWeatherData() {
        return weatherDataModel;
    }

    public void setWeatherDataModel(WeatherDataModel weatherDataModel) {
        this.weatherDataModel = weatherDataModel;
    }

}
