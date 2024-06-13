package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.host.AccommodationReportsHolder;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.AccommodationReportDTO;

import java.util.List;

public class AccommodationReportsAdapter extends RecyclerView.Adapter<AccommodationReportsHolder> {
    private List<AccommodationReportDTO> accommodationReports;

    public AccommodationReportsAdapter(List<AccommodationReportDTO> accommodationReports) {
        this.accommodationReports = accommodationReports;
    }

    @NonNull
    @Override
    public AccommodationReportsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accommodatin_report, parent, false);
        return new AccommodationReportsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccommodationReportsHolder holder, int position) {
        AccommodationReportDTO accommodation = accommodationReports.get(position);
        holder.bind(accommodation);

    }

    @Override
    public int getItemCount() {
        return accommodationReports.size();
    }

    public void setAccommodations(List<AccommodationReportDTO> accommodations) {
        this.accommodationReports = accommodations;
        notifyDataSetChanged();
    }

    public void removeItem(AccommodationReportDTO accommodation) {
        int position = accommodationReports.indexOf(accommodation);
        if (position == -1) {
            return;
        }
        accommodationReports.remove(position);
        notifyItemRemoved(position);
    }

}
