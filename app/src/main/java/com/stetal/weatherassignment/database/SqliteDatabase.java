package com.stetal.weatherassignment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.stetal.weatherassignment.database.model.FavouriteCitySchema;

import java.util.ArrayList;
import java.util.List;

public class SqliteDatabase extends SQLiteOpenHelper {

    //Database Vars
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favouriteCities.db";

    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates a table when the
     *
     * @param db The database to create the table in.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FavouriteCitySchema.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavouriteCitySchema.TABLE_NAME);
        onCreate(db);
    }

    public long insertCity(FavouriteCitySchema city) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FavouriteCitySchema.COLUMN_CITY_NAME, city.getName());
        values.put(FavouriteCitySchema.COLUMN_CITY_COUNTRY, city.getCountry());
        values.put(FavouriteCitySchema.COLUMN_TIMESTAMP, city.getTimestamp());

        long id = db.insert(FavouriteCitySchema.TABLE_NAME, null, values);

        db.close();
        return id;
    }

    public void insertCity(List<FavouriteCitySchema> cities) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < cities.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(FavouriteCitySchema.COLUMN_CITY_NAME, cities.get(i).getName());
            values.put(FavouriteCitySchema.COLUMN_CITY_COUNTRY, cities.get(i).getCountry());
            values.put(FavouriteCitySchema.COLUMN_TIMESTAMP, cities.get(i).getTimestamp());
            db.insert(FavouriteCitySchema.TABLE_NAME, null, values);
        }
        db.close();

    }

    public FavouriteCitySchema getSavedCity(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        FavouriteCitySchema favouriteCitySchema;
        Cursor cursor = db.query(FavouriteCitySchema.TABLE_NAME,
                new String[]{FavouriteCitySchema.COLUMN_CITY_NAME, FavouriteCitySchema.COLUMN_CITY_COUNTRY, FavouriteCitySchema.COLUMN_TIMESTAMP},
                FavouriteCitySchema.COLUMN_ID + "= ?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            favouriteCitySchema = new FavouriteCitySchema(
                    cursor.getString(cursor.getColumnIndex(FavouriteCitySchema.COLUMN_CITY_NAME)),
                    cursor.getString(cursor.getColumnIndex(FavouriteCitySchema.COLUMN_CITY_COUNTRY)),
                    cursor.getLong(cursor.getColumnIndex(FavouriteCitySchema.COLUMN_TIMESTAMP))
            );
            cursor.close();
            return favouriteCitySchema;
        }

        db.close();
        return null;
    }

    public List<FavouriteCitySchema> getAllSavedCities() {
        List<FavouriteCitySchema> savedCities = new ArrayList<>();

        String selectionQuery = "SELECT * FROM "
                + FavouriteCitySchema.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectionQuery, null);

        if (cursor.moveToFirst()) {
            do {
                FavouriteCitySchema city = new FavouriteCitySchema();
                city.setCountry(cursor.getString(cursor.getColumnIndex(FavouriteCitySchema.COLUMN_CITY_COUNTRY)));
                city.setName(cursor.getString(cursor.getColumnIndex(FavouriteCitySchema.COLUMN_CITY_NAME)));
                city.setTimestamp(cursor.getLong(cursor.getColumnIndex(FavouriteCitySchema.COLUMN_TIMESTAMP)));
                savedCities.add(city);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return savedCities;
    }

    public int getSavedCityCount() {
        String countQuery = "SELECT  * FROM " + FavouriteCitySchema.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return count;
    }

    /**
     * Deletes a city from the database with the given name
     *
     * @param city The name of the city to delete
     */
    public void deleteSavedCity(FavouriteCitySchema city) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavouriteCitySchema.TABLE_NAME, FavouriteCitySchema.COLUMN_CITY_NAME + " = ?",
                new String[]{String.valueOf(city.getName())});
        db.close();
    }

    /**
     * Deletes a city from the database with the corresponding id
     *
     * @param id The id of the city to delete
     */
    public void deleteSavedCity(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavouriteCitySchema.TABLE_NAME, FavouriteCitySchema.COLUMN_ID + " = " + id, null);
    }

    public void deleteSavedCity(String cityName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavouriteCitySchema.TABLE_NAME, FavouriteCitySchema.COLUMN_CITY_NAME + " = ?", new String[]{cityName});
        db.close();
    }

    public void deleteTable(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName);
        db.close();
    }


}
