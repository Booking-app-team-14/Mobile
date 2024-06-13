package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.admin.reports.AdminUserReportsViewHolder;
import com.example.bookingapptim14.models.dtos.ReportsDTO.UserReportsData;

import java.util.List;

public class AdminUserReportsAdapter extends RecyclerView.Adapter<AdminUserReportsViewHolder> {

    private List<UserReportsData> userReports;
    private OnReportListener listener;

    public AdminUserReportsAdapter(List<UserReportsData> userReports, OnReportListener listener) {
        this.userReports = userReports;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminUserReportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_user_reports, parent, false);
        return new AdminUserReportsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminUserReportsViewHolder holder, int position) {
        UserReportsData userReport = userReports.get(position);
        holder.bind(userReport);

        holder.blockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserBlocked(userReport);
            }
        });

        holder.dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onReportDismissed(userReport);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userReports.size();
    }

    public void setUserReports(List<UserReportsData> userReports) {
        this.userReports = userReports;
        notifyDataSetChanged();
    }

    public void removeItem(UserReportsData userReport) {
        int position = userReports.indexOf(userReport);
        if (position == -1) {
            return;
        }
        userReports.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnReportListener {
        void onUserBlocked(UserReportsData report);
        void onReportDismissed(UserReportsData report);
    }

}
