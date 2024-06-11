package com.example.bookingapptim14.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.enums.ConfirmationMethod;
import com.example.bookingapptim14.host.ApprovedReservationsViewHolder;
import com.example.bookingapptim14.host.ReservationRequestsViewHolder;
import com.example.bookingapptim14.models.AccommodationRequest;
import com.example.bookingapptim14.models.ReservationRequest;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationData;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ReservationRequestsAdapter extends RecyclerView.Adapter<ReservationRequestsViewHolder> {

    private List<ApprovedReservationData> reservations;
    private ReservationRequestsAdapter.OnRequestListener listener;

    public ReservationRequestsAdapter(List<ApprovedReservationData> reservations, OnRequestListener listener) {
        this.reservations = reservations;
        this.listener = listener;
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

        holder.approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRequestApproved(reservation);
            }
        });

        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRequestRejected(reservation);
            }
        });
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

    public interface OnRequestListener {
        void onRequestApproved(ApprovedReservationData request);
        void onRequestRejected(ApprovedReservationData request);
    }

}

