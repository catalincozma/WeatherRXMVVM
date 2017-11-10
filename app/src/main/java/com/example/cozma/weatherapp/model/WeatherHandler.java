package com.example.cozma.weatherapp.model;


/**
 * Created by cozma on 04.11.2017.
 */

public class WeatherHandler {

//    // singleton stuff
//    private static WeatherHandler ourInstance = new WeatherHandler();
//
//    public static WeatherHandler getInstance() {
//        return ourInstance;
//    }
//
//
//    public static WeatherHandler getOurInstance() {
//        return ourInstance;
//    }
//
//    public static void setOurInstance(WeatherHandler ourInstance) {
//        WeatherHandler.ourInstance = ourInstance;
//    }
//
//
//    public static void resetInstance() {
//        WeatherHandler.ourInstance = new WeatherHandler();
//    }
//
//    private WeatherHandler() {
//    }
//
//
//    private WeatherDataModel weatherDataModels = new WeatherDataModel();
//    private List< WeatherData > myWether = new ArrayList<>();
//
//    public WeatherData getWeatherData() {
//        return weatherData;
//    }
//
//    public void setWeatherData(WeatherData weatherData) {
//        this.weatherData = weatherData;
//        notifyListeners();
//    }
//
//    public void notifyListeners() {
//        for(WeatherListener listener : listeners) {
//            listener.onWeatherForMyLocation(this.weatherData);
//        }
//    }
//
//    public void addListener(WeatherListener listener) {
//        this.listeners.add(listener);
//    }
//
//    public void removeListener(WeatherListener listener) {
//        this.listeners.remove(listener);
//    }
//
//    private WeatherData weatherData = new WeatherData();
//
//    public List<WeatherData> getMyWether() {
//        return myWether;
//    }
//
//    public void setMyWether(List<WeatherData> myWether) {
//        this.myWether = myWether;
//    }
//
//    public WeatherDataModel getWeatherDataModels() {
//        return weatherDataModels;
//    }
//
//    public void setWeatherDataModels(WeatherDataModel weatherDataModels) {
//        this.weatherDataModels = weatherDataModels;
//    }
//
//    ArrayList<WeatherListener> listeners = new ArrayList<>();
//
//    public interface WeatherListener{
//        void onWeatherForMyLocation(WeatherData data);
//    }
}
