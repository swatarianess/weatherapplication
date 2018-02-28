package com.stetal.weatherassignment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stetal.weatherassignment.Basic.CityData;
import com.stetal.weatherassignment.recyclerStuff.CityRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {


    ArrayList<CityData> cityDataArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CityRecyclerAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Configure the refreshing colors



        //RecyclerView Stuff!

        mRecyclerView = findViewById(R.id.citiesRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        CityData london = new CityData(200, "London", "United Kingdom", 1519838493L);
        CityData amsterdam = new CityData(1, "Amsterdam", "Netherlands", 1486732893L);
        CityData hongKong = new CityData(334, "Hong Kong", "China", 1513249600L);
        CityData bangkok = new CityData(663, "BangKok", "Thailand", 1519845048L);
        CityData casablanca = new CityData(663, "Casablanca", "Morocco", 1519841556L);
        CityData beijing = new CityData(888, "Beijing", "China", 1519841000L);
        CityData telAviv = new CityData(123, "Tel Aviv", "Israel", 1519841711L);
        CityData mexicoCity = new CityData(123, "Mexico City", "Mexico", 1519843811L);
        CityData souel = new CityData(123, "Souel", "South Korea", 1519846999L);
        CityData nyc = new CityData(123, "New York City", "USA", 1519848999L);

        cityDataArrayList.addAll(Arrays.asList(london, amsterdam, hongKong, bangkok, casablanca, beijing, telAviv
        , mexicoCity, souel, nyc));

        mRecyclerAdapter = new CityRecyclerAdapter(cityDataArrayList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        swipeContainer = findViewById(R.id.swipeRefreshLayout);
        swipeContainer.setOnRefreshListener(this);

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //
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
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.citySearchMenuButton) {
            Toast.makeText(this, "Send to Search Fragment!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.app_cities) {
            Toast.makeText(this, "Clicked Cities List!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.app_current_location) {
            Toast.makeText(this, "Clicked Current Location!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.app_settings) {
            Toast.makeText(this, "Clicked Application Settings!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SettingsActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(()-> {
            mRecyclerView.setAdapter(new CityRecyclerAdapter(cityDataArrayList));
            swipeContainer.setRefreshing(false);
        }                    ,3000);
    }
}
