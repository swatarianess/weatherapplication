package com.stetal.weatherassignment.weatherdetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

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
        mDataSource = new SqliteDatabase(this);
        NUM_PAGES = mDataSource.getAllSavedCities().size();
        Log.i(TAG, "#Pages: " + NUM_PAGES);

        mDataSource.getAllSavedCities().forEach( e -> cityNames.add(e.getName()));
        Log.i(TAG, "savedCities: " + cityNames.toString());
        setContentView(R.layout.weatherdetailview);
        mPager = findViewById(R.id.weatherDetailViewPager);

        mPagerAdapter = new WeatherDetailSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

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
