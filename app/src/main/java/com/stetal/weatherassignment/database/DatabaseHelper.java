package com.stetal.weatherassignment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.stetal.weatherassignment.database.model.SavedCity;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "saved_cities_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SavedCity.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SavedCity.TABLE_NAME);
        onCreate(db);
    }

    public long insertCity(SavedCity city){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SavedCity.COLUMN_CITY_NAME, city.getName());
        values.put(SavedCity.COLUMN_CITY_COUNTRY, city.getCountry());

        long id = db.insert(SavedCity.TABLE_NAME, null, values);

        db.close();
        return id;
    }

    public SavedCity getSavedCity(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(SavedCity.TABLE_NAME,
                new String[]{SavedCity.COLUMN_ID, SavedCity.COLUMN_CITY_NAME, SavedCity.COLUMN_CITY_COUNTRY ,SavedCity.COLUMN_TIMESTAMP},
                SavedCity.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }

        SavedCity savedCity = new SavedCity(
                cursor.getInt(cursor.getColumnIndex(SavedCity.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(SavedCity.COLUMN_CITY_NAME)),
                cursor.getString(cursor.getColumnIndex(SavedCity.COLUMN_CITY_COUNTRY)),
                cursor.getString(cursor.getColumnIndex(SavedCity.COLUMN_TIMESTAMP))
        );
        cursor.close();
        return savedCity;
    }

    public List<SavedCity> getAllSavedCities(){
        List<SavedCity> savedCities = new ArrayList<>();

        String selectionQuery = "SELECT * FROM "
                + SavedCity.TABLE_NAME
                + " ORDER BY "
                + SavedCity.COLUMN_CITY_NAME
                + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectionQuery, null);

        if (cursor.moveToFirst()){
            do {
                SavedCity city = new SavedCity();
                city.setId(cursor.getInt(cursor.getColumnIndex(SavedCity.COLUMN_ID)));
                city.setCountry(cursor.getString(cursor.getColumnIndex(SavedCity.COLUMN_CITY_COUNTRY)));
                city.setName(cursor.getString(cursor.getColumnIndex(SavedCity.COLUMN_CITY_NAME)));
                city.setTimestamp(cursor.getString(cursor.getColumnIndex(SavedCity.COLUMN_TIMESTAMP)));

                savedCities.add(city);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return savedCities;
    }

    public int getSavedCityCount() {
        String countQuery = "SELECT  * FROM " + SavedCity.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public void deleteSavedCity(SavedCity city) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SavedCity.TABLE_NAME, SavedCity.COLUMN_ID + " = ?",
                new String[]{String.valueOf(city.getId())});
        db.close();
    }
}
