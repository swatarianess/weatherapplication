package com.stetal.weatherassignment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.stetal.weatherassignment.database.model.FavouriteCitySchema;

import java.util.ArrayList;
import java.util.List;

public class FavoriteCitiesLab {
    private static FavoriteCitiesLab sfavoriteCitiesLab;

    private List<FavouriteCitySchema> mSavedCities;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public FavoriteCitiesLab(Context mContext) {
        this.mContext = mContext;
        mDatabase = new SqliteDatabase(mContext).getWritableDatabase();
        mSavedCities = new ArrayList<>();
    }
}
