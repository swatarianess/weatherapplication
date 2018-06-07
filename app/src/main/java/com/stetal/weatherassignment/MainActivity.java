package com.stetal.weatherassignment;

import android.app.SearchManager;
import android.content.Context;
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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.stetal.weatherassignment.adapters.CityRecyclerAdapter;
import com.stetal.weatherassignment.database.SqliteDatabase;
import com.stetal.weatherassignment.database.model.FavouriteCitySchema;
import com.stetal.weatherassignment.database.model.ForecastSchema;
import com.stetal.weatherassignment.mapSearch.CitySearchActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    private final String TAG = "MainActivity";
    private ArrayList<FavouriteCitySchema> cityDataArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CityRecyclerAdapter mRecyclerAdapter;
    private SwipeRefreshLayout swipeContainer;
    SqliteDatabase mDataSource;

    private ArrayList<Pair<String, String>> cityDummyData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataSource = new SqliteDatabase(this);
        populateCityList();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //RecyclerView Stuff!
        mRecyclerView = findViewById(R.id.citiesRecyclerView);
//        mRecyclerView.setHasFixedSize(true);


        //Saved Cities Data to fill into the Recycler View
        cityDataArrayList.addAll(mDataSource.getAllSavedCities());
        //------

        //Initializing properties for the Recycler View
        mRecyclerAdapter = new CityRecyclerAdapter(cityDataArrayList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        //-----

        //Initialize callback for smooth looking removal from list feature
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int pos = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    mDataSource.removeSavedCity(mRecyclerAdapter.getItem(pos));
                    mRecyclerAdapter.removeItem(pos);
                    Log.i(TAG, "onSwiped pos: " + pos);
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        swipeContainer = findViewById(R.id.swipeRefreshLayout);
        swipeContainer.setOnRefreshListener(this);

        swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit ");
                ArrayList<FavouriteCitySchema> result = new ArrayList<>(mDataSource.getSavedCity(s));
                mRecyclerAdapter.notifyData(result);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "onQueryTextChange ");
                ArrayList<FavouriteCitySchema> result = new ArrayList<>(mDataSource.getSavedCity(s));
                mRecyclerAdapter.notifyData(result);
                return false;
            }

        });
        
        return true;
    }

    /**
     * Handles action bar item clicks.
     *
     * @param item The menu item that has been selected
     * @return Returns if the item was selected?
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.citySearchMenuButton) {
            startActivity(new Intent(this, CitySearchActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param item The menu item that has been selected
     * @return Returns true always?
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        long currentTime = Calendar.getInstance().getTime().getTime();

        if (id == R.id.app_cities) {
            SqliteDatabase dbh = new SqliteDatabase(this);
            dbh.deleteTable(FavouriteCitySchema.TABLE_NAME);
            dbh.deleteTable(ForecastSchema.TABLE_NAME);

            Toast.makeText(this, "Cleared Tables!", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.app_current_location) {
            SqliteDatabase dbh = new SqliteDatabase(this);

            //Todo: Remove inserting to db in next iteration
            for (Pair<String, String> p : cityDummyData) {
                dbh.insertCity(new FavouriteCitySchema(p.first, p.second, currentTime));
            }

            //TODO: Make sure this works!
            List<ForecastSchema> dummyForecast = populateForecasts();
            for (ForecastSchema f : dummyForecast) {
                dbh.addForecast(f);
            }

            Log.i("currentTime: ", String.valueOf(currentTime));

            Log.i("Saved Cities: ", dbh.getAllSavedCities().toString());

        } else if (id == R.id.app_settings) {
            Toast.makeText(this, "Clicked Application Settings!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SettingsActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * This method is called when scroll-to-refresh event happens.
     * Currently;
     * - Updates the recycler view's adapter
     * - Waits 1 seconds
     * - Hides the Refreshing animation
     */
    @Override
    public void onRefresh() {
        Log.i("City Count: ", String.valueOf(mDataSource.getTableRowCount(FavouriteCitySchema.TABLE_NAME)));
        Log.i("Forecast Count: ", String.valueOf(mDataSource.getForecasts().size()));
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            mRecyclerAdapter.notifyData((ArrayList<FavouriteCitySchema>) mDataSource.getAllSavedCities());
            swipeContainer.setRefreshing(false);
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: " + mDataSource.getTableRowCount(FavouriteCitySchema.TABLE_NAME));
        mRecyclerAdapter.notifyData((ArrayList<FavouriteCitySchema>) mDataSource.getAllSavedCities());
    }

    private void populateCityList() {
        cityDummyData.add(new Pair<>("Amsterdam", "Netherlands"));
        cityDummyData.add(new Pair<>("Berlin", "Germany"));
        cityDummyData.add(new Pair<>("Mumbai", "India"));
        cityDummyData.add(new Pair<>("Bangalore", "India"));
        cityDummyData.add(new Pair<>("Tokyo", "Japan"));
        cityDummyData.add(new Pair<>("Beijing", "China"));
        cityDummyData.add(new Pair<>("Hong Kong", "China"));
    }

    private List<ForecastSchema> populateForecasts() {
        ArrayList<ForecastSchema> forecastSchemasList = new ArrayList<>();
        long currentTime = Calendar.getInstance().getTime().getTime();

        forecastSchemasList.add(new ForecastSchema("Amsterdam", "Sunny", 20L, 15, 2, "something", currentTime));
        forecastSchemasList.add(new ForecastSchema("Amsterdam", "Rainy", 13L, 18, 2, "something", currentTime + dayToMiliseconds(1)));
        forecastSchemasList.add(new ForecastSchema("Amsterdam", "Thunder", 8L, 80, 2, "something", currentTime + dayToMiliseconds(2)));

        forecastSchemasList.add(new ForecastSchema("Berlin", "Thunder", 27, 40, 2, "something", currentTime));
        forecastSchemasList.add(new ForecastSchema("Berlin", "Sunny", 23, 46, 2, "something", currentTime + dayToMiliseconds(1)));
        forecastSchemasList.add(new ForecastSchema("Berlin", "Rain", 22, 80, 2, "something", currentTime + dayToMiliseconds(2)));

        forecastSchemasList.add(new ForecastSchema("Mumbai", "Sunny", 12, 22, 2, "something", currentTime));
        forecastSchemasList.add(new ForecastSchema("Mumbai", "Cloudy", 19, 33, 2, "something", currentTime + dayToMiliseconds(1)));
        forecastSchemasList.add(new ForecastSchema("Mumbai", "Sunny", 29, 44, 2, "something", currentTime + dayToMiliseconds(2)));

        forecastSchemasList.add(new ForecastSchema("Bangalore", "Sunny", 5, 55, 2, "something", currentTime));
        forecastSchemasList.add(new ForecastSchema("Bangalore", "Sunny", 2, 22, 2, "something", currentTime + dayToMiliseconds(1)));
        forecastSchemasList.add(new ForecastSchema("Bangalore", "Sunny", 8, 1, 2, "something", currentTime + dayToMiliseconds(2)));

        forecastSchemasList.add(new ForecastSchema("Tokyo", "Cloudy", 10L, 33L, 2L, "something", currentTime));
        forecastSchemasList.add(new ForecastSchema("Tokyo", "Sunny", 12L, 88L, 2L, "something", currentTime + dayToMiliseconds(1)));
        forecastSchemasList.add(new ForecastSchema("Tokyo", "Thunder", 32L, 44L, 2L, "something", currentTime + dayToMiliseconds(2)));

        forecastSchemasList.add(new ForecastSchema("Beijing", "Cloudy", 33, 55, 2, "something", currentTime));
        forecastSchemasList.add(new ForecastSchema("Beijing", "Sunny", 47, 66, 2, "something", currentTime + dayToMiliseconds(1)));
        forecastSchemasList.add(new ForecastSchema("Beijing", "Sunny", 40, 77, 2, "something", currentTime + dayToMiliseconds(2)));

        forecastSchemasList.add(new ForecastSchema("Hong Kong", "Sunny", 18, 15, 2, "something", currentTime));
        forecastSchemasList.add(new ForecastSchema("Hong Kong", "Cloudy", 20, 10, 2, "something", currentTime + dayToMiliseconds(1)));
        forecastSchemasList.add(new ForecastSchema("Hong Kong", "Thunder", 22, 0, 2, "something", currentTime + dayToMiliseconds(2)));

        return forecastSchemasList;
    }

    private Long dayToMiliseconds(int days) {
        return (long) (days * 24 * 60 * 60 * 1000);
    }

}
