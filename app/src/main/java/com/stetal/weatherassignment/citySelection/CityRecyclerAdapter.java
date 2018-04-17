package com.stetal.weatherassignment.citySelection;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stetal.weatherassignment.database.model.CityData;
import com.stetal.weatherassignment.R;
import com.stetal.weatherassignment.util.RemoveClickListener;

import java.util.ArrayList;

/**
 * @author Stephen Adu
 * @version 0.0.1
 * @since 28/02/2018
 */

public class CityRecyclerAdapter extends RecyclerView.Adapter<CityRecyclerAdapter.RecyclerItemViewHolder> {

    private ArrayList<CityData> myList = new ArrayList<>();
    private RemoveClickListener mListener;

    public CityRecyclerAdapter(ArrayList<CityData> myList) {
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
        CityData item = myList.get(position);
        long expiry = item.getLastUpdated() < 1000000000000L ? item.getLastUpdated() * 1000 : item.getLastUpdated();

        holder.cityNameTextView.setText(String.format("%s, %s",item.getCityName(),item.getCountry()));
        holder.lastUpdatedTextView.setText(String.format("Updated: %s",DateUtils.getRelativeTimeSpanString(expiry)));

        //TODO: Find out if the TypeFace is actually being changed or not..
        String hexWeatherIcon = item.getCurrentWeatherIcon().replace("&#x","").replace(";","");
        long valLong = Long.parseLong(hexWeatherIcon,16);

        holder.weatherIconTextView.setText(String.valueOf((char) valLong));
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    /**
     * @param cityData New set of data to add to Adapter
     */
    public void notifyData(ArrayList<CityData> cityData){
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

            mainLayout.setOnClickListener(view -> Toast.makeText(itemView.getContext(), String.format("Clicked: %s",cityNameTextView.getText().toString()), Toast.LENGTH_SHORT).show());
        }
    }

}
