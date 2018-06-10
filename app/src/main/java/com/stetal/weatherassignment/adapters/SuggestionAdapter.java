package com.stetal.weatherassignment.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.stetal.weatherassignment.suggestions.MainRequest;
import com.stetal.weatherassignment.database.model.City;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SuggestionAdapter extends ArrayAdapter<City> {

    protected static final String TAG = "SuggestionAdapter";
    private List<City> suggestions;

    public SuggestionAdapter(Activity context) {
        super(context, android.R.layout.simple_dropdown_item_1line);
        suggestions = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public City getItem(int index) {
        return suggestions.get(index);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<City> new_suggestions;
                    try {
                        new_suggestions = MainRequest.retrieveSuggestions(constraint.toString());

                        suggestions.clear();
                        suggestions.addAll(new_suggestions);

                        // Now assign the values and count to the FilterResults
                        // object
                        filterResults.values = suggestions;
                        filterResults.count = suggestions.size();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

}
