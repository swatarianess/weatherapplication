package com.stetal.weatherassignment.database.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

class Coord {

    @SerializedName("lon")
    private double lon;

    @SerializedName("lat")
    private double lat;

    public Coord(){}

    public Coord(JSONObject json){
        this.lon = json.optDouble("lon");
        this.lat = json.optDouble("lat");
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
