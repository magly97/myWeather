package com.marlys.myweather.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class CityWeather implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityWeather that = (CityWeather) o;
        return Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city);
    }
}
