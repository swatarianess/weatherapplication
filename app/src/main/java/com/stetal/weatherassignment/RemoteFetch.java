package com.stetal.weatherassignment;

import android.content.Context;
import android.util.Log;

import com.stetal.weatherassignment.database.model.City;
import com.stetal.weatherassignment.database.model.ForecastSchema;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RemoteFetch {

    private static final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String OPEN_WEATHER_MAP_FORECAST = "http://api.openweathermap.org/data/2.5/forecast?id=";
    private static final String TAG = "RemoteFetch";


    public static JSONObject getJSON(Context context, int cityID) {
        try {
            String api_key = context.getString(R.string.open_weather_maps_app_id);
            URL url = new URL(OPEN_WEATHER_MAP_FORECAST + cityID + "&APPID=" + api_key);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuilder json = new StringBuilder(1024);
            String tmp;
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
            if (!data.getString("cod").equals("200")) {
                return null;
            }

            return data;
        } catch (Exception e) {
            Log.e(TAG, "getJSON: ", e);
            return null;
        }
    }

    public static List<ForecastSchema> parseForecasts(JSONObject job, City c) {
        List<ForecastSchema> result = new ArrayList<>();

        try {
        JSONArray weatherList = job.getJSONArray("list");
        for (int i = 0; i < weatherList.length(); i++) {
            JSONObject target = weatherList.getJSONObject(i);

            long temperature = (long) target.getJSONObject("main").getDouble("temp");
            long humidity = (long) target.getJSONObject("main").getDouble("humidity");
            long pressure = (long) target.getJSONObject("main").getDouble("pressure");
            long dateTime = (long) target.getDouble("dt");


            JSONObject targetWeather = target.getJSONArray("weather").getJSONObject(0);
            String weatherDescription = targetWeather.getString("description");
            String iconText = targetWeather.getString("icon");

            result.add(new ForecastSchema((long) c.getId(), c.getName(), weatherDescription, temperature, humidity, pressure, iconText, dateTime));
        }
        } catch (JSONException e){
            e.printStackTrace();
        }
        Log.i(TAG, "parseForecasts: " + result.size());
        return result;
    }

}
