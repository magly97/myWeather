package com.marlys.myweather;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.marlys.myweather.model.CityWeather;
import com.marlys.myweather.model.Weather;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CityWeatherInfoActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView cityName;
    private TextView weatherDesc;
    private TextView weatherTemp;
    private TextView weatherMaxTemp;
    private TextView weatherMinTemp;
    private TextView weatherSunrise;
    private TextView weatherSunset;
    private TextView tempMon;
    private TextView tempDay;
    private TextView tempEve;
    private TextView tempNight;
    private TextView wind;
    private TextView humid;
    private TextView cloud;
    private TextView rain;
    private TextView pressure;

    private CityWeather cityWeather;
    private DateFormat format = new SimpleDateFormat("HH:mm");
    private DecimalFormat formatValue = new DecimalFormat("#.#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather_info);

        ActionBar actionBar = getSupportActionBar();

        this.imageView = findViewById(R.id.weather_image);
        this.cityName = findViewById(R.id.weather_city);
        this.weatherDesc = findViewById(R.id.weather_desc);
        this.weatherTemp = findViewById(R.id.weather_temp);
        this.weatherMaxTemp = findViewById(R.id.weather_max_temp);
        this.weatherMinTemp = findViewById(R.id.weather_min_temp);
        this.weatherSunrise = findViewById(R.id.weather_sunrise_time);
        this.weatherSunset = findViewById(R.id.weather_sunset_time);
        this.tempMon = findViewById(R.id.temp_morn);
        this.tempDay = findViewById(R.id.temp_day);
        this.tempEve = findViewById(R.id.temp_eve);
        this.tempNight = findViewById(R.id.temp_night);

        this.cloud = findViewById(R.id.cloud);
        this.rain = findViewById(R.id.rain);
        this.wind = findViewById(R.id.wind);
        this.humid = findViewById(R.id.humid);
        this.pressure = findViewById(R.id.pressure);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (!bundle.isEmpty()) {
            cityWeather = (CityWeather) bundle.getSerializable("cityWeather");

            Weather weatherToday = cityWeather.getWeeklyWeather().get(0);
            cityName.setText(cityWeather.getCity().getNameWithCity());
            weatherDesc.setText(weatherToday.getWeatherDescriptions().get(0).getDescription());
            weatherTemp.setText(formatValue.format(weatherToday.getTemp().getDay()) + "°");
            weatherMaxTemp.setText(formatValue.format(weatherToday.getTemp().getMax()) + "°");
            weatherMinTemp.setText(formatValue.format(weatherToday.getTemp().getMin()) + "°");

            Date sunrise = new Date(weatherToday.getSunrise());
            weatherSunrise.setText(format.format(sunrise));
            Date sunset = new Date(weatherToday.getSunset());
            weatherSunset.setText(format.format(sunset));
            String icon = weatherToday.getWeatherDescriptions().get(0).getIcon();
            Picasso.get().load("http://openweathermap.org/img/wn/" + icon + "@2x.png").into(imageView);

            tempMon.setText(formatValue.format(weatherToday.getTemp().getMorn()) + "°");
            tempDay.setText(formatValue.format(weatherToday.getTemp().getDay()) + "°");
            tempEve.setText(formatValue.format(weatherToday.getTemp().getEve()) + "°");
            tempNight.setText(formatValue.format(weatherToday.getTemp().getNight()) + "°");

            cloud.setText(formatValue.format(weatherToday.getClouds()) + "%");
            rain.setText(formatValue.format(weatherToday.getRain()) + "mm");
            wind.setText(formatValue.format(weatherToday.getSpeed()) + "m/s");
            humid.setText(formatValue.format(weatherToday.getHumidity()) + "%");
            pressure.setText((int) weatherToday.getPressure() + " hPa");

            actionBar.setTitle(getResources().getString(R.string.weather_for) + cityWeather.getCity().getNameWithCity());

        }
    }
}
