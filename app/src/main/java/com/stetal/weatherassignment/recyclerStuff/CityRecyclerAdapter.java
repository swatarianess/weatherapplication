package com.stetal.weatherassignment.recyclerStuff;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stetal.weatherassignment.Basic.CityData;
import com.stetal.weatherassignment.FontManager;
import com.stetal.weatherassignment.R;

import java.util.ArrayList;

/**
 * @author Ultraphatty
 * @version 0.0.1
 * @since 28/02/2018
 */

public class CityRecyclerAdapter extends RecyclerView.Adapter<CityRecyclerAdapter.RecyclerItemViewHolder> {

    private ArrayList<CityData> myList = new ArrayList<>();
    private int mLastPosition = 0;
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

        holder.weatherIconTextView.setTypeface(FontManager.getTypeface(holder.mainLayout.getContext(),FontManager.FONTAWESOME));
        holder.weatherIconTextView.setText(item.getCurrentWeatherIcon());
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
            cityNameTextView = parent.findViewById(R.id.cityName);
            lastUpdatedTextView = parent.findViewById(R.id.cityLastUpdated);
            mainLayout = parent.findViewById(R.id.cityRowLayout);
            weatherIconTextView = parent.findViewById(R.id.weatherIconView);

            mainLayout.setOnClickListener(view -> Toast.makeText(itemView.getContext(), String.format("Clicked: %s",cityNameTextView.getText().toString()), Toast.LENGTH_SHORT).show());
        }
    }

}
