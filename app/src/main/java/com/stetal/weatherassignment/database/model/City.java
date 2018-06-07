package com.stetal.weatherassignment.database.model;

import org.json.JSONObject;

import java.util.Locale;

public class City {

    private double id;
    private String country;
    private Coord coord;
    private String name;

    public City(){

    }

    public City(JSONObject json) {
        this.id = json.optDouble("id");
        this.country = json.optString("country");
        this.coord = new Coord(json.optJSONObject("coord"));
        this.name = json.optString("name");
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s, %s", name, country);
    }

    public void setId(double id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public Coord getCoord() {
        return coord;
    }

    public String getName() {
        return name;
    }

    public void setLatitude(double latitude){
        coord.setLat(latitude);
    }

    public double getLatitude(){
        return coord.getLat();
    }


    public void setLongitude(int longitude){
        coord.setLon(longitude);
    }

    public double getLongitude(){
        return coord.getLon();
    }
}
