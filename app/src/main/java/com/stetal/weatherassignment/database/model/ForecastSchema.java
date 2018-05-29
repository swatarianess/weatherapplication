package com.stetal.weatherassignment.database.model;

public class ForecastSchema {

    public static final String TABLE_NAME           = "Forecasts";
    public static final String COLUMN_ID            = "_id";
    public static final String COLUMN_CITY_NAME     = "cityName";
    public static final String COLUMN_DESCRIPTION   = "description";
    public static final String COLUMN_TEMPERATURE   = "temperature";
    public static final String COLUMN_HUMIDITY      = "humidity";
    public static final String COLUMN_PRESSURE      = "pressure";
    public static final String COLUMN_ICONTEXT      = "iconText";
    public static final String COLUMN_TIMESTAMP     = "timestamp";

    private String cityName;
    private String description;
    private long temperature;
    private long humidity;
    private long pressure;
    private String iconText;
    private long timestamp;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME
                    + "("
                    + " _id INTEGER PRIMARY KEY AUTOINCREMENT, "    // ID Column
                    + COLUMN_CITY_NAME      + " TEXT, "             // Forecast City Column
                    + COLUMN_DESCRIPTION    + " TEXT, "             // Forecast Desc Column
                    + COLUMN_TEMPERATURE    + " INT, "              // Forecast Temp Column
                    + COLUMN_HUMIDITY       + " INT, "              // Forecast Humidity Column
                    + COLUMN_PRESSURE       + " INT, "              // Forecast Pressure Column
                    + COLUMN_ICONTEXT       + " TEXT, "             // Forecast IconText Column
                    + COLUMN_TIMESTAMP      + " INT "               // Forecast Timestamp Column
                    + ")";

    public ForecastSchema() {}

    public ForecastSchema(String cityName, String description, long temperature, long humidity, long pressure, String iconText, long timestamp) {
        this.cityName = cityName;
        this.description = description;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.iconText = iconText;
        this.timestamp = timestamp;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTemperature() {
        return temperature;
    }

    public void setTemperature(long temperature) {
        this.temperature = temperature;
    }

    public long getHumidity() {
        return humidity;
    }

    public void setHumidity(long humidity) {
        this.humidity = humidity;
    }

    public long getPressure() {
        return pressure;
    }

    public void setPressure(long pressure) {
        this.pressure = pressure;
    }

    public String getIconText() {
        return iconText;
    }

    public void setIconText(String iconText) {
        this.iconText = iconText;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ForecastSchema{" +
                "cityName='" + cityName + '\'' +
                ", description='" + description + '\'' +
                ", temperature='" + temperature + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}