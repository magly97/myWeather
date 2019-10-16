package com.marlys.myweather.api;

import com.marlys.myweather.model.CityWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMap {

    @GET("forecast/daily")
    Call<CityWeather> getWeatherCity (@Query("q") String city,
                                      @Query("APPID") String key,
                                      @Query("units") String units,
                                      @Query("cnt") int days,
                                      @Query("lang") String lang);

}
