package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.admin.approval.AccommodationApprovalViewHolder;
import com.example.bookingapptim14.host.MyAccommodationsViewHolder;
import com.example.bookingapptim14.models.Accommodation;
import com.example.bookingapptim14.models.AccommodationRequest;
import com.example.bookingapptim14.models.dtos.OwnersAccommodationDTO;

import java.util.List;

public class HostAccommodationsAdapter extends RecyclerView.Adapter<MyAccommodationsViewHolder> {

    private List<OwnersAccommodationDTO> accommodations;
    private OnAccommodationListener listener;

    public HostAccommodationsAdapter(List<OwnersAccommodationDTO> accommodations, OnAccommodationListener listener) {
        this.accommodations = accommodations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyAccommodationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_host_accommodations, parent, false);
        return new MyAccommodationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAccommodationsViewHolder holder, int position) {
        OwnersAccommodationDTO accommodation = accommodations.get(position);
        holder.bind(accommodation);

        holder.viewDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAccommodationDetailsRequested(accommodation);
            }
        });

        holder.monthlyAccommodationReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMonthlyReportsRequested(accommodation);
            }
        });

        holder.updateAccommodationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAccommodationUpdate(accommodation);
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

        void onMonthlyReportsRequested(OwnersAccommodationDTO accommodation);
        void onAccommodationDetailsRequested(OwnersAccommodationDTO request);
        void onAccommodationUpdate(OwnersAccommodationDTO accommodation);
    }

}