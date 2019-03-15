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
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stetal.weatherassignment.R;
import com.stetal.weatherassignment.RemoteFetch;
import com.stetal.weatherassignment.adapters.SuggestionAdapter;
import com.stetal.weatherassignment.database.SqliteDatabase;
import com.stetal.weatherassignment.database.model.City;
import com.stetal.weatherassignment.database.model.FavouriteCitySchema;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CitySearchActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
    private Boolean mLocationPermissionGranted = false;
    private static final int DEFAULT_ZOOM = 10;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final String TAG = "CitySearchActivity";
    SqliteDatabase mDataSource;

    private List<FavouriteCitySchema> citySchemaList = new ArrayList<>();

    private AutoCompleteTextView acSearchTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataSource = new SqliteDatabase(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_city_search);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        citySchemaList.addAll(mDataSource.getAllSavedCities());

        acSearchTextView = findViewById(R.id.autoCompleteCityTextView);
        acSearchTextView.setEnabled(false);

        acSearchTextView.setThreshold(4);

        acSearchTextView.setOnItemClickListener((aView, view, pos, d) -> {
            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

            City selected = (City) aView.getAdapter().getItem(pos);
            Log.i(TAG, "selected: " + selected);
            LatLng selectedCityLocation = new LatLng(selected.getLatitude(), selected.getLongitude());

            mMap.addMarker(new MarkerOptions().position(selectedCityLocation).title(selected.getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedCityLocation, DEFAULT_ZOOM));
            mDataSource.insertCity(new FavouriteCitySchema((long) selected.getId(), selected.getName(), selected.getCountry(), Calendar.getInstance().getTime().getTime()));

            JSONObject cityForecasts = RemoteFetch.getJSON(this, (long) selected.getId());

            Log.i(TAG, "onCreate: " + (cityForecasts != null ? cityForecasts.toString() : null));

            try {
                mDataSource.addForecast(RemoteFetch.parseForecasts(cityForecasts,selected));
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
            SuggestionAdapter suggestionAdapter = new SuggestionAdapter(this);
            acSearchTextView.setAdapter(suggestionAdapter);
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

    /**
     * Checks if the location service is enabled
     *
     * @param context The context which the method is in
     * @return Returns if location is enabled
     */
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

}
