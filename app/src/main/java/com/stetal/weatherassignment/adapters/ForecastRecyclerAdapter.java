package com.stetal.weatherassignment.adapters;

import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stetal.weatherassignment.FontAwesome;
import com.stetal.weatherassignment.R;
import com.stetal.weatherassignment.database.model.ForecastSchema;
import com.thefinestartist.finestwebview.FinestWebView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ForecastRecyclerAdapter extends RecyclerView.Adapter<ForecastRecyclerAdapter.RecyclerItemViewHolder> {

    private String cityName;
    private long cityID;
    private ArrayList<ForecastSchema> myList;
    private final String TAG = "ForecastRAdapter";

    public ForecastRecyclerAdapter(ArrayList<ForecastSchema> myList) {
        this.myList = myList;
    }

    @Override
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weatherdetail_item, parent, false);
        return new RecyclerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerItemViewHolder holder, int position) {
        ForecastSchema item = myList.get(position);
        cityID = item.getCityID();

        SimpleDateFormat sdfDayOfWeek = new SimpleDateFormat("EEEE", Locale.getDefault());  //
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM", Locale.getDefault());  //
        long timestamp = item.getTimestamp() * 1000;

        holder.forecastDescription.setText(item.getDescription());
        holder.forecastTemperature.setText(String.format(Locale.getDefault(), "Temp: %d", (item.getTemperature()- 273)));

        holder.forecastDayOfWeek.setText(String.format(Locale.getDefault(), "Date: %s", sdfDayOfWeek.format(timestamp)));
        holder.forecastDate.setText(String.format(Locale.getDefault(), "Date: %s", sdfDate.format(timestamp)));

        holder.mainLayout.setOnClickListener(view -> {
                    new FinestWebView.Builder(view.getContext())
                            .showIconMenu(true)
                            .titleDefault(cityName)
                            .showUrl(false)
                            .gradientDivider(false)
                            .webViewJavaScriptEnabled(true)
                            .show("https://openweathermap.org/city/" + cityID);

                    Log.i(TAG, "onBindViewHolder: CITY_ID := " + cityID);
                    Log.i(TAG, "onBindViewHolder: ITEM := " + item);
                }
        );


    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public ArrayList<ForecastSchema> getCityList() {
        return myList;
    }

    /**
     * @param forecastList New set of data to add to Adapter
     */
    public void notifyData(ArrayList<ForecastSchema> forecastList) {
        this.myList = forecastList;
        notifyDataSetChanged();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCityID(long cityID) {
        this.cityID = cityID;
    }


    /**
     * Inner class that handles the ViewHolder of the item.
     */
    class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView forecastDayOfWeek;
        private final TextView forecastDate;
        private final TextView forecastDescription;
        private final TextView forecastTemperature;
        private final FontAwesome weatherIconTextView;
        private ConstraintLayout mainLayout;

        RecyclerItemViewHolder(final View v) {
            super(v);
            this.forecastDayOfWeek = v.findViewById(R.id.WeatherForecastDay);
            this.forecastDate = v.findViewById(R.id.WeatherForecastDate);
            this.forecastDescription = v.findViewById(R.id.WeatherForecastDescription);
            this.forecastTemperature = v.findViewById(R.id.WeatherForecastTemperature);
            this.weatherIconTextView = v.findViewById(R.id.WeatherForecastIcon);
            this.mainLayout = v.findViewById(R.id.weatherForecastItemLayout);

            Typeface typeface = Typeface.createFromAsset(v.getContext().getAssets(), "weatherfont.ttf");
            this.weatherIconTextView.setTypeface(typeface);
        }
    }


}
