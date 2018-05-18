package com.stetal.weatherassignment.database;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.stetal.weatherassignment.database.model.FavouriteCitySchema;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseHelperInstrumentedTest {
    private DatabaseHelper mDataSource;

    @Ignore
    public void onCreate() {
    }

    @Before
    public void setUp(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        mDataSource = new DatabaseHelper(appContext);
//        mDataSource.insertCity(new FavouriteCitySchema("Amsterdam", "Netherlands", "1500555555"));
//        mDataSource.insertCity(new FavouriteCitySchema("Tokyo", "Japan", "1526502652"));
    }

    @After
    public void finished(){
        mDataSource.close();
    }

    @Test
    public void insertCity() {
        mDataSource.insertCity(new FavouriteCitySchema( "Tokyo", "Japan", "1525861701"));
        System.out.println("mDataSource.getSavedCityCount() = " + mDataSource.getSavedCityCount());
        Log.i("insertCity: ", String.valueOf(mDataSource.getSavedCityCount()));
        assertEquals(mDataSource.getSavedCityCount(),2);
    }

    @Ignore
    public void getSavedCity() {
    }

    @Test
    public void getAllSavedCities() {
        System.out.println("mDataSource.getAllSavedCities().toString() = " + mDataSource.getAllSavedCities().toString());
    }

    @Test
    public void getSavedCityCount() {
        Log.i("getSavedCityCount: ", String.valueOf(mDataSource.getSavedCityCount()));
        System.out.println("mDataSource.getSavedCityCount() = " + mDataSource.getSavedCityCount());
        assertNotEquals(0, mDataSource.getSavedCityCount());
    }

    @Ignore
    public void deleteSavedCity() {
    }

    @Test
    public void deleteCity(){
        mDataSource.deleteSavedCity("Amsterdam");
        mDataSource.deleteSavedCity("Tokyo");
    }
}