package com.stetal.assignment;

import android.util.Pair;

/**
 *  A Class used
 *
 * @author Stephen T. Adu
 * @since 16/02/2018
 * @version 0.0.1
 */

public class DataWeather {

    private int id;                             //found under "id"
    private int code;                           //Found under "cod"
    private String location_name;               //Found under "name"
    private Pair coordinate;                    //Found under 'coord'

    private String weather;                     //Found under "weather/main"
    private String weather_description;         //Found under "weather/description"

    private double temperature;                 //Found under "main/temperature"
    private int pressure;                       //Found under "main/pressure"
    private int humidity;                       //Found under "main/humidity"
    private double temp_min;                    //Found under "main/temp_min"
    private double temp_max;                    //Found under "main/temp_max"

    private int visibility;                     //Found under "visibility"
    private int wind_speed;                     //Found under "wind/speed"
    private int wind_degree;                    //Found under "wind/deg"
    private int cloudiness;                     //Found under "clouds/all"


}
