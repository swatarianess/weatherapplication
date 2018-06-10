package com.stetal.weatherassignment.suggestions;

import com.stetal.weatherassignment.database.model.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainRequest {

    private final static String TAG = "MainRequest";

    private static String urlRequest = "https://search-suggestions-3b32iesczvnfcxlwjjjy7r3y5u.eu-west-2.es.amazonaws.com/name/_search?q=";

    private static String callURL(String myURL) {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection urlConn;
        InputStreamReader in = null;
        try {
            URL url = new URL(myURL);
            urlConn = (HttpURLConnection) url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(5 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:" + myURL, e);
        }

        return sb.toString();
    }

    public static List<City> retrieveSuggestions(String query) throws JSONException {
        List<City> results = new ArrayList<>();

        String jsonString = callURL(urlRequest + query);

        //Root
        JSONObject json = new JSONObject(jsonString);
        JSONObject hitsObj = json.getJSONObject("hits");

        //Hits Array
        JSONArray hitsArr = hitsObj.getJSONArray("hits");

        for (int i = 0; i < hitsArr.length(); i++) {
            JSONObject first = hitsArr.getJSONObject(i);
            JSONObject source = first.getJSONObject("_source");

            results.add(new City(source));
        }
        System.out.println("Query : " + jsonString);
        return results;
    }
}