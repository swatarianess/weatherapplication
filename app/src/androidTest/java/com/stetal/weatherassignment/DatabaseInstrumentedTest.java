package com.stetal.weatherassignment;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.stetal.weatherassignment.database.SqliteDatabase;
import com.stetal.weatherassignment.database.model.FavouriteCitySchema;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseInstrumentedTest {

    private SqliteDatabase mDataSource;
    private ArrayList<FavouriteCitySchema> cityList = new ArrayList<>();

    @Test
    public void onCreate() {
    }

    @Before
    public void setUp(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        mDataSource = new SqliteDatabase(appContext);
        mDataSource.getAllSavedCities();
        mDataSource.insertCity(new FavouriteCitySchema( "Amsterdam", "Netherlands", 1500555555L));
    }

    @After
    public void finished(){
        mDataSource.close();
    }

    @Test
    public void insertCity() {
        mDataSource.insertCity(new FavouriteCitySchema("Tokyo", "Japan", 1525861701L));
        System.out.println("mDataSource.getSavedCityCount() = " + mDataSource.getSavedCityCount());
        assertEquals(2,mDataSource.getSavedCityCount());
    }

    @Ignore
    public void getSavedCity() {
    }

    @Ignore
    public void getAllSavedCities() {
    }

    @Ignore
    public void getSavedCityCount() {
        Log.i("getSavedCityCount: ", String.valueOf(mDataSource.getSavedCityCount()));
        System.out.println("mDataSource.getSavedCityCount() = " + mDataSource.getSavedCityCount());
        assertEquals(0, mDataSource.getSavedCityCount());
    }

    @Ignore
    public void deleteSavedCity() {
    }
}