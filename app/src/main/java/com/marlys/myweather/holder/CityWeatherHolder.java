package com.marlys.myweather.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marlys.myweather.listener.ItemClickListener;
import com.marlys.myweather.R;

public class CityWeatherHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {

    private ImageView imageView;
    private TextView cityName;
    private TextView weatherDesc;
    private TextView weatherTemp;
    private TextView weatherMaxTemp;
    private TextView weatherMinTemp;
    private TextView weatherSunrise;
    private TextView weatherSunset;
    private ItemClickListener itemClickListener;
    private  ItemClickListener itemLongClickListener;

    public CityWeatherHolder(@NonNull View itemView) {
        super(itemView);

        this.imageView = itemView.findViewById(R.id.weather_image);
        this.cityName = itemView.findViewById(R.id.weather_city);
        this.weatherDesc = itemView.findViewById(R.id.weather_desc);
        this.weatherTemp = itemView.findViewById(R.id.weather_temp);
        this.weatherMaxTemp = itemView.findViewById(R.id.weather_max_temp);
        this.weatherMinTemp = itemView.findViewById(R.id.weather_min_temp);
        this.weatherSunrise = itemView.findViewById(R.id.weather_sunrise_time);
        this.weatherSunset = itemView.findViewById(R.id.weather_sunset_time);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClickListener(view, getLayoutPosition());
    }
    @Override
    public boolean onLongClick(View view) {
        this.itemLongClickListener.onItemClickListener(view, getLayoutPosition());
        return false;
    }
    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getCityName() {
        return cityName;
    }

    public void setCityName(TextView cityName) {
        this.cityName = cityName;
    }

    public TextView getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(TextView weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public TextView getWeatherTemp() {
        return weatherTemp;
    }

    public void setWeatherTemp(TextView weatherTemp) {
        this.weatherTemp = weatherTemp;
    }

    public TextView getWeatherMaxTemp() {
        return weatherMaxTemp;
    }

    public void setWeatherMaxTemp(TextView weatherMaxTemp) {
        this.weatherMaxTemp = weatherMaxTemp;
    }

    public TextView getWeatherMinTemp() {
        return weatherMinTemp;
    }

    public void setWeatherMinTemp(TextView weatherMinTemp) {
        this.weatherMinTemp = weatherMinTemp;
    }

    public TextView getWeatherSunrise() {
        return weatherSunrise;
    }

    public void setWeatherSunrise(TextView weatherSunrise) {
        this.weatherSunrise = weatherSunrise;
    }

    public TextView getWeatherSunset() {
        return weatherSunset;
    }

    public void setWeatherSunset(TextView weatherSunset) {
        this.weatherSunset = weatherSunset;
    }

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ItemClickListener getItemLongClickListener() {
        return itemLongClickListener;
    }

    public void setItemLongClickListener(ItemClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

}
