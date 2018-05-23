package com.stetal.weatherassignment.database;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.stetal.weatherassignment.database.model.FavouriteCitySchema;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseInstrumentedTest {

    private SqliteDatabase mDataSource;
    private FavouriteCitySchema ams = new FavouriteCitySchema( "Amsterdam", "Netherlands", 1500555555L);
    private FavouriteCitySchema tok = new FavouriteCitySchema( "Tokyo", "Japan", 1500555555L);

    @Test
    public void onCreate() {
    }

    @Before
    public void setUp(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        mDataSource = new SqliteDatabase(appContext);
        mDataSource.getAllSavedCities();
        mDataSource.insertCity(ams);
    }

    @After
    public void finished(){
        mDataSource.deleteTable(FavouriteCitySchema.TABLE_NAME);
    }

    @Test
    public void insertCity() {
        mDataSource.insertCity(new FavouriteCitySchema("Tokyo", "Japan", SystemClock.currentThreadTimeMillis()));
        System.out.println("mDataSource.getTableRowCount() = " + mDataSource.getTableRowCount(FavouriteCitySchema.TABLE_NAME));
        assertNotEquals(0,mDataSource.getTableRowCount(FavouriteCitySchema.TABLE_NAME));
    }

    @Ignore
    public void getSavedCity() {
    }

    @Ignore
    public void getAllSavedCities() {
    }

    @Test
    public void getSavedCityCount() {
        Log.i("getTableRowCount: ", String.valueOf(mDataSource.getTableRowCount(FavouriteCitySchema.TABLE_NAME)));
        mDataSource.deleteTable(FavouriteCitySchema.TABLE_NAME);
        Log.i("getTableRowCount: ", String.valueOf(mDataSource.getTableRowCount(FavouriteCitySchema.TABLE_NAME)));
        assertEquals(0, mDataSource.getTableRowCount(FavouriteCitySchema.TABLE_NAME));
    }

    @Test
    public void deleteSavedCity() {
        mDataSource.insertCity(ams);
        mDataSource.insertCity(ams);
        mDataSource.insertCity(tok);
        assertEquals(4, mDataSource.getTableRowCount(FavouriteCitySchema.TABLE_NAME));
        mDataSource.removeSavedCity(tok.getName());
        assertEquals(3, mDataSource.getTableRowCount(FavouriteCitySchema.TABLE_NAME));
    }
}