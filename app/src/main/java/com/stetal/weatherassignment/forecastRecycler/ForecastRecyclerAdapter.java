package com.stetal.weatherassignment.forecastRecycler;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stetal.weatherassignment.FontAwesome;
import com.stetal.weatherassignment.R;
import com.stetal.weatherassignment.database.model.ForecastSchema;
import com.stetal.weatherassignment.util.RemoveClickListener;

import java.util.ArrayList;

public class ForecastRecyclerAdapter extends RecyclerView.Adapter<ForecastRecyclerAdapter.RecyclerItemViewHolder> {

    private ArrayList<ForecastSchema> myList;
    private RemoveClickListener mListener;

    public ForecastRecyclerAdapter(ArrayList<ForecastSchema> myList) {
        this.myList = myList;
        this.mListener = null;
    }

    @Override
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weatherdetail_item, parent, false);
        return new RecyclerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerItemViewHolder holder, int position) {
        ForecastSchema item = myList.get(position);
        long expiry = item.getTimestamp();

        holder.forecastDescription.setText(item.getDescription());
        holder.forecastTemperature.setText(String.format("Temp: %d", item.getTemperature()));


    }

    public void removeItem(int position) {
        myList.remove(position);
        notifyItemRemoved(position);
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
        Log.d("notifyData ", myList.size() + "");
        this.myList = forecastList;
        notifyDataSetChanged();
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
        private RelativeLayout mainLayout;

        RecyclerItemViewHolder(final View v) {
            super(v);
            this.forecastDayOfWeek      = v.findViewById(R.id.WeatherForecastDay);
            this.forecastDate           = v.findViewById(R.id.WeatherForecastDate);
            this.forecastDescription    = v.findViewById(R.id.WeatherForecastDescription);
            this.forecastTemperature    = v.findViewById(R.id.WeatherForecastTemperature);
            this.weatherIconTextView    = v.findViewById(R.id.WeatherForecastIcon);

            Typeface typeface = Typeface.createFromAsset(v.getContext().getAssets(), "weatherfont.ttf");
            this.weatherIconTextView.setTypeface(typeface);

//            mainLayout.setOnClickListener(view -> Toast.makeText(view.getContext(), "Clicked: " + forecastTemperature.getText(), Toast.LENGTH_SHORT).show());
        }
    }


}
