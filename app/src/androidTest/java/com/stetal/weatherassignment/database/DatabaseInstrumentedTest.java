package com.stetal.weatherassignment.database;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.stetal.weatherassignment.database.model.FavouriteCitySchema;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class DatabaseInstrumentedTest {

    private SqliteDatabase mDataSource;
    private FavouriteCitySchema ams = new FavouriteCitySchema( 1, "Amsterdam", "Netherlands", 1500555555L);
    private FavouriteCitySchema tok = new FavouriteCitySchema(2,  "Tokyo", "Japan", 1500555555L);

    @Before
    public void setUp(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        mDataSource = new SqliteDatabase(appContext);
        if (mDataSource.getAllSavedCities().size() > 0) {
            mDataSource.deleteTable(FavouriteCitySchema.TABLE_NAME);
        }
        mDataSource.insertCity(ams);
    }

    @After
    public void finished(){
        mDataSource.deleteTable(FavouriteCitySchema.TABLE_NAME);
    }

    @Test
    public void insertCity() {
        mDataSource.insertCity(new FavouriteCitySchema(2, "Tokyo", "Japan", SystemClock.currentThreadTimeMillis()));
        System.out.println("mDataSource.getTableRowCount() = " + mDataSource.getTableRowCount(FavouriteCitySchema.TABLE_NAME));
        assertNotEquals(0,mDataSource.getTableRowCount(FavouriteCitySchema.TABLE_NAME));
    }

    @Test
    public void getSavedCity() {
        assertNotEquals(0,mDataSource.getAllSavedCities().size());
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