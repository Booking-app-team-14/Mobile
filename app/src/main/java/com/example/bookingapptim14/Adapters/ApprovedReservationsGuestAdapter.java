package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.guest.ApprovedReservationsGuestViewHolder;
import com.example.bookingapptim14.host.ApprovedReservationsViewHolder;
import com.example.bookingapptim14.models.AccommodationRequest;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationData;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationGuestData;

import java.util.List;

public class ApprovedReservationsGuestAdapter extends RecyclerView.Adapter<ApprovedReservationsGuestViewHolder> {

    private List<ApprovedReservationGuestData> reservations;
    private OnReservationListener listener;

    public ApprovedReservationsGuestAdapter(List<ApprovedReservationGuestData> reservations, OnReservationListener listener) {
        this.reservations = reservations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ApprovedReservationsGuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest_approved_reservations, parent, false);
        return new ApprovedReservationsGuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovedReservationsGuestViewHolder holder, int position) {
        ApprovedReservationGuestData reservation = reservations.get(position);
        holder.bind(reservation);

        holder.cancelReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onReservationCancelled(reservation);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void setReservations(List<ApprovedReservationGuestData> reservations) {
        this.reservations = reservations;
        notifyDataSetChanged();
    }

    public void removeItem(ApprovedReservationGuestData reservation) {
        int position = reservations.indexOf(reservation);
        if (position == -1) {
            return;
        }
        reservations.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnReservationListener {
        void onReservationCancelled(ApprovedReservationGuestData reservation);
    }

}
