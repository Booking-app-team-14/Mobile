package com.example.bookingapptim14.admin.approval;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveAccommodationReviewsData;

import java.time.format.DateTimeFormatter;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAndReviewsApprovalAccommodationViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView guestImage;
    private TextView guestUsername;
    private TextView datePosted;
    private TextView comment;
    private RatingBar ratingGiven;
    private ImageView accommodationImage;
    private TextView accommodationType;
    private RatingBar accommodationRating;
    private TextView accommodationName;
    public Button approveButton;
    public Button rejectButton;
    public Button viewAccommodationDetailsButton;

    public CommentsAndReviewsApprovalAccommodationViewHolder(@NonNull View itemView) {
        super(itemView);
        guestImage = itemView.findViewById(R.id.adminCommentsAndReviewsAccommodationsGuestProfilePicture);
        guestUsername = itemView.findViewById(R.id.adminCommentsAndReviewsAccommodationsOwnerUsername);
        datePosted = itemView.findViewById(R.id.adminCommentsAndReviewsAccommodationsDatePosted);
        comment = itemView.findViewById(R.id.adminCommentsAndReviewsAccommodationsComment);
        ratingGiven = itemView.findViewById(R.id.adminCommentsAndReviewsAccommodationsRatingGiven);
        accommodationImage = itemView.findViewById(R.id.adminCommentsAndReviewsAccommodationsAccommodationImage);
        accommodationType = itemView.findViewById(R.id.adminCommentsAndReviewsAccommodationsAccommodationType);
        accommodationRating = itemView.findViewById(R.id.adminCommentsAndReviewsAccommodationsAccommodationRating);
        accommodationName = itemView.findViewById(R.id.adminCommentsAndReviewsAccommodationsAccommodationName);
        approveButton = itemView.findViewById(R.id.adminCommentsAndReviewsAccommodationsApproveButton);
        rejectButton = itemView.findViewById(R.id.adminCommentsAndReviewsAccommodationsRejectButton);
        viewAccommodationDetailsButton = itemView.findViewById(R.id.adminCommentsAndReviewsAccommodationsViewAccommodationDetailsButton);
    }

    public void bind(ApproveAccommodationReviewsData review) {
        guestUsername.setText(review.getGuestUsername());
        datePosted.setText("(" + review.getSentAt().format(DateTimeFormatter.ofPattern("MMMM dd yyyy")) + ")");
        if (review.getComment().isEmpty()) {
            comment.setVisibility(View.GONE);
            itemView.findViewById(R.id.adminCommentsAndReviewsAccommodationsRequestMessageLabel).setVisibility(View.GONE);
        }
        else {
            comment.setText("“" + review.getComment() + "“");
        }
        if (review.getRating() == -1) {
            ratingGiven.setVisibility(View.GONE);
            itemView.findViewById(R.id.adminCommentsAndReviewsAccommodationsRatingLabel).setVisibility(View.GONE);
        }
        else {
            ratingGiven.setRating(review.getRating());
        }
        accommodationType.setText(review.getAccommodationType());
        accommodationRating.setRating(Long.valueOf(review.getRatingString()).floatValue());
        accommodationName.setText(review.getAccommodationName());
        String base64Image = review.getGuestProfilePictureBytes();
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64Image);
            guestImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        } else {
            byte[] decodedString = Base64.getDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAB4AAAAQ4AQMAAADSHVMAAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAGUExURX9/f4CAgCDDweQAAAABdFJOU/4a4wd9AAAED0lEQVR42u3PQQ0AAAgEIDf7V1ZfpjhoQG2WKWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYeHccIj+8AGdU9s1O0HsQgAAAABJRU5ErkJggg==");
            guestImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
        String base64AccommodationImage = review.getAccommodationPictureBytes();
        if (base64AccommodationImage != null && !base64AccommodationImage.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64AccommodationImage);
            accommodationImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        } else {
            byte[] decodedString = Base64.getDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAB4AAAAQ4AQMAAADSHVMAAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAGUExURX9/f4CAgCDDweQAAAABdFJOU/4a4wd9AAAED0lEQVR42u3PQQ0AAAgEIDf7V1ZfpjhoQG2WKWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYeHccIj+8AGdU9s1O0HsQgAAAABJRU5ErkJggg==");
            accommodationImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }

    }

}
