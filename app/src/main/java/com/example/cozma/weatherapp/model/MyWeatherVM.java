package com.example.cozma.weatherapp.model;

import com.example.cozma.weatherapp.ServiceProvider;
import com.example.cozma.weatherapp.WeatherFireBaseService;
import com.example.cozma.weatherapp.WeatherService;
import com.example.cozma.weatherapp.util.Utils;

import java.util.ArrayList;

import rx.Observer;
import rx.subjects.PublishSubject;

/**
 * Created by cozma on 04.11.2017.
 */


public class MyWeatherVM implements WeatherService.WeatherListener {


    public WeatherService.WeatherListener weatherListener, cityWeatherListener;
    public ServiceProvider serviceProvider;
    public ArrayList<CityDataModel> cityDataModelsList = new ArrayList<>();
    WeatherService weatherService;
    public PublishSubject<ArrayList<CityDataModel>> searchObservable;
    public PublishSubject<WeatherDataModel> myLocationCityObservable;

    private ArrayList<Integer> myCities = new ArrayList<>();
    public ArrayList<WeatherDataModel> citiesWeatherList = new ArrayList<>();
    private WeatherFireBaseService cityDeleteFirebase = new WeatherFireBaseService();

    public MyWeatherVM(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
        weatherService = serviceProvider.getWheatherService();
        weatherService.updateMyWeatherLocation();
        weatherService.weatherListener = this;
        cityDataModelsList = weatherService.readCitiesFromFile();
        searchObservable = PublishSubject.create();
        myLocationCityObservable = PublishSubject.create();

        /**observer for my location*/
        observerMyLocation();
    }

    private void observerMyLocation() {
        weatherService.myLocationObservable.subscribe(new Observer<WeatherDataModel>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(WeatherDataModel cityDataModel) {
                myLocationCityObservable.onNext(cityDataModel);
                Utils.showObjectLog("VM", cityDataModel);
            }
        });
    }


    /**Result for added city*/
    @Override
    public void onWeatherForCity(WeatherDataModel modelCity) {
        Utils.showObjectLog("ModelCity", modelCity);
        myCities.add(modelCity.getCity().getId());
        cityWeatherListener.onWeatherForCity(modelCity);

    }

    /**search new city and return suggestions*/
    public void searchCity(String searchText) {
        ArrayList<CityDataModel> finaleresults = new ArrayList<>();
        for (CityDataModel city : cityDataModelsList) {
            if (city.getName().contains(searchText) && !myCities.contains(city.getId())) {
                finaleresults.add(city);
            }
        }

        searchObservable.onNext(finaleresults);
    }

    /**Delete city selected from UI and Firebase*/
    public void deleteCityFromFireBase(int position) {
        String deleteElementFromFirebase = citiesWeatherList.get(position).getCity().getName();
        cityDeleteFirebase.deleteCityFromFireBase(deleteElementFromFirebase);
        citiesWeatherList.remove(position);
    }

    /**
     * get weather for a new city
     */
    public void getWeatherForCity(CityDataModel city) {
        weatherService.requestWeatherForCity(city);
    }

}



