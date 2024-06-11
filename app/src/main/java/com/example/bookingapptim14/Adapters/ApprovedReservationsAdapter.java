package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.admin.approval.AccommodationApprovalViewHolder;
import com.example.bookingapptim14.host.ApprovedReservationsViewHolder;
import com.example.bookingapptim14.host.MyAccommodationsViewHolder;
import com.example.bookingapptim14.models.Accommodation;
import com.example.bookingapptim14.models.AccommodationRequest;
import com.example.bookingapptim14.models.dtos.OwnersAccommodationDTO;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationData;

import java.util.List;

public class ApprovedReservationsAdapter extends RecyclerView.Adapter<ApprovedReservationsViewHolder> {

    private List<ApprovedReservationData> reservations;

    public ApprovedReservationsAdapter(List<ApprovedReservationData> reservations) {
        this.reservations = reservations;
    }

    @NonNull
    @Override
    public ApprovedReservationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_host_approved_reservations, parent, false);
        return new ApprovedReservationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovedReservationsViewHolder holder, int position) {
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