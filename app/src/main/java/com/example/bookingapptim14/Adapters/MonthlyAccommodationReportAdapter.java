package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.host.MonthlyAccommodationsReportHolder;
import com.example.bookingapptim14.models.Accommodation;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.MonthlyAccommodationReport;

import java.util.List;

public class MonthlyAccommodationReportAdapter extends RecyclerView.Adapter<MonthlyAccommodationsReportHolder> {

    private List<MonthlyAccommodationReport> requests;

    public MonthlyAccommodationReportAdapter(List<MonthlyAccommodationReport> requests) {
        this.requests = requests;
    }

    @NonNull
    @Override
    public MonthlyAccommodationsReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monthly, parent, false);
        return new MonthlyAccommodationsReportHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthlyAccommodationsReportHolder holder, int position) {
        MonthlyAccommodationReport reservation = requests.get(position);
        holder.bind(reservation);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public void setReports(List<MonthlyAccommodationReport> reservations) {
        this.requests = reservations;
        notifyDataSetChanged();
    }

    public void removeItem(MonthlyAccommodationReport reservation) {
        int position = requests.indexOf(reservation);
        if (position == -1) {
            return;
        }
        requests.remove(position);
        notifyItemRemoved(position);
    }

}
