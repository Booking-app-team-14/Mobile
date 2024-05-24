package com.example.bookingapptim14.admin.reports;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveAccommodationReviewsData;
import com.example.bookingapptim14.models.dtos.ReportsDTO.UserReportsData;

import java.time.format.DateTimeFormatter;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminUserReportsViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView reportingUserProfilePicture;
    private CircleImageView reportedUserProfilePicture;
    private TextView reportingUserUsername;
    private TextView reportedUserUsername;
    private TextView dateReported;
    private TextView reason;
    private TextView previousReports;
    public Button blockButton;
    public Button dismissButton;

    public AdminUserReportsViewHolder(@NonNull View itemView) {
        super(itemView);
        reportingUserProfilePicture = itemView.findViewById(R.id.adminUserReportsReportingUserProfilePicture);
        reportedUserProfilePicture = itemView.findViewById(R.id.adminUserReportsReportedUserProfilePicture);
        reportingUserUsername = itemView.findViewById(R.id.adminUserReportsReportingUserUsername);
        reportedUserUsername = itemView.findViewById(R.id.adminUserReportsReportedUserUsername);
        dateReported = itemView.findViewById(R.id.adminUserReportsDateReported);
        reason = itemView.findViewById(R.id.adminUserReportsReason);
        previousReports = itemView.findViewById(R.id.adminUserReportsReportedUserPreviousReports);
        blockButton = itemView.findViewById(R.id.adminUserReportsBlockUserButton);
        dismissButton = itemView.findViewById(R.id.adminUserReportsDismissReportButton);
    }

    public void bind(UserReportsData review) {
        reportingUserUsername.setText(review.getReportingUserUsername());
        reportedUserUsername.setText(review.getReportedUserUsername());
        dateReported.setText("(" + review.getSentAt().format(DateTimeFormatter.ofPattern("MMMM dd yyyy")) + ")");
        reason.setText("“" + review.getDescription() + "“");
        previousReports.setText("(" + review.getReportedUserNumberOfReports() + " previous reports)");
        String base64ReportingUserProfilePicture = review.getReportingUserProfilePictureBytes();
        if (base64ReportingUserProfilePicture != null && !base64ReportingUserProfilePicture.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64ReportingUserProfilePicture);
            reportingUserProfilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
        String base64ReportedUserProfilePicture = review.getReportedUserProfilePictureBytes();
        if (base64ReportedUserProfilePicture != null && !base64ReportedUserProfilePicture.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64ReportedUserProfilePicture);
            reportedUserProfilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
    }

}
