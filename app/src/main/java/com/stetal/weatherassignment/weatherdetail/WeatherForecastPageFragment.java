package com.stetal.weatherassignment.weatherdetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stetal.weatherassignment.R;
import com.stetal.weatherassignment.database.SqliteDatabase;
import com.stetal.weatherassignment.database.model.ForecastSchema;
import com.stetal.weatherassignment.forecastRecycler.ForecastRecyclerAdapter;

import java.util.ArrayList;

public class WeatherForecastPageFragment extends Fragment {

    private RecyclerView mForecastFragmentRecyclerView;
    private String TAG = "ForecastPagerFragment";
    private ArrayList<ForecastSchema> cityForecastList = new ArrayList<>();
    private ForecastRecyclerAdapter mRecyclerAdapter;
    private SqliteDatabase mDatasource;
    private String cityName = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.weatherdetail_fragmentview, container, false);
        mForecastFragmentRecyclerView = v.findViewById(R.id.WeatherForecastFragmentRecyclerView);
        TextView forecastCityNameTextView = v.findViewById(R.id.weatherForecastFragmentCityName);

        forecastCityNameTextView.setText(cityName);
        Log.i(TAG, "onCreateView: " + cityName);

        mDatasource = new SqliteDatabase(getActivity().getApplicationContext());
        cityForecastList.addAll(mDatasource.getCityForecasts(cityName));
        Log.d(TAG, "onCreateView: " + cityForecastList.toString());
        mRecyclerAdapter = new ForecastRecyclerAdapter(cityForecastList);


        mForecastFragmentRecyclerView.setAdapter(mRecyclerAdapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mForecastFragmentRecyclerView.setLayoutManager(layoutManager);
        return v;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
