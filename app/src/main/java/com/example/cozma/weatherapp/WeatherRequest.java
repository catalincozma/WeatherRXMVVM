package com.example.cozma.weatherapp;

import android.content.Context;

import com.example.cozma.weatherapp.model.WeatherDataModel;
import com.example.cozma.weatherapp.network.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;

import com.example.cozma.weatherapp.util.Utils;

/**
 * Created by cozma on 05.11.2017.
 */

public class WeatherRequest {

    private ResponseResult responseResult;
    private Context context;

    public void getResponseRequestMyLocation(Context context, ResponseResult responseResult){
        this.context = context;
        this.responseResult = responseResult;
    }

    Observable<WeatherDataModel> myLocation;
    /**Request weather for my location*/
    public void getMyWeather(double lat, double lon) {
        myLocation =  RestClient.networkHandler().getWeatherMyLocation(lat,lon,"20",Utils.API_KEY);
    }

    public void getWeatherForCity(double lat, double lon) {
        double latitude = lat;
        double longitude = lon;

        RestClient.networkHandler().getWeatherForCity(latitude,
                longitude,
                "20",
                Utils.API_KEY)
                .enqueue(new Callback<WeatherDataModel>() {
                    @Override
                    public void onResponse(Call<WeatherDataModel> call, Response<WeatherDataModel> response) {
                        responseResult.gotResponseForCity(response.body());
                    }

                    @Override
                    public void onFailure(Call<WeatherDataModel> call, Throwable t) {

                    }
                });
    }


    public static abstract class ResponseResult {
        public abstract void gotResponseForCity(WeatherDataModel weatherDataModelMyLocation);
    }

}
