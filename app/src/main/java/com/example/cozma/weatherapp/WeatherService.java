package com.example.cozma.weatherapp;

import android.content.Context;
import android.location.Location;

import com.example.cozma.weatherapp.handler.GPSLocation;
import com.example.cozma.weatherapp.model.CityDataModel;
import com.example.cozma.weatherapp.model.WeatherDataModel;
import com.example.cozma.weatherapp.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by cozma on 05.11.2017.
 */

public class WeatherService implements WeatherFireBaseService.GetCitiesFromFireBase {

    private GPSLocation gpsLocationUpdater;
    private WeatherRequest weatherRequest = new WeatherRequest();
    private WeatherFireBaseService citiesFireBaseService = new WeatherFireBaseService();
    public WeatherListener weatherListener;
    private Context context;
    public PublishSubject<WeatherDataModel> myLocationObservable;

    public WeatherService(Context context) {
        this.context = context;
    }

    /***
     * Get init components
     */
    public void updateMyWeatherLocation() {
        gpsLocationUpdater = new GPSLocation(context);
        gpsLocationUpdater.getLocation(gpsLocationResult);
        weatherRequest.getResponseRequestMyLocation(context, responseResultMyLocation);
        citiesFireBaseService.citiesFromFireBaseListener = this;
        citiesFireBaseService.getWeatherCitiesFireBase();
        myLocationObservable = PublishSubject.create();
    }

    /***
     * GPS result
     */
    public GPSLocation.LocationResult gpsLocationResult = new GPSLocation.LocationResult() {
        @Override
        public void gotLocation(Location location) {
            final double lat = location.getLatitude();
            final double lon = location.getLongitude();
            weatherRequest.getMyWeather(lat, lon);
            weatherRequest.myLocation.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherData -> {
                        myLocationObservable.onNext(weatherData);
                    });
        }

    };


    /**
     * Response for city
     */
    public WeatherRequest.ResponseResult responseResultMyLocation = new WeatherRequest.ResponseResult() {
        //response for added cities
        @Override
        public void gotResponseForCity(WeatherDataModel weatherDataModelMyLocation) {
            Utils.showObjectLog("CityModel", weatherDataModelMyLocation);
            weatherListener.onWeatherForCity(weatherDataModelMyLocation);


        }
    };

    /**
     * Read all cities from local json file
     */
    public ArrayList<CityDataModel> readCitiesFromFile() {
        ArrayList<CityDataModel> weatherDataModels = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(Utils.readCityListJson(context));
            JSONArray m_jArry = obj.getJSONArray("cityes");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String name = jo_inside.getString("name");
                String country = jo_inside.getString("country");
                Integer id = jo_inside.getInt("id");
                JSONObject coord_jArry = jo_inside.getJSONObject("coord");
                Double longitude = coord_jArry.getDouble("lon");
                Double latitude = coord_jArry.getDouble("lat");
                weatherDataModels.add(new CityDataModel(id, name, country, latitude, longitude));

            }
            return weatherDataModels;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherDataModels;
    }

    @Override
    public void onCitiesWeatherFromFirebase(ArrayList<CityDataModel> cityDataModel) {
        for (CityDataModel citymodel : cityDataModel) {
            weatherRequest.getWeatherForCity(citymodel.getLatitude(), citymodel.getLongitude());
        }
    }

    public interface WeatherListener {
        void onWeatherForCity(WeatherDataModel modelCity);
    }

    /***
     * Request weather for city
     */
    public void requestWeatherForCity(CityDataModel city) {
        weatherRequest.getWeatherForCity(city.getLatitude(), city.getLongitude());
        citiesFireBaseService.sendCityToFireBase(city);
    }
}
