package com.marlys.myweather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityWeather {

    private City city;

    @SerializedName("list")
    private List<Weather> weeklyWeather;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Weather> getWeeklyWeather() {
        return weeklyWeather;
    }

    public void setWeeklyWeather(List<Weather> weeklyWeather) {
        this.weeklyWeather = weeklyWeather;
    }
}
