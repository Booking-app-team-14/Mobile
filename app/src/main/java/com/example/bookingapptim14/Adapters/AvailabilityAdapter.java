package com.example.bookingapptim14.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.Availability;

import java.util.List;

public class AvailabilityAdapter extends BaseAdapter {

    private Context context;
    private List<Availability> availabilities;

    public AvailabilityAdapter(Context context, List<Availability> availabilities) {
        this.context = context;
        this.availabilities = availabilities;
    }

    @Override
    public int getCount() {
        return availabilities.size();
    }

    @Override
    public Object getItem(int position) {
        return availabilities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the layout for each item
            convertView = LayoutInflater.from(context).inflate(R.layout.availability_item, parent, false);

            // Create ViewHolder and store references to the views
            viewHolder = new ViewHolder();
            viewHolder.textViewAvailability = convertView.findViewById(R.id.textViewAvailability);
            convertView.setTag(viewHolder);
        } else {
            // Reuse existing ViewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the availability at the current position
        Availability availability = (Availability) getItem(position);

        // Format the date range and price
        String availabilityInfo = String.format("%s - %s\nPrice: $%.2f per night",
                availability.getStartDate(), availability.getEndDate(), availability.getSpecialPrice());

        // Set the text to the TextView
        viewHolder.textViewAvailability.setText(availabilityInfo);

        return convertView;
    }

    // ViewHolder pattern to improve ListView performance
    private static class ViewHolder {
        TextView textViewAvailability;
    }
}
