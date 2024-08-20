package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    private OnReportClickListener reportClickListener;

    // Dodajemo konstruktor koji prima OnReportClickListener
    public ApprovedReservationsAdapter(List<ApprovedReservationData> reservations, OnReportClickListener reportClickListener) {
        this.reservations = reservations;
        this.reportClickListener = reportClickListener;
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

        holder.report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Prikazi polje za unos razloga i dugme za submit
                holder.reportReasonEditText.setVisibility(View.VISIBLE);
                holder.submitReportButton.setVisibility(View.VISIBLE);
            }
        });

        holder.submitReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = holder.reportReasonEditText.getText().toString().trim();
                if (!reason.isEmpty()) {
                    // Pozovite onReportClick metodu iz reportClickListener-a
                    reportClickListener.onReportClick(reservation, reason);
                    holder.reportReasonEditText.setVisibility(View.GONE);
                    holder.submitReportButton.setVisibility(View.GONE);
                } else {
                    Toast.makeText(holder.itemView.getContext(), "Reason cannot be empty", Toast.LENGTH_SHORT).show();
                }
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

    // Interfejs za klik na Report dugme
    public interface OnReportClickListener {
        void onReportClick(ApprovedReservationData reservation, String reason);
    }
}
