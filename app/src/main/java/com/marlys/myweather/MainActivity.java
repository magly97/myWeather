package com.marlys.myweather;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.marlys.myweather.adapter.CityWeatherAdapter;
import com.marlys.myweather.api.OpenWeatherMap;
import com.marlys.myweather.api.RetrofitConf;
import com.marlys.myweather.database.DatabaseHelper;
import com.marlys.myweather.listener.DialogClickListener;
import com.marlys.myweather.model.CityWeather;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,DialogClickListener {

    private ArrayList<CityWeather> cityWeatherList;
    private RecyclerView recyclerView;
    private OpenWeatherMap openWeatherMap;
    private CityWeatherAdapter cityWeatherAdapter;
    private String lang;
    private String units;
    private DatabaseHelper db;
    private MyReceiver receiver = new MyReceiver();

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

        db =  new DatabaseHelper(this);
        cityWeatherList = new ArrayList<>();
        openWeatherMap = RetrofitConf.getInstance().create(OpenWeatherMap.class);
        lang = getString(R.string.actual_lang);
        getUnit();
        getDataFromDB();

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cityWeatherAdapter = new CityWeatherAdapter(this, cityWeatherList);
        recyclerView.setAdapter(cityWeatherAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    private void getUnit() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean metricSelected = sharedPreferences.getBoolean("unit",false);
        if (!metricSelected){
            units = "metric";
        } else {
            units = "imperial";
        }
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

        if (id == R.id.nav_refresh) {
            refresh();
        } else if (id == R.id.nav_add_city) {
            openDialogAddCity();
        } else if (id == R.id.nav_tools) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void addCity(final String cityName) {
        Call<CityWeather> cityWeather = openWeatherMap.getWeatherCity(cityName.trim(), RetrofitConf.KEY, units, 1, lang);
        cityWeather.enqueue(new Callback<CityWeather>() {
            @Override
            public void onResponse(Call<CityWeather> call, Response<CityWeather> response) {
                if (response.isSuccessful()) {
                    CityWeather cityWeatherBody = response.body();
                    checkNewCityAlreadyExistInList(cityWeatherBody);
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.city_not_found), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CityWeather> call, Throwable t) {
                if (receiver.isOnline(getApplicationContext()))
                    Toast.makeText(MainActivity.this, getString(R.string.api_error), Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(MainActivity.this, getString(R.string.internet_connection_2), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void updateCity(final String cityName, final int pos) {
        Call<CityWeather> cityWeather = openWeatherMap.getWeatherCity(cityName.trim(), RetrofitConf.KEY, units, 1, lang);
        cityWeather.enqueue(new Callback<CityWeather>() {
            @Override
            public void onResponse(Call<CityWeather> call, Response<CityWeather> response) {
                if (response.isSuccessful()) {
                    CityWeather cityWeatherBody = response.body();
                    cityWeatherList.remove(pos);
                    cityWeatherList.add(pos, cityWeatherBody);
                    cityWeatherAdapter.notifyItemChanged(pos);
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.city_not_found), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CityWeather> call, Throwable t) {
                if (receiver.isOnline(getApplicationContext()))
                    Toast.makeText(MainActivity.this, getString(R.string.api_error), Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(MainActivity.this, getString(R.string.internet_connection_2), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void getDataFromDB(){
        Cursor cursor = db.getData();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                addCity(cursor.getString(0));
            }
        }
    }
    private void checkNewCityAlreadyExistInList(CityWeather cityWeatherBody) {
        if (!cityWeatherList.contains(cityWeatherBody)) {
            cityWeatherList.add(cityWeatherBody);
            db.insertData(cityWeatherBody.getCity().getName());
            cityWeatherAdapter.notifyItemInserted(cityWeatherList.size() - 1);
            recyclerView.scrollToPosition(cityWeatherList.size() - 1);
        }
    }
    private void refresh() {
        getUnit();
        for (int i = 0; i < cityWeatherList.size(); i++) {
            updateCity(cityWeatherList.get(i).getCity().getName(), i);
        }
    }
    private void openDialogAddCity() {
        AddCityDialog addCityDialog = new AddCityDialog();
        addCityDialog.show(getSupportFragmentManager(),"addCityDialog");
    }

    @Override
    public void applyText(String cityName) {
        addCity(cityName);
    }
}
