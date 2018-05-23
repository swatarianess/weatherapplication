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
import java.util.List;

public class WeatherForecastActivity extends FragmentActivity {

    private static int NUM_PAGES;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private SqliteDatabase mDataSource;
    private final String TAG = "ForecastActivity";

    private ArrayList<Fragment> pagerFragments;
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

        mPagerAdapter = new WeatherDetailSlidePagerAdapter(getSupportFragmentManager(), pagerFragments);
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class WeatherDetailSlidePagerAdapter extends FragmentStatePagerAdapter {
        private final String TAG = "ForecastPagerAdapter";
        private List<Fragment> pagerItems;


        public WeatherDetailSlidePagerAdapter(FragmentManager fm, List<Fragment> pagerItems) {
            super(fm);
            this.pagerItems = pagerItems;
        }

        @Override
        public Fragment getItem(int position) {
            Log.i(TAG, "getItem Position: " + position);
            WeatherForecastPageFragment wPageFragment = new WeatherForecastPageFragment();
            Bundle b = new Bundle();
            b.putStringArray("CITY_NAME_ARRAY", cityNames.toArray(new String[]{}));
            wPageFragment.setArguments(b);
            wPageFragment.setCityName(cityNames.get(position));
            return wPageFragment;

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }


    }
}
