package com.stetal.weatherassignment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.stetal.weatherassignment.database.model.FavouriteCitySchema;
import com.stetal.weatherassignment.database.model.ForecastSchema;

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
        db.execSQL(ForecastSchema.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavouriteCitySchema.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ForecastSchema.TABLE_NAME);
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

    public int getTableRowCount(String tableName) {
        String countQuery = "SELECT  * FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    /**
     * Deletes a city from the database with the given name
     *
     * @param city The name of the city to delete
     */
    public void removeSavedCity(FavouriteCitySchema city) {
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
    public void removeSavedCity(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavouriteCitySchema.TABLE_NAME, FavouriteCitySchema.COLUMN_ID + " = " + id, null);
    }

    /**
     * Removes a city from the FavouriteCities table
     *
     * @param cityName The city to remove from the FavouriteCities table
     */
    public void removeSavedCity(String cityName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavouriteCitySchema.TABLE_NAME, FavouriteCitySchema.COLUMN_CITY_NAME + " = ?", new String[]{cityName});
        db.close();
    }

    /**
     * Clears the rows of a table
     *
     * @param tableName The table to truncate
     */
    public void deleteTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName);
        db.close();
    }


    /**
     * Adds a single forecast to the forecast table
     *
     * @param forecast The single day forecast for a city
     * @return Returns the id of the forecast
     */
    public long addForecast(ForecastSchema forecast) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ForecastSchema.COLUMN_CITY_NAME, forecast.getCityName());
        values.put(ForecastSchema.COLUMN_DESCRIPTION, forecast.getDescription());
        values.put(ForecastSchema.COLUMN_TEMPERATURE, forecast.getTemperature());
        values.put(ForecastSchema.COLUMN_HUMIDITY, forecast.getHumidity());
        values.put(ForecastSchema.COLUMN_PRESSURE, forecast.getPressure());
        values.put(ForecastSchema.COLUMN_ICONTEXT, forecast.getIconText());
        values.put(ForecastSchema.COLUMN_TIMESTAMP, forecast.getTimestamp());

        long id = db.insert(ForecastSchema.TABLE_NAME, null, values);
        db.close();

        return id;
    }

    /**
     * Adds multiple city forecast's to the forecast table
     *
     * @param forecastSchemaList The list of forecast for a city
     */
    public void addForecast(List<ForecastSchema> forecastSchemaList) {
        for (int i = 0; i < forecastSchemaList.size(); i++) {
            addForecast(forecastSchemaList.get(i));
        }
    }

    public List<ForecastSchema> getForecasts() {
        List<ForecastSchema> forecasts = new ArrayList<>();

        String selectionQuery = "SELECT * FROM "
                + ForecastSchema.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectionQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ForecastSchema forecast = new ForecastSchema();
                forecast.setCityName(cursor.getString(cursor.getColumnIndex(ForecastSchema.COLUMN_CITY_NAME)));
                forecast.setDescription(cursor.getString(cursor.getColumnIndex(ForecastSchema.COLUMN_DESCRIPTION)));
                forecast.setHumidity(cursor.getInt(cursor.getColumnIndex(ForecastSchema.COLUMN_DESCRIPTION)));
                forecast.setIconText(cursor.getString(cursor.getColumnIndex(ForecastSchema.COLUMN_DESCRIPTION)));
                forecast.setPressure(cursor.getInt(cursor.getColumnIndex(ForecastSchema.COLUMN_DESCRIPTION)));
                forecast.setTemperature(cursor.getInt(cursor.getColumnIndex(ForecastSchema.COLUMN_DESCRIPTION)));
                forecast.setTimestamp(cursor.getInt(cursor.getColumnIndex(ForecastSchema.COLUMN_DESCRIPTION)));

                forecasts.add(forecast);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return forecasts;
    }

    public List<ForecastSchema> getCityForecasts(String cityName) {
        List<ForecastSchema> forecastSchemas = new ArrayList<>();

        String selectionQuery = "SELECT * FROM "
                + ForecastSchema.TABLE_NAME
                + " WHERE "
                + ForecastSchema.COLUMN_CITY_NAME
                + " = \'"
                + cityName
                + "\'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectionQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ForecastSchema forecast = new ForecastSchema();
                forecast.setCityName(cursor.getString(cursor.getColumnIndex(ForecastSchema.COLUMN_CITY_NAME)));
                forecast.setDescription(cursor.getString(cursor.getColumnIndex(ForecastSchema.COLUMN_DESCRIPTION)));
                forecast.setHumidity(cursor.getLong(cursor.getColumnIndex(ForecastSchema.COLUMN_HUMIDITY)));
                forecast.setIconText(cursor.getString(cursor.getColumnIndex(ForecastSchema.COLUMN_ICONTEXT)));
                forecast.setPressure(cursor.getLong(cursor.getColumnIndex(ForecastSchema.COLUMN_PRESSURE)));
                forecast.setTemperature(cursor.getLong(cursor.getColumnIndex(ForecastSchema.COLUMN_TEMPERATURE)));
                forecast.setTimestamp(cursor.getLong(cursor.getColumnIndex(ForecastSchema.COLUMN_TIMESTAMP)));

                forecastSchemas.add(forecast);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return forecastSchemas;
    }

    /**
     * Removes a row from the forecast table
     *
     * @param forecastSchema The forecast to remove from the Forecast Table
     */
    public void removeForecasts(ForecastSchema forecastSchema) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ForecastSchema.TABLE_NAME, ForecastSchema.COLUMN_CITY_NAME + " = ?", new String[]{forecastSchema.getCityName()});
        db.close();
    }

    /**
     * Removes all forecasts for a particular city
     *
     * @param forecastCityName The name of forecasts for a city
     */
    public void removeForecasts(String forecastCityName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ForecastSchema.TABLE_NAME, ForecastSchema.COLUMN_CITY_NAME + " IN (?) ", new String[]{forecastCityName});
        db.close();
    }

}
