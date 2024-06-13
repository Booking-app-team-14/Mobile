package com.example.bookingapptim14.admin.reports;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.ReportsDTO.OwnerReviewReportsData;

import java.time.format.DateTimeFormatter;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminOwnerReviewReportsViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView reportingUserProfilePicture;
    private CircleImageView reportedUserProfilePicture;
    private TextView reportingUserUsername;
    private TextView reportedUserUsername;
    private TextView datePosted;
    private TextView comment;
    private RatingBar ratingGiven;
    private RatingBar ownersRating;
    private TextView reason;
    public Button removeButton;
    public Button dismissButton;

    public AdminOwnerReviewReportsViewHolder(@NonNull View itemView) {
        super(itemView);
        reportingUserProfilePicture = itemView.findViewById(R.id.adminOwnerReviewReportsReportingUserProfilePicture);
        reportedUserProfilePicture = itemView.findViewById(R.id.adminOwnerReviewReportsReportedUserProfilePicture);
        reportingUserUsername = itemView.findViewById(R.id.adminOwnerReviewReportsReportingUserUsername);
        reportedUserUsername = itemView.findViewById(R.id.adminOwnerReviewReportsReportedUserUsername);
        datePosted = itemView.findViewById(R.id.adminOwnerReviewReportsDateReported);
        comment = itemView.findViewById(R.id.adminOwnerReviewReportsComment);
        ratingGiven = itemView.findViewById(R.id.adminOwnerReviewReportsRatingGiven);
        ownersRating = itemView.findViewById(R.id.adminOwnerReviewReportsOwnersRating);
        reason = itemView.findViewById(R.id.adminOwnerReviewReportsReason);
        removeButton = itemView.findViewById(R.id.adminOwnerReviewReportsRemoveButton);
        dismissButton = itemView.findViewById(R.id.adminOwnerReviewReportsDismissButton);
    }

    public void bind(OwnerReviewReportsData review) {
        reportingUserUsername.setText(review.getSenderUsername());
        reportedUserUsername.setText(review.getRecipientUsername());
        datePosted.setText("(" + review.getSentAt().format(DateTimeFormatter.ofPattern("MMMM dd yyyy")) + ")");
        if (review.getOwnerReview().getComment() == null || review.getOwnerReview().getComment().isEmpty()) {
            comment.setVisibility(View.GONE);
            itemView.findViewById(R.id.adminOwnerReviewReportsCommentLabel).setVisibility(View.GONE);
        }
        else {
            comment.setText("“" + review.getOwnerReview().getComment() + "“");
        }
        if (review.getOwnerReview().getRating() == -1) {
            ratingGiven.setVisibility(View.GONE);
            itemView.findViewById(R.id.adminOwnerReviewReportsRatingLabel).setVisibility(View.GONE);
        }
        else {
            ratingGiven.setRating(review.getOwnerReview().getRating());
        }
        String ownersRating = review.getRatingBeforeString();
        if (ownersRating.equals("-1")) {
            this.ownersRating.setVisibility(View.GONE);
        }
        else {
            this.ownersRating.setRating(Float.parseFloat(ownersRating));
        }
        reason.setText("“" + review.getReason() + "“");
        String base64ReportingUserProfilePicture = review.getSenderProfilePictureBytes();
        if (base64ReportingUserProfilePicture != null && !base64ReportingUserProfilePicture.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64ReportingUserProfilePicture);
            reportingUserProfilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
        String base64ReportedUserProfilePicture = review.getRecipientProfilePictureBytes();
        if (base64ReportedUserProfilePicture != null && !base64ReportedUserProfilePicture.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64ReportedUserProfilePicture);
            reportedUserProfilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
    }

}
