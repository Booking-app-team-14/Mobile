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
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationGuestData;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ApprovedReservationsGuestViewHolder extends RecyclerView.ViewHolder {

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
    private TextView cancellationDeadline;
    public Button cancelReservation;

    public ApprovedReservationsGuestViewHolder(@NonNull View itemView) {
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
        dateTo =  itemView.findViewById(R.id.guestApprovedReservationsToDate);
        cancellationDeadline = itemView.findViewById(R.id.guestApprovedReservationsCancellationDeadline);
        cancelReservation = itemView.findViewById(R.id.guestApprovedReservationsCancelButton);
    }

    public void bind(ApprovedReservationGuestData accommodation) {
        guestUsername.setText(accommodation.getGuestUsername());
        long epochMillis = Integer.parseInt(accommodation.getDateRequested()) * 1000L;
        Date date = new Date(epochMillis);
        SimpleDateFormat simpleFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        String dateRequestedString = "(" + simpleFormatter.format(date) + "),";
        dateRequested.setText(dateRequestedString);
        String cancellationsString = accommodation.getNumberOfCancellations() + " cancellations";
        numberOfCancellations.setText(cancellationsString);
        accommodationType.setText(accommodation.getType());
        accommodationRating.setRating((float)accommodation.getStars());
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
        String cancellationDeadlineString;
        if (accommodation.getCancellationDeadline().equals("1"))
            cancellationDeadlineString = accommodation.getCancellationDeadline() + " day";
        else
            cancellationDeadlineString = accommodation.getCancellationDeadline() + " days";
        cancellationDeadline.setText(cancellationDeadlineString);
        // disable cancel button if the deadline has passed from the start date of the reservation
        cancelReservation.setEnabled(!LocalDate.now().isAfter(startDateLocal.minusDays(Integer.parseInt(accommodation.getCancellationDeadline()))));
        // if disabled, change the color of the button
        if (!cancelReservation.isEnabled())
            cancelReservation.setBackgroundColor(cancelReservation.getContext().getResources().getColor(R.color.icon_gray));
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
    }

}
