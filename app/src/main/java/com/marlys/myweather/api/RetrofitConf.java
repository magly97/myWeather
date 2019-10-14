package com.marlys.myweather.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConf {

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String KEY = "79badf94102e008963c2d50b6cfa43f2";


    private static final Retrofit ourInstance = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static Retrofit getInstance() {
        return ourInstance;
    }

    private RetrofitConf() {
    }
}
