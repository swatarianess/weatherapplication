package com.stetal.weatherassignment.weatherdetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stetal.weatherassignment.R;

public class WeatherDetailSlidePageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return (ViewGroup) inflater.inflate(R.layout.weatherdetailview, container, false);
    }
}
