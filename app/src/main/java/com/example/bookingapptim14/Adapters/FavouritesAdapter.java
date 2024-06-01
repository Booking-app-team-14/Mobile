package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.guest.FavouritesViewHolder;
import com.example.bookingapptim14.host.MyAccommodationsViewHolder;
import com.example.bookingapptim14.models.dtos.OwnersAccommodationDTO;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesViewHolder>{
    private List<OwnersAccommodationDTO> accommodations;
    private FavouritesAdapter.OnAccommodationListener listener;

    public FavouritesAdapter(List<OwnersAccommodationDTO> accommodations, FavouritesAdapter.OnAccommodationListener listener) {
        this.accommodations = accommodations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_accommodation_item, parent, false);
        return new FavouritesViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull FavouritesViewHolder holder, int position) {
        OwnersAccommodationDTO accommodation = accommodations.get(position);
        holder.bind(accommodation);

        holder.viewDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAccommodationDetailsRequested(accommodation);
            }
        });


    }

    @Override
    public int getItemCount() {
        return accommodations.size();
    }

    public void setAccommodations(List<OwnersAccommodationDTO> accommodations) {
        this.accommodations = accommodations;
        notifyDataSetChanged();
    }

    public void removeItem(OwnersAccommodationDTO accommodation) {
        int position = accommodations.indexOf(accommodation);
        if (position == -1) {
            return;
        }
        accommodations.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnAccommodationListener {
        void onAccommodationDetailsRequested(OwnersAccommodationDTO request);
    }
}
