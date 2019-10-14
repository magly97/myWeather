package com.marlys.myweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marlys.myweather.R;
import com.marlys.myweather.holder.CityWeatherHolder;
import com.marlys.myweather.model.CityWeather;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CityWeatherAdapter extends RecyclerView.Adapter<CityWeatherHolder> {

    private Context context;
    private ArrayList<CityWeather> cityWeathers;
    private DateFormat format = new SimpleDateFormat( "hh:mm" );
    DecimalFormat formatValue = new DecimalFormat("#.#");

    public CityWeatherAdapter(Context context, ArrayList<CityWeather> cityWeathers) {
        this.context = context;
        this.cityWeathers = cityWeathers;
    }

    @NonNull
    @Override
    public CityWeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_weather_card,null);
        return new CityWeatherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityWeatherHolder holder, int position) {
        holder.getCityName().setText(cityWeathers.get(position).getCity().getNameWithCity());
        holder.getWeatherDesc().setText(cityWeathers.get(position).getWeeklyWeather().get(0).getWeatherDescriptions().get(0).getMain());

        String weatherTemp = formatValue.format(cityWeathers.get(position).getWeeklyWeather().get(0).getTemp().getDay())+"°";
        holder.getWeatherTemp().setText(weatherTemp);

        String weatherMaxTemp = formatValue.format(cityWeathers.get(position).getWeeklyWeather().get(0).getTemp().getMax())+"°";
        holder.getWeatherMaxTemp().setText(weatherMaxTemp);


        String weatherMinTemp = formatValue.format(cityWeathers.get(position).getWeeklyWeather().get(0).getTemp().getMin())+"°";
        holder.getWeatherMinTemp().setText(weatherMinTemp);

        String sunrise = format.format(cityWeathers.get(position).getWeeklyWeather().get(0).getSunrise());
        holder.getWeatherSunrise().setText(sunrise);

        String sunset = format.format(cityWeathers.get(position).getWeeklyWeather().get(0).getSunset());
        holder.getWeatherSunset().setText(sunset);


        String icon = cityWeathers.get(position).getWeeklyWeather().get(0).getWeatherDescriptions().get(0).getIcon();
        Picasso.get().load("http://openweathermap.org/img/wn/"+icon+"@2x.png").into(holder.getImageView());



    }

    @Override
    public int getItemCount() {
        return cityWeathers.size();
    }
}
