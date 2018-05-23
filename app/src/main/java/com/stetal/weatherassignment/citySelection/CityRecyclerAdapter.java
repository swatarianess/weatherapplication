package com.stetal.weatherassignment.citySelection;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stetal.weatherassignment.R;
import com.stetal.weatherassignment.database.model.FavouriteCitySchema;
import com.stetal.weatherassignment.util.RemoveClickListener;
import com.stetal.weatherassignment.weatherdetail.WeatherForecastActivity;

import java.util.ArrayList;

/**
 * @author Stephen Adu
 * @version 0.0.1
 * @since 28/02/2018
 */

public class CityRecyclerAdapter extends RecyclerView.Adapter<CityRecyclerAdapter.RecyclerItemViewHolder> {

    private ArrayList<FavouriteCitySchema> myList;
    private RemoveClickListener mListener;

    public CityRecyclerAdapter(ArrayList<FavouriteCitySchema> myList) {
        this.myList = myList;
        this.mListener = null;
    }

    @Override
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.citieslist, parent, false);
        return new RecyclerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerItemViewHolder holder, int position) {
        FavouriteCitySchema item = myList.get(position);
        long expiry = item.getTimestamp();

        holder.cityNameTextView.setText(String.format("%s, %s",item.getName(),item.getCountry()));
        holder.lastUpdatedTextView.setText(String.format("Updated: %s", DateUtils.getRelativeTimeSpanString(expiry)));

//        String hexWeatherIcon = item.getCurrentWeatherIcon().replace("&#x","").replace(";","");
        String hexWeatherIcon = "&#xf022;".replace("&#x","").replace(";","");
        long valLong = Long.parseLong(hexWeatherIcon,16);
        holder.weatherIconTextView.setText(String.valueOf((char) valLong));
    }

    /**
     * Retrieves the particular item from the adapter
     *
     * @param position The position of the item to return
     * @return Returns the Forecast item at the position
     */
    public FavouriteCitySchema getItem(int position){
        return myList.get(position);
    }

    public void removeItem(int position) {
        myList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public ArrayList<FavouriteCitySchema> getCityList() {
        return myList;
    }

    /**
     * @param cityData New set of data to add to Adapter
     */
    public void notifyData(ArrayList<FavouriteCitySchema> cityData){
        Log.d("notifyData ", myList.size() + "");
        this.myList = cityData;
        notifyDataSetChanged();
    }


    /**
     *  Inner class that handles the ViewHolder of the item.
     */
    class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView cityNameTextView;
        private final TextView lastUpdatedTextView;
        private final TextView weatherIconTextView;
        private RelativeLayout mainLayout;

        RecyclerItemViewHolder(final View parent) {
            super(parent);
            this.cityNameTextView = parent.findViewById(R.id.cityName);
            this.lastUpdatedTextView = parent.findViewById(R.id.cityLastUpdated);
            this.mainLayout = parent.findViewById(R.id.cityRowLayout);
            this.weatherIconTextView = parent.findViewById(R.id.weatherIconView);

            Typeface typeface = Typeface.createFromAsset(parent.getContext().getAssets(), "weatherfont.ttf");
            this.weatherIconTextView.setTypeface(typeface);

            mainLayout.setOnClickListener(view -> {
                Context context = view.getContext();
                Intent intent = new Intent(context, WeatherForecastActivity.class);
                intent.putExtra("CITY_NAME", cityNameTextView.getText().toString());
                context.startActivity(intent);
            });
        }
    }

}
