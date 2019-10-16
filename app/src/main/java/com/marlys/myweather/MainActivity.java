package com.marlys.myweather;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.marlys.myweather.adapter.CityWeatherAdapter;
import com.marlys.myweather.api.OpenWeatherMap;
import com.marlys.myweather.api.RetrofitConf;
import com.marlys.myweather.model.CityWeather;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<CityWeather> cityWeatherList;
    private RecyclerView recyclerView;
    private ListView listView;
    private OpenWeatherMap openWeatherMap;
    private CityWeatherAdapter cityWeatherAdapter;
    private String lang;

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
        lang = getString(R.string.actual_lang);

        cityWeatherList = new ArrayList<>();
        openWeatherMap = RetrofitConf.getInstance().create(OpenWeatherMap.class);

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addCity("Krakow");
        addCity("Warszawa");

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
        Call<CityWeather> cityWeather = openWeatherMap.getWeatherCity(cityName, RetrofitConf.KEY, "metric", 1, lang);
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
        return cityWeatherList;
    }

    public void setCityWeatherList(ArrayList<CityWeather> cityWeatherList) {
        this.cityWeatherList = cityWeatherList;
    }
}
