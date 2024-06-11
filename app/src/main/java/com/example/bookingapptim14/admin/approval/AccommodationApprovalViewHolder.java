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
import com.example.bookingapptim14.models.AccommodationRequest;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccommodationApprovalViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView ownerImage;
    private TextView accommodationName;
    private TextView ownerUsername;
    private TextView datePosted;
    private TextView requestMessage;
    private TextView accommodationType;
    private RatingBar accommodationRating;
    private TextView accommodationPostedAgo;
    private ImageView accommodationRequestType;
    public Button rejectButton;
    public Button approveButton;
    public Button viewDetailsButton;

    public AccommodationApprovalViewHolder(@NonNull View itemView) {
        super(itemView);
        ownerImage = itemView.findViewById(R.id.ownerProfilePicture);
        ownerUsername = itemView.findViewById(R.id.ownerUsername);
        datePosted = itemView.findViewById(R.id.datePosted);
        requestMessage = itemView.findViewById(R.id.requestMessage);
        accommodationType = itemView.findViewById(R.id.accommodationType);
        accommodationRating = itemView.findViewById(R.id.accommodationRating);
        accommodationPostedAgo = itemView.findViewById(R.id.accommodationPostedAgo);
        accommodationName = itemView.findViewById(R.id.accommodationName);
        accommodationRequestType = itemView.findViewById(R.id.accommodationRequestType);
        rejectButton = itemView.findViewById(R.id.rejectButton);
        approveButton = itemView.findViewById(R.id.approveButton);
        viewDetailsButton = itemView.findViewById(R.id.viewDetailsButton);
    }

    public void bind(AccommodationRequest request) {
        ownerUsername.setText(request.getOwnerUsername());
        String date = request.getDateRequested();
        Instant instant = Instant.ofEpochSecond(Long.parseLong(date));
        ZoneId zoneId = ZoneId.of("Europe/Belgrade");
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String formattedDateTime = "(" + zonedDateTime.format(formatter) + ")";
        datePosted.setText(formattedDateTime);
        requestMessage.setText(request.getMessage());
        accommodationType.setText(request.getType());
        accommodationRating.setRating(Long.valueOf(request.getStars()).floatValue());
        accommodationPostedAgo.setText(getPostedAgo(instant));
        accommodationName.setText(request.getName());
        if (request.getRequestType().equals("new")) {
            accommodationRequestType.setImageResource(R.drawable.ic_new);
        } else {
            accommodationRequestType.setImageResource(R.drawable.ic_updated);
        }
        String base64Image = request.getOwnerProfilePictureBytes();
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64Image);
            ownerImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
    }

    public static String getPostedAgo(Instant date) {
        Instant now = Instant.now();
        Duration difference = Duration.between(date, now);
        long seconds = difference.getSeconds();

        if (seconds < 60) {
            return "just now";
        } else if (seconds < 60 * 60) {
            long minutes = seconds / 60;
            return minutes + " minutes ago";
        } else if (seconds < 24 * 60 * 60) {
            long hours = seconds / (60 * 60);
            return hours + " hours ago";
        } else {
            long days = seconds / (24 * 60 * 60);
            return days + " days ago";
        }
    }

}


