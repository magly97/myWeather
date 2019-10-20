package com.marlys.myweather.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marlys.myweather.CityWeatherInfoActivity;
import com.marlys.myweather.listener.ItemClickListener;
import com.marlys.myweather.R;
import com.marlys.myweather.database.DatabaseHelper;
import com.marlys.myweather.holder.CityWeatherHolder;
import com.marlys.myweather.model.CityWeather;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CityWeatherAdapter extends RecyclerView.Adapter<CityWeatherHolder> {

    private Context context;
    private ArrayList<CityWeather> cityWeathers;
    private DateFormat format = new SimpleDateFormat("HH:mm");
    private DecimalFormat formatValue = new DecimalFormat("#.#");
    private DatabaseHelper db;

    public CityWeatherAdapter(Context context, ArrayList<CityWeather> cityWeathers) {
        this.context = context;
        this.cityWeathers = cityWeathers;
    }

    @NonNull
    @Override
    public CityWeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        db = new DatabaseHelper(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_weather_card, null);
        return new CityWeatherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityWeatherHolder holder, int position) {
        holder.getCityName().setText(cityWeathers.get(position).getCity().getNameWithCity());
        holder.getWeatherDesc().setText(cityWeathers.get(position).getWeeklyWeather().get(0).getWeatherDescriptions().get(0).getDescription());

        String weatherTemp = formatValue.format(cityWeathers.get(position).getWeeklyWeather().get(0).getTemp().getDay()) + "°";
        holder.getWeatherTemp().setText(weatherTemp);

        String weatherMaxTemp = formatValue.format(cityWeathers.get(position).getWeeklyWeather().get(0).getTemp().getMax()) + "°";
        holder.getWeatherMaxTemp().setText(weatherMaxTemp);


        String weatherMinTemp = formatValue.format(cityWeathers.get(position).getWeeklyWeather().get(0).getTemp().getMin()) + "°";
        holder.getWeatherMinTemp().setText(weatherMinTemp);

        Date dateSunrise = new Date(cityWeathers.get(position).getWeeklyWeather().get(0).getSunrise() * 1000);
        String sunrise = format.format(dateSunrise);
        holder.getWeatherSunrise().setText(sunrise);

        Date dateSunset = new Date(cityWeathers.get(position).getWeeklyWeather().get(0).getSunset() * 1000);
        String sunset = format.format(dateSunset);
        holder.getWeatherSunset().setText(sunset);


        String icon = cityWeathers.get(position).getWeeklyWeather().get(0).getWeatherDescriptions().get(0).getIcon();
        Picasso.get().load("http://openweathermap.org/img/wn/" + icon + "@2x.png").into(holder.getImageView());


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                if (cityWeathers.get(position) != null) {
                    CityWeather cityWeather = cityWeathers.get(position);
                    Intent intent = new Intent(context, CityWeatherInfoActivity.class);
                    intent.putExtra("cityWeather", cityWeather);
                    context.startActivity(intent);
                }
            }
        });

        holder.setItemLongClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                Log.println(10,"ERROR",cityWeathers.get(position).getCity().getName());
                if(db.deleteRow(cityWeathers.get(position).getCity().getName()) > 0) {
                    Toast.makeText(context, context.getString(R.string.delete_item) + " " + cityWeathers.get(position).getCity().getName(), Toast.LENGTH_SHORT).show();
                    cityWeathers.remove(cityWeathers.get(position));
                    notifyItemRemoved(position);
                } else {
                    Toast.makeText(context, context.getString(R.string.delete_item), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return cityWeathers.size();
    }
}
