package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.guest.ReservationRequestsGuestViewHolder;
import com.example.bookingapptim14.host.ReservationRequestsViewHolder;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationData;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationGuestData;

import java.util.List;

public class ReservationRequestsGuestAdapter extends RecyclerView.Adapter<ReservationRequestsGuestViewHolder> {

    private List<ApprovedReservationGuestData> reservations;
    private ReservationRequestsGuestAdapter.OnRequestListener listener;

    public ReservationRequestsGuestAdapter(List<ApprovedReservationGuestData> reservations, OnRequestListener listener) {
        this.reservations = reservations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReservationRequestsGuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest_requests, parent, false);
        return new ReservationRequestsGuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationRequestsGuestViewHolder holder, int position) {
        ApprovedReservationGuestData reservation = reservations.get(position);
        holder.bind(reservation);


        holder.cancelReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRequestCancelled(reservation);

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

    public interface OnRequestListener {
        void onRequestCancelled(ApprovedReservationGuestData request);

    }

}

