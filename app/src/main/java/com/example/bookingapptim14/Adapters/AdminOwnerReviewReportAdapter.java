package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.admin.reports.AdminOwnerReviewReportsViewHolder;;
import com.example.bookingapptim14.models.dtos.ReportsDTO.OwnerReviewReportsData;

import java.util.List;

public class AdminOwnerReviewReportAdapter extends RecyclerView.Adapter<AdminOwnerReviewReportsViewHolder> {

    private List<OwnerReviewReportsData> reports;
    private AdminOwnerReviewReportAdapter.OnReportListener listener;

    public AdminOwnerReviewReportAdapter(List<OwnerReviewReportsData> reports, AdminOwnerReviewReportAdapter.OnReportListener listener) {
        this.reports = reports;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminOwnerReviewReportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_owner_review_reports, parent, false);
        return new AdminOwnerReviewReportsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOwnerReviewReportsViewHolder holder, int position) {
        OwnerReviewReportsData report = reports.get(position);
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

    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public void setOwnerReviewReports(List<OwnerReviewReportsData> reports) {
        this.reports = reports;
    }

    public void removeItem(OwnerReviewReportsData report) {
        int position = reports.indexOf(report);
        if (position == -1) {
            return;
        }
        reports.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnReportListener {
        void onReviewRemoved(OwnerReviewReportsData report);
        void onReviewReportDismissed(OwnerReviewReportsData report);
    }

}
