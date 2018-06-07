package com.stetal.weatherassignment.mapSearch;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.stetal.weatherassignment.R;
import com.stetal.weatherassignment.database.model.City;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CitySearchActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
    private Boolean mLocationPermissionGranted = false;
    private static final int DEFAULT_ZOOM = 10;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final String TAG = "CitySearchActivity";

    private AutoCompleteTextView acSearchTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        acSearchTextView = findViewById(R.id.autoCompleteCityTextView);
        acSearchTextView.setEnabled(false);

        acSearchTextView.setThreshold(4);
        acSearchTextView.setOnItemClickListener((aView, view, pos, d) -> {

            City selected = (City) aView.getAdapter().getItem(pos);
            Toast.makeText(this, "Clicked: " + selected.getId(), Toast.LENGTH_SHORT).show();
            Log.i(TAG, "cityClicked: " + selected.getId() + ": " + selected.toString());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(selected.getLatitude(), selected.getLongitude()), DEFAULT_ZOOM));
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        Handler h = new Handler();
        h.post(() -> {
            List<String> theList = new ArrayList<>(Arrays.asList("Amsterdam", "Hong Kong", "Brisbane", "Something", "Zimbabkk", "Cairo"));
//            ArrayAdapter cityAdapter = new ArrayAdapter<>(CitySearchActivity.this, android.R.layout.select_dialog_item, theList);

            ArrayList<City> cityArrayList = loadJSONFromAsset(this);
            ArrayAdapter<City> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cityArrayList);

            acSearchTextView.setAdapter(adapter);
        });
        acSearchTextView.setEnabled(true);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (isLocationEnabled(this)) {
            Log.i(TAG, "isLocationEnabled: " + isLocationEnabled(this));
        } else {
            Toast.makeText(this, "Location Not enabled!", Toast.LENGTH_SHORT).show();
            buildAlertMessageNoGps();
        }

        return false;
    }

    /**
     * This method is called when the location button is clicked on this activity.
     *
     * @param location The location the user is currently at.
     */
    @Override
    public void onMyLocationClick(@NonNull Location location) {
        if (isLocationEnabled(this)) {
            Log.i(TAG, "isLocationEnabled: " + isLocationEnabled(this));
            Toast.makeText(this.getApplicationContext(), "Current location:\n" + location.toString(), Toast.LENGTH_LONG).show();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM - 5));
        } else {
            buildAlertMessageNoGps();
        }
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode;
        try {
            locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return locationMode != Settings.Secure.LOCATION_MODE_OFF;


    }

    /**
     * Creates a AlertDialog, asking if the user wants to enable GPS
     */
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Requests location permission
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionGranted = true;
                    } else {
                        Toast.makeText(getApplicationContext(), "This app requires location permissions to be granted", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }

        try {
            if (mLocationPermissionGranted && isLocationEnabled(this)) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                Log.d(TAG, "LocationEnabled:" + isLocationEnabled(this));
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                Log.i(TAG, "LocationEnabled:" + isLocationEnabled(this));
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    private static ArrayList<City> loadJSONFromAsset(Context context) {
        ArrayList<City> cityList = new ArrayList<>();

        try {
            InputStream is = context.getAssets().open("city_list.json");
//            InputStream is = context.getAssets().open("city_list_basic.json");
            JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));

            reader.beginArray();

            Gson gson = new GsonBuilder().create();

            while (reader.hasNext()) {
                City cityJson = gson.fromJson(reader, City.class);
                cityList.add(cityJson);
            }
            reader.endArray();
            reader.close();

        } catch (IOException ignored) {

        }

        return cityList;
    }


}
