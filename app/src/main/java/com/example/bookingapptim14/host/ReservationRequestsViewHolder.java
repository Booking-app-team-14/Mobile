package com.example.bookingapptim14.host;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationData;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReservationRequestsViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView profilePicture;
    private TextView guestUsername;
    private TextView dateRequested;
    private TextView numberOfCancellations;
    private ImageView accommodationImage;
    private TextView accommodationType;
    private RatingBar accommodationRating;
    private TextView price;
    private TextView guests;
    private TextView accommodationName;
    private TextView dateFrom;
    private TextView dateTo;
    public Button approveButton;
    public Button rejectButton;

    public ReservationRequestsViewHolder(@NonNull View itemView) {
        super(itemView);
        profilePicture = itemView.findViewById(R.id.guestApprovedGuestProfilePicture);
        guestUsername = itemView.findViewById(R.id.guestApprovedReservationsGuestUsername);
        dateRequested = itemView.findViewById(R.id.guestApprovedReservationsDateRequested);
        numberOfCancellations = itemView.findViewById(R.id.guestApprovedReservationsCancellations);
        accommodationImage = itemView.findViewById(R.id.guestApprovedReservationsImageView);
        accommodationType = itemView.findViewById(R.id.guestApprovedReservationsAccommodationType);
        accommodationRating = itemView.findViewById(R.id.guestApprovedReservationsAccommodationRating);
        price = itemView.findViewById(R.id.guestApprovedReservationsPrice);
        guests = itemView.findViewById(R.id.guestApprovedReservationsGuests);
        accommodationName = itemView.findViewById(R.id.guestApprovedReservationsAccommodationName);
        dateFrom = itemView.findViewById(R.id.guestApprovedReservationsFromDate);
        dateTo = itemView.findViewById(R.id.guestApprovedReservationsToDate);
        approveButton = itemView.findViewById(R.id.hostApproveRequestButton);
        rejectButton = itemView.findViewById(R.id.hostRejectRequestButton);
    }

    public void bind(ApprovedReservationData accommodation) {
        guestUsername.setText(accommodation.getGuestUsername());
        // TODO: accommodation.getDateRequested() - "2024-06-12" not epoch seconds
        // long epochMillis = Integer.parseInt(accommodation.getDateRequested()) * 1000L;
        // Date date = new Date(epochMillis);
        // SimpleDateFormat simpleFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        // String dateRequestedString = "(" + simpleFormatter.format(date) + "),";
        String dateRequestedString = "(" + accommodation.getDateRequested() + ")";
        dateRequested.setText(dateRequestedString);
        String cancellationsString = accommodation.getNumberOfCancellations() + " cancellations";
        numberOfCancellations.setText(cancellationsString);
        accommodationType.setText(accommodation.getType());
        accommodationRating.setRating((float) accommodation.getStars());
        price.setText(String.valueOf(accommodation.getTotalPrice()));
        guests.setText(String.valueOf(accommodation.getNumberOfGuests()));
        accommodationName.setText(accommodation.getName());
        LocalDate startDateLocal = accommodation.getStartDate();
        LocalDate endDateLocal = accommodation.getEndDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.US);
        String startDate = startDateLocal.format(formatter);
        dateFrom.setText(startDate);
        String endDate = endDateLocal.format(formatter);
        dateTo.setText(endDate);
        String base64ImageGuest = accommodation.getUserProfilePictureBytes();
        if (base64ImageGuest != null && !base64ImageGuest.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64ImageGuest);
            profilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
        String base64Image = accommodation.getMainPictureBytes();
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64Image);
            accommodationImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }

        String status = accommodation.getRequestStatus();
        if ("DECLINED".equalsIgnoreCase(status)) {
            approveButton.setVisibility(View.GONE);
            rejectButton.setVisibility(View.GONE);
        } else if ("SENT".equalsIgnoreCase(status)) {
            approveButton.setVisibility(View.VISIBLE);
            rejectButton.setVisibility(View.VISIBLE);
        }
    }
}