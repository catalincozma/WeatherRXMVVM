package com.example.cozma.weatherapp;

import android.app.Application;

/**
 * Created by cozma on 05.11.2017.
 */

public class MainApp extends Application implements ServiceProvider {

    private static MainApp instance;

    private WeatherService weatherService;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        weatherService = new WeatherService(this);
    }

    public static ServiceProvider getInstance() {
        return instance;
    }

    @Override
    public WeatherService getWheatherService() {
        return weatherService;
    }
}
