package com.example.bookingapptim14.admin.approval;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveOwnerReviewsData;

import java.time.format.DateTimeFormatter;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAndReviewsApprovalOwnerViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView guestImage;
    private TextView guestUsername;
    private TextView datePosted;
    private TextView comment;
    private RatingBar ratingGiven;
    private CircleImageView ownerImage;
    private TextView ownerUsername;
    public Button approveButton;
    public Button rejectButton;

    public CommentsAndReviewsApprovalOwnerViewHolder(@NonNull View itemView) {
        super(itemView);
        guestImage = itemView.findViewById(R.id.adminCommentsAndReviewsOwnerGuestProfilePicture);
        guestUsername = itemView.findViewById(R.id.adminCommentsAndReviewsOwnerGuestUsername);
        datePosted = itemView.findViewById(R.id.adminCommentsAndReviewsOwnerDatePosted);
        comment = itemView.findViewById(R.id.adminCommentsAndReviewsOwnerComment);
        ratingGiven = itemView.findViewById(R.id.adminCommentsAndReviewsOwnerRatingGiven);
        ownerImage = itemView.findViewById(R.id.adminCommentsAndReviewsOwnerOwnerProfilePicture);
        ownerUsername = itemView.findViewById(R.id.adminCommentsAndReviewsOwnerOwnerUsername);
        approveButton = itemView.findViewById(R.id.adminCommentsAndReviewsOwnerApproveButton);
        rejectButton = itemView.findViewById(R.id.adminCommentsAndReviewsOwnerRejectButton);
    }

    public void bind(ApproveOwnerReviewsData review) {
        guestUsername.setText(review.getGuestUsername());
        datePosted.setText("(" + review.getTimestamp().format(DateTimeFormatter.ofPattern("MMMM dd yyyy")) + ")");
        if (review.getComment() == null || review.getComment().isEmpty()) {
            comment.setVisibility(View.GONE);
            itemView.findViewById(R.id.adminCommentsAndReviewsOwnerCommentLabel).setVisibility(View.GONE);
        } else {
            comment.setText("“" + review.getComment() + "“");
        }
        if (review.getRating() == -1) {
            ratingGiven.setVisibility(View.GONE);
            itemView.findViewById(R.id.adminCommentsAndReviewsOwnerRatingLabel).setVisibility(View.GONE);
        } else {
            ratingGiven.setRating(review.getRating());
        }
        ownerUsername.setText(review.getOwnerUsername());
        String base64Image = review.getGuestProfilePictureBytes();
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64Image);
            guestImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
        String base64OwnerImage = review.getOwnerProfilePictureBytes();
        if (base64OwnerImage != null && !base64OwnerImage.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64OwnerImage);
            ownerImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
    }

}
