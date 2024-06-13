package com.example.bookingapptim14.admin.reports;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewReportsData;

import java.time.format.DateTimeFormatter;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminAccommodationReviewReportsViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView userProfilePicture;
    public TextView username;
    public TextView datePosted;
    public TextView comment;
    public RatingBar ratingGiven;
    public ImageView accommodationImage;
    public TextView accommodationName;
    public TextView accommodationType;
    public RatingBar accommodationRating;
    public TextView reason;
    public Button detailsButton;
    public Button removeButton;
    public Button dismissButton;

    public AdminAccommodationReviewReportsViewHolder(@NonNull View itemView) {
        super(itemView);
        userProfilePicture = itemView.findViewById(R.id.adminAccommodationReviewsUserProfilePicture);
        username = itemView.findViewById(R.id.adminAccommodationReviewsUserUsername);
        datePosted = itemView.findViewById(R.id.adminAccommodationReviewsDatePosted);
        comment = itemView.findViewById(R.id.adminAccommodationReviewsComment);
        ratingGiven = itemView.findViewById(R.id.adminAccommodationReviewsRatingGiven);
        accommodationImage = itemView.findViewById(R.id.adminAccommodationReviewsAccommodationImage);
        accommodationName = itemView.findViewById(R.id.adminAccommodationReviewsAccommodationName);
        accommodationType = itemView.findViewById(R.id.adminAccommodationReviewsAccommodationType);
        accommodationRating = itemView.findViewById(R.id.adminAccommodationReviewsAccommodationRating);
        reason = itemView.findViewById(R.id.adminAccommodationReviewsReason);
        detailsButton = itemView.findViewById(R.id.adminAccommodationReviewsDetailsButton);
        removeButton = itemView.findViewById(R.id.adminAccommodationReviewsRemoveButton);
        dismissButton = itemView.findViewById(R.id.adminAccommodationReviewsDismissButton);
    }

    public void bind(AccommodationReviewReportsData review) {
        username.setText(review.getUserUsername());
        datePosted.setText("(" + review.getSentAt().format(DateTimeFormatter.ofPattern("MMMM dd yyyy")) + ")");
        if (review.getAccommodationReviewDTO().getComment() == null || review.getAccommodationReviewDTO().getComment().isEmpty()) {
            comment.setVisibility(View.GONE);
            itemView.findViewById(R.id.adminAccommodationReviewsCommentLabel).setVisibility(View.GONE);
        }
        else {
            comment.setText("“" + review.getAccommodationReviewDTO().getComment() + "“");
        }
        if (review.getAccommodationReviewDTO().getRating() == null || review.getAccommodationReviewDTO().getRating() == -1) {
            ratingGiven.setVisibility(View.GONE);
            itemView.findViewById(R.id.adminAccommodationReviewsRatingLabel).setVisibility(View.GONE);
        }
        else {
            ratingGiven.setRating(review.getAccommodationReviewDTO().getRating());
        }
        accommodationName.setText(review.getAccommodationName());
        accommodationType.setText(review.getAccommodationType());
        if (review.getAccommodationReviewDTO().getRating() != null && review.getAccommodationReviewDTO().getRating() != -1) {
            accommodationRating.setRating(review.getAccommodationReviewDTO().getRating());
        }
        reason.setText("“" + review.getReason() + "“");
        String base64AccommodationImage = review.getAccommodationImageBytes();
        if (base64AccommodationImage != null && !base64AccommodationImage.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64AccommodationImage);
            accommodationImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
        String base64UserProfilePicture = review.getUserProfilePictureBytes();
        if (base64UserProfilePicture != null && !base64UserProfilePicture.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64UserProfilePicture);
            userProfilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }

    }

}
