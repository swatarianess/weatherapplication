package com.stetal.weatherassignment.Basic;

import android.os.SystemClock;

import java.util.Random;

/**
 * @author Stephen Adu
 * @version 0.0.1
 * @since 28/02/2018
 */

public class CityData {

    private Random r = new Random();

    private int CityID;
    private String cityName;
    private String country;
    private String currentWeatherIcon;
    private String[] weatherTypes = {
            "&#xf022;",
            "&#xf016;",
            "&#xf01b;",
            "&#xf021;",
            "&#xf071;",
            "&#xf02e;"
    };

    private long lastUpdated;

    public CityData(){
        this.CityID = -1;
        this.cityName = "";
        this.country = "";
        this.lastUpdated = SystemClock.currentThreadTimeMillis();
        this.currentWeatherIcon = weatherTypes[r.nextInt(weatherTypes.length-1)];
    }

    public CityData(int cityID, String cityName, String country, long lastUpdated) {
        CityID = cityID;
        this.cityName = cityName;
        this.country = country;
        this.lastUpdated = lastUpdated;
        this.currentWeatherIcon = weatherTypes[r.nextInt(weatherTypes.length-1)];
    }

    public CityData(int cityID, String cityName, String country, long lastUpdated, String currentWeatherIcon) {
        CityID = cityID;
        this.cityName = cityName;
        this.country = country;
        this.lastUpdated = lastUpdated;
        this.currentWeatherIcon = currentWeatherIcon;
    }

    public int getCityID() {
        return CityID;
    }

    public CityData setCityID(int cityID) {
        CityID = cityID;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public CityData setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public CityData setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public CityData setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCurrentWeatherIcon() {
        return currentWeatherIcon;
    }

    public CityData setCurrentWeatherIcon(String currentWeatherIcon) {
        this.currentWeatherIcon = currentWeatherIcon;
        return this;
    }


}
