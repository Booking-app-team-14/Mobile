package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.admin.reports.AdminAccommodationReviewReportsViewHolder;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewReportsData;

import java.util.List;

public class AdminAccommodationReviewReportsAdapter extends RecyclerView.Adapter<AdminAccommodationReviewReportsViewHolder> {

    private List<AccommodationReviewReportsData> reports;
    private AdminAccommodationReviewReportsAdapter.OnReportListener listener;

    public AdminAccommodationReviewReportsAdapter(List<AccommodationReviewReportsData> reports, AdminAccommodationReviewReportsAdapter.OnReportListener listener) {
        this.reports = reports;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminAccommodationReviewReportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_accommodation_review_reports, parent, false);
        return new AdminAccommodationReviewReportsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAccommodationReviewReportsViewHolder holder, int position) {
        AccommodationReviewReportsData report = reports.get(position);
        holder.bind(report);

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onReviewRemoved(report);
            }
        });

        holder.dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onReviewReportDismissed(report);
            }
        });

        holder.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDetailsRequested(report);
            }
        });

    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public void setAccommodationReviewReports(List<AccommodationReviewReportsData> reports) {
        this.reports = reports;
        notifyDataSetChanged();
    }

    public void removeItem(AccommodationReviewReportsData report) {
        int position = reports.indexOf(report);
        if (position == -1) {
            return;
        }
        reports.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnReportListener {
        void onReviewRemoved(AccommodationReviewReportsData report);
        void onReviewReportDismissed(AccommodationReviewReportsData report);
        void onDetailsRequested(AccommodationReviewReportsData report);
    }

}
