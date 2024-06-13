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
import com.example.bookingapptim14.models.dtos.AccommodationDTO.AccommodationReportDTO;
import com.example.bookingapptim14.models.dtos.OwnersAccommodationDTO;

import java.util.Base64;

public class AccommodationReportsHolder extends RecyclerView.ViewHolder {

    private ImageView mainAccommodationImage;
    private RatingBar accommodationRating;
    private TextView accommodationName;
    private TextView accommodationType;
    private TextView minGuests;
    private TextView maxGuests;
    private TextView price;

    private TextView totalPrice;

    private TextView numberOfReservations;

    public AccommodationReportsHolder(@NonNull View itemView) {
        super(itemView);
        mainAccommodationImage = itemView.findViewById(R.id.hostAccommodationImageView);
        accommodationRating = itemView.findViewById(R.id.hostAccommodationsAccommodationRating);
        accommodationType = itemView.findViewById(R.id.hostAccommodationsAccommodationType);
        accommodationName = itemView.findViewById(R.id.hostAccommodationsAccommodationName);
        price = itemView.findViewById(R.id.hostAccommodationsPrice);
        minGuests = itemView.findViewById(R.id.hostAccommodationsAddress);
        maxGuests = itemView.findViewById(R.id.hostAccommodationsMaxGuests);
        totalPrice = itemView.findViewById(R.id.total);
        numberOfReservations = itemView.findViewById(R.id.reservations);

    }

    public void bind(AccommodationReportDTO accommodation) {
        accommodationName.setText(accommodation.getAccommodationName());
        accommodationType.setText(accommodation.getType().toString());
        maxGuests.setText(String.valueOf(accommodation.getMaxNumberOfGuests()));
        minGuests.setText(String.valueOf(accommodation.getMinNumberOfGuests()));
        price.setText(String.valueOf(accommodation.getPricePerNight()));
        accommodationRating.setRating(accommodation.getRating());
        totalPrice.setText(String.valueOf((int) accommodation.getTotalProfit()));
        numberOfReservations.setText(String.valueOf(accommodation.getNumberOfReservations()));
        String base64Image = accommodation.getImageBytes();
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64Image);
            mainAccommodationImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
    }
}
