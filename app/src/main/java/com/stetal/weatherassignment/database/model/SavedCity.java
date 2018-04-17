package com.stetal.weatherassignment.database.model;

public class SavedCity {

    public static final String TABLE_NAME = "Cities";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CITY_COUNTRY = "country";
    public static final String COLUMN_CITY_NAME = "name";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String timestamp;
    private String name;
    private String country;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME
                    + "("
                    + COLUMN_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CITY_COUNTRY + " TEXT,"
                    + COLUMN_CITY_NAME + " TEXT,"
                    + COLUMN_TIMESTAMP + "DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public SavedCity() {
    }

    public SavedCity(int id, String name, String country, String timestamp) {
        this.id = id;
        this.timestamp = timestamp;
        this.name = name;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
