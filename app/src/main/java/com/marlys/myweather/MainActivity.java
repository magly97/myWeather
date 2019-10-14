package com.marlys.myweather;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.marlys.myweather.adapter.CityWeatherAdapter;
import com.marlys.myweather.api.OpenWeatherMap;
import com.marlys.myweather.api.RetrofitConf;
import com.marlys.myweather.model.City;
import com.marlys.myweather.model.CityWeather;
import com.marlys.myweather.model.Temp;
import com.marlys.myweather.model.Weather;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<CityWeather> cityWeatherList;
    private RecyclerView recyclerView;
    private OpenWeatherMap openWeatherMap;
    private CityWeatherAdapter cityWeatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        cityWeatherList = new ArrayList<>();
        openWeatherMap = RetrofitConf.getInstance().create(OpenWeatherMap.class);

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addCity("Krakow");
        cityWeatherAdapter = new CityWeatherAdapter(this, cityWeatherList);
        recyclerView.setAdapter(cityWeatherAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_add_city) {

        } else if (id == R.id.nav_tools) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addCity(String cityName) {
        Call<CityWeather> cityWeather = openWeatherMap.getWeatherCity(cityName, RetrofitConf.KEY, "metric", 5);
        cityWeather.enqueue(new Callback<CityWeather>() {
            @Override
            public void onResponse(Call<CityWeather> call, Response<CityWeather> response) {
                if (response.isSuccessful()) {
                    CityWeather cityWeather = response.body();
                    cityWeatherList.add(cityWeather);
                    cityWeatherAdapter.notifyItemInserted(cityWeatherList.size() - 1);
                    recyclerView.scrollToPosition(getCityWeatherList().size() - 1);

                } else {
                    Toast.makeText(MainActivity.this, "Sorry, city not found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CityWeather> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Sorry, weather services are currently unavailable", Toast.LENGTH_LONG).show();
            }
        });
    }

    public ArrayList<CityWeather> getCityWeatherList() {
        ArrayList<CityWeather> cityWeathers = new ArrayList<>();
        CityWeather cityWeather = new CityWeather();

        City city = new City();
        city.setCountry("Pl");
        city.setName("Zamosc");
        city.setId(1);

        Temp temp = new Temp();
        temp.setDay(10);
        temp.setMax(19);
        temp.setMin(10);

        List<Weather> weathers =  new ArrayList<>();
        Weather weather = new Weather();
        weather.setClouds(10.0f);
        weather.setDate(100000);
        weather.setDeg(10.0f);
        weather.setSpeed(100);
        weather.setSunrise(1571072836);
        weather.setSunset(1571072836);
        weather.setTemp(temp);
        weathers.add(weather);
        cityWeather.setCity(city);
        cityWeather.setWeeklyWeather(weathers);
        cityWeathers.add(cityWeather);
        return cityWeathers;
    }

    public void setCityWeatherList(ArrayList<CityWeather> cityWeatherList) {
        this.cityWeatherList = cityWeatherList;
    }
}