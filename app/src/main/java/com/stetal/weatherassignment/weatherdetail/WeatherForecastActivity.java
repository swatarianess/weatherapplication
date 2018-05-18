package com.stetal.weatherassignment.weatherdetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.stetal.weatherassignment.R;

public class WeatherForecastActivity extends FragmentActivity {

    private static final int NUM_PAGES = 5;

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String cityName = getIntent().getStringExtra("CITY_NAME");

        setContentView(R.layout.weatherdetailview);

        mPager = findViewById(R.id.weatherDetailViewPager);
        mPagerAdapter = new WeatherDetailSlidePagerAdapter(getSupportFragmentManager());
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

        public WeatherDetailSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new WeatherDetailSlidePageFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
