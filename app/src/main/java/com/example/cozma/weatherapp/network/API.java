package com.example.cozma.weatherapp.network;

import com.example.cozma.weatherapp.model.WeatherCityModel;
import com.example.cozma.weatherapp.model.WeatherDataModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by cozma on 02.11.2017.
 */

public interface API {

    @GET("forecast")
    Observable<WeatherDataModel> getWeatherMyLocation(@Query("lat") Double latitude,
                                                      @Query("lon") Double longitude,
                                                      @Query("cnt") String cnt,
                                                      @Query("appid") String appid);

    @GET("forecast")
    Call<WeatherDataModel> getWeatherForCity(  @Query("lat") Double latitude,
                                               @Query("lon") Double longitude,
                                               @Query("cnt") String cnt,
                                               @Query("appid") String appid);


}
