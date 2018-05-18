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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stetal.weatherassignment.citySelection.CityRecyclerAdapter;
import com.stetal.weatherassignment.database.SqliteDatabase;
import com.stetal.weatherassignment.database.model.FavouriteCitySchema;
import com.stetal.weatherassignment.mapSearch.CitySearchActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<FavouriteCitySchema> cityDataArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CityRecyclerAdapter mRecyclerAdapter;
    private SwipeRefreshLayout swipeContainer;
    SqliteDatabase mDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataSource = new SqliteDatabase(this);

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
        mRecyclerView.setHasFixedSize(true);


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
                    mRecyclerAdapter.removeItem(pos);
//                    mRecyclerAdapter.notifyItemRemoved(pos);
//                    mRecyclerAdapter.notifyItemRangeChanged(pos, mRecyclerAdapter.getItemCount());
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        //-------
        swipeContainer = findViewById(R.id.swipeRefreshLayout);
        swipeContainer.setOnRefreshListener(this);

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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
        getMenuInflater().inflate(R.menu.search, menu);
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
            Toast.makeText(this, "Clicked Cities List!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.app_current_location) {
            SqliteDatabase dbh = new SqliteDatabase(this);

            //Todo: Remove inserting to db in next iteration
            dbh.insertCity(new FavouriteCitySchema("Amsterdam", "Netherlands", currentTime));
            dbh.insertCity(new FavouriteCitySchema("Tokyo", "Japan", currentTime));
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
     * - Waits 3 seconds
     * - Hides the Refreshing animation
     */
    @Override
    public void onRefresh() {
        Log.i("City Count: ", String.valueOf(mDataSource.getSavedCityCount()));
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            mRecyclerAdapter.notifyData((ArrayList<FavouriteCitySchema>) mDataSource.getAllSavedCities());
            swipeContainer.setRefreshing(false);
        }, 1000);
    }
}
