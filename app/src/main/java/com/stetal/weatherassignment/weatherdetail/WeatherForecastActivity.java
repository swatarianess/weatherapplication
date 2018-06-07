package com.stetal.weatherassignment.weatherdetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.stetal.weatherassignment.R;
import com.stetal.weatherassignment.database.SqliteDatabase;

import java.util.ArrayList;

public class WeatherForecastActivity extends FragmentActivity {

    private static int NUM_PAGES;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private SqliteDatabase mDataSource;
    private final String TAG = "ForecastActivity";

    private ArrayList<String> cityNames = new ArrayList<>();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Retrieving information from the database
        mDataSource = new SqliteDatabase(this);
        NUM_PAGES = mDataSource.getAllSavedCities().size();
        mDataSource.getAllSavedCities().forEach( e -> cityNames.add(e.getName()));

        //Getting the reference to the page from the layout xml
        setContentView(R.layout.weatherdetailview);
        mPager = findViewById(R.id.weatherDetailViewPager);

        //Set an adapter
        mPagerAdapter = new WeatherDetailSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        //Gets data sent to this Activity. Parses the item clicked in the RecyclerView
        //Sets the current viewPage to the city selected previously
        Intent intent = getIntent();
        String selectedCityName = intent.getStringExtra("CITY_NAME");
        mPager.setCurrentItem(cityNames.indexOf(selectedCityName.split(",")[0]));

        //Splits the city name into 2, because it normally includes the Country too
        //For example; "Berlin, Germany"
        //Splitting will just return Berlin, which is one of the viewpager titles.
    }

    /**
     * Code that is executed when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }

    private class WeatherDetailSlidePagerAdapter extends FragmentStatePagerAdapter {
        private final String TAG = "ForecastPagerAdapter";

        public WeatherDetailSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            WeatherForecastPageFragment wPageFragment = new WeatherForecastPageFragment();
            wPageFragment.setCityName(cityNames.get(position));

            return wPageFragment;

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }


    }
}
