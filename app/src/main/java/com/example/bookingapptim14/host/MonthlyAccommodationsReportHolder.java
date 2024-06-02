package com.example.bookingapptim14.host;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.Accommodation;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.AccommodationReportDTO;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.MonthlyAccommodationReport;
import com.example.bookingapptim14.models.dtos.OwnersAccommodationDTO;

import java.util.Base64;
import java.util.Map;

public class MonthlyAccommodationsReportHolder extends RecyclerView.ViewHolder {


    private TextView month;
    private TextView total;
    private TextView reservations;

    public MonthlyAccommodationsReportFragment fragment;




    public MonthlyAccommodationsReportHolder(@NonNull View itemView) {
        super(itemView);
       reservations = itemView.findViewById(R.id.reservations);
       month = itemView.findViewById(R.id.month);
       total = itemView.findViewById(R.id.total);

    }

    public void bind(MonthlyAccommodationReport report) {
        reservations.setText(String.valueOf(report.getNumberOfReservations()));
        total.setText(String.valueOf(report.getTotalProfit()));
        month.setText(report.getMonth());


        Map<String, MonthlyAccommodationReport> monthlyAccommodationReportMap = GlobalData.getInstance().getMonthlyReportMap();

        for (Map.Entry<String, MonthlyAccommodationReport> entry : monthlyAccommodationReportMap.entrySet()) {
            String month = entry.getKey();
            MonthlyAccommodationReport report1 = entry.getValue();

        }
    }
}