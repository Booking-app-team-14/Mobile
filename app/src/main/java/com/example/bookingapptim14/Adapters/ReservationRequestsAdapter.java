package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.enums.ConfirmationMethod;
import com.example.bookingapptim14.host.ApprovedReservationsViewHolder;
import com.example.bookingapptim14.host.ReservationRequestsViewHolder;
import com.example.bookingapptim14.models.ReservationRequest;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationData;

import java.util.List;

public class ReservationRequestsAdapter extends RecyclerView.Adapter<ReservationRequestsViewHolder> {

    private List<ApprovedReservationData> reservations;

    public ReservationRequestsAdapter(List<ApprovedReservationData> reservations) {
        this.reservations = reservations;
    }

    @NonNull
    @Override
    public ReservationRequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_host_requests, parent, false);
        return new ReservationRequestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationRequestsViewHolder holder, int position) {
        ApprovedReservationData reservation = reservations.get(position);
        holder.bind(reservation);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void setReservations(List<ApprovedReservationData> reservations) {
        this.reservations = reservations;
        notifyDataSetChanged();
    }

    public void removeItem(ApprovedReservationData reservation) {
        int position = reservations.indexOf(reservation);
        if (position == -1) {
            return;
        }
        reservations.remove(position);
        notifyItemRemoved(position);
    }

}

