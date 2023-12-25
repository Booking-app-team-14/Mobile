package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.Accommodation;

import java.util.List;

public class AccommodationAdapter extends RecyclerView.Adapter<AccommodationAdapter.AccommodationViewHolder> {

    private List<Accommodation> accommodations;

    public AccommodationAdapter(List<Accommodation> accommodations) {
        this.accommodations = accommodations;
    }

    @NonNull
    @Override
    public AccommodationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_guest, parent, false);
        return new AccommodationViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AccommodationViewHolder holder, int position) {
        Accommodation currentAccommodation = accommodations.get(position);
        // Popunite UI elemente u kartici sa podacima iz currentAccommodation
        // holder.descriptionTextView.setText(currentAccommodation.getDescription());
        // holder.ratingTextView.setText("Rating: " + currentAccommodation.getRating());
        // holder.priceTextView.setText("Price/Night: $" + currentAccommodation.getPricePerNight());
        // Postavite slike ili ikonice
    }

    @Override
    public int getItemCount() {
        return accommodations.size();
    }

    static class AccommodationViewHolder extends RecyclerView.ViewHolder {
        // Definišite UI elemente za prikaz podataka o smeštaju
        // TextView descriptionTextView, ratingTextView, priceTextView;

        public AccommodationViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicijalizacija UI elemenata
        }
    }
}