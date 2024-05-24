package com.example.bookingapptim14.guest;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.NotificationDTO.Guest.GuestNotificationsData;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;

public class GuestNotificationsViewHolder extends RecyclerView.ViewHolder {

    private TextView accepted;
    private CircleImageView ownerProfilePicture;
    private TextView ownerUsername;
    private TextView dateSentAt;
    private ImageView accommodationImage;
    private TextView accommodationName;
    private TextView accommodationType;
    private RatingBar accommodationRating;
    private TextView price;
    private TextView guests;
    private TextView dateFrom;
    private TextView dateTo;
    public Button deleteButton;

    public GuestNotificationsViewHolder(@NonNull View itemView) {
        super(itemView);
        accepted = itemView.findViewById(R.id.guestNotificationAccepted);
        ownerProfilePicture = itemView.findViewById(R.id.guestNotificationOwnerProfilePicture);
        ownerUsername = itemView.findViewById(R.id.guestNotificationOwnerUsername);
        dateSentAt = itemView.findViewById(R.id.guestNotificationDateSentAt);
        accommodationImage = itemView.findViewById(R.id.guestNotificationAccommodationImageView);
        accommodationName = itemView.findViewById(R.id.guestNotificationAccommodationName);
        accommodationType = itemView.findViewById(R.id.guestNotificationAccommodationType);
        accommodationRating = itemView.findViewById(R.id.guestNotificationAccommodationRating);
        price = itemView.findViewById(R.id.guestNotificationAccommodationPrice);
        guests = itemView.findViewById(R.id.guestNotificationAcommodationGuests);
        dateFrom = itemView.findViewById(R.id.guestNotificationAccommodationFromDate);
        dateTo = itemView.findViewById(R.id.guestNotificationAccommodationToDate);
        deleteButton = itemView.findViewById(R.id.guestNotificationDeleteNotificationButton);
    }

    public void bind(GuestNotificationsData notification) {
        accepted.setText(notification.getAccepted() ? "ACCEPTED" : "REJECTED");
        if (notification.getAccepted()){
            accepted.setTextColor(accepted.getContext().getResources().getColor(R.color.green));
        } else {
            accepted.setTextColor(accepted.getContext().getResources().getColor(R.color.red));
        }
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(Long.parseLong(notification.getSentAt()), 0, ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm:ss a");
        String formattedDate = dateTime.format(formatter);
        dateSentAt.setText(formattedDate);
        //
        // if Date as string
        //    String sentAt = "2022-03-01 10:30:00"; // example date string
        //    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //    LocalDateTime dateTime = LocalDateTime.parse(sentAt, formatter);
        //
        //    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm:ss a");
        //    String formattedDate = dateTime.format(outputFormatter);
        //
        //    System.out.println(formattedDate);
        //
        accommodationName.setText(notification.getReservation().getName());
        if (notification.getReservation().getType().equals("STUDIO")) {
            accommodationType.setText("Studio");
        } else if (notification.getReservation().getType().equals("ROOM")) {
            accommodationType.setText("Room");
        } else if (notification.getReservation().getType().equals("APARTMENT")) {
            accommodationType.setText("Apartment");
        } else if (notification.getReservation().getType().equals("VILLA")) {
            accommodationType.setText("Villa");
        } else if (notification.getReservation().getType().equals("HOTEL")) {
            accommodationType.setText("Hotel");
        }
        accommodationRating.setRating((float)notification.getReservation().getStars());
        price.setText(String.valueOf(notification.getReservation().getTotalPrice()));
        guests.setText(String.valueOf(notification.getReservation().getNumberOfGuests()));
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("MMM d, yyyy");
        String startDate = notification.getReservation().getStartDate().format(formatterDate);
        dateFrom.setText(startDate);
        String endDate = notification.getReservation().getEndDate().format(formatterDate);
        dateTo.setText(endDate);
        ownerUsername.setText(notification.getReservation().getUserUsername());
        String base64OwnerImage = notification.getReservation().getUserProfilePictureBytes();
        if (base64OwnerImage != null && !base64OwnerImage.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64OwnerImage);
            ownerProfilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
        String base64AccommodationImage = notification.getReservation().getMainPictureBytes();
        if (base64AccommodationImage != null && !base64AccommodationImage.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64AccommodationImage);
            accommodationImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
    }

}
