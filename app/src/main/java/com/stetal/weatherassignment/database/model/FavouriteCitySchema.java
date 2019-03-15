package com.stetal.weatherassignment.database.model;

public class FavouriteCitySchema {

    public static final String TABLE_NAME = "Cities";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CITY_ID = "city_remote_id";
    public static final String COLUMN_CITY_COUNTRY = "country";
    public static final String COLUMN_CITY_NAME = "name";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private long remoteID;
    private long timestamp;
    private String name;
    private String country;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME
                    + "("
                    + " _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_CITY_ID + " INT UNIQUE,"
                    + COLUMN_CITY_COUNTRY + " TEXT,"
                    + COLUMN_CITY_NAME + " TEXT,"
                    + COLUMN_TIMESTAMP + " INT "
                    + ")";

    public FavouriteCitySchema() {
    }

    /**
     * @param name Name of the city
     * @param country Country the city is in
     * @param timestamp Time of adding the city
     */
    public FavouriteCitySchema(long cityID, String name, String country, long timestamp) {
        this.remoteID = cityID;
        this.timestamp = timestamp;
        this.name = name;
        this.country = country;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
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

    public void setRemoteID(long remoteID) {
        this.remoteID = remoteID;
    }

    public long getRemoteID() {
        return remoteID;
    }


    @Override
    public String toString() {
        return "FavouriteCitySchema{" +
                "name=" + name +
                ", country='" + country + '\'' +
                ", timestamp=" + timestamp +
                ", remoteID='" + remoteID + '\'' +
                '}';
    }
}
