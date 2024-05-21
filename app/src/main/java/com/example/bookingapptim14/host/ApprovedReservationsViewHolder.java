package com.example.bookingapptim14.host;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.Accommodation;
import com.example.bookingapptim14.models.AccommodationRequest;
import com.example.bookingapptim14.models.dtos.OwnersAccommodationDTO;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationData;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ApprovedReservationsViewHolder extends RecyclerView.ViewHolder {

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

    public ApprovedReservationsViewHolder(@NonNull View itemView) {
        super(itemView);
        profilePicture = itemView.findViewById(R.id.hostApprovedGuestProfilePicture);
        guestUsername = itemView.findViewById(R.id.hostApprovedReservationsGuestUsername);
        dateRequested = itemView.findViewById(R.id.hostApprovedReservationsDateRequested);
        numberOfCancellations = itemView.findViewById(R.id.hostApprovedReservationsCancellations);
        accommodationImage = itemView.findViewById(R.id.hostApprovedReservationsImageView);
        accommodationType = itemView.findViewById(R.id.hostApprovedReservationsAccommodationType);
        accommodationRating = itemView.findViewById(R.id.hostApprovedReservationsAccommodationRating);
        price = itemView.findViewById(R.id.hostApprovedReservationsPrice);
        guests = itemView.findViewById(R.id.hostApprovedReservationsGuests);
        accommodationName = itemView.findViewById(R.id.hostApprovedReservationsAccommodationName);
        dateFrom = itemView.findViewById(R.id.hostApprovedReservationsFromDate);
        dateTo =  itemView.findViewById(R.id.hostApprovedReservationsToDate);
    }

    public void bind(ApprovedReservationData accommodation) {
        guestUsername.setText(accommodation.getGuestUsername());
        String dateRequestedString = "(" + accommodation.getDateRequested() + ")";
        dateRequested.setText(dateRequestedString);
        String cancellationsString = accommodation.getNumberOfCancellations() + " cancellations";
        numberOfCancellations.setText(cancellationsString);
        accommodationType.setText(accommodation.getType());
        accommodationRating.setRating((float)accommodation.getStars());
        price.setText(String.valueOf(accommodation.getTotalPrice()));
        guests.setText(String.valueOf(accommodation.getNumberOfGuests()));
        accommodationName.setText(accommodation.getAccommodationName());
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        String startDate = formatter.format(accommodation.getStartDate());
        dateFrom.setText(startDate);
        String endDate = formatter.format(accommodation.getEndDate());
        dateTo.setText(endDate);
        String base64ImageGuest = accommodation.getUserProfilePictureBytes();
        if (base64ImageGuest != null) {
            byte[] decodedString = Base64.getDecoder().decode(base64ImageGuest);
//            String imageType = accommodation.getImageType();
            profilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
        else {
            profilePicture.setImageResource(R.drawable.gray_background_box);
        }
        String base64Image = accommodation.getMainPictureBytes();
        if (base64Image != null) {
            byte[] decodedString = Base64.getDecoder().decode(base64Image);
//            String imageType = accommodation.getImageType();
            accommodationImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
        else {
            accommodationImage.setImageResource(R.drawable.gray_background_box);
        }
    }

}