package com.stetal.weatherassignment.Basic;

import android.os.SystemClock;

/**
 * @author Ultraphatty
 * @version 0.0.1
 * @since 28/02/2018
 */

public class CityData {

    private int CityID;
    private String cityName;
    private String country;
    private long lastUpdated;

    public CityData(){
        this.CityID = -1;
        this.cityName = "";
        this.country = "";
        this.lastUpdated = SystemClock.currentThreadTimeMillis();
    }

    public CityData(int cityID, String cityName, String country, long lastUpdated) {
        CityID = cityID;
        this.cityName = cityName;
        this.country = country;
        this.lastUpdated = lastUpdated;
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
}
