package com.example.bookingapptim14.host;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class MyAccommodationsViewHolder extends RecyclerView.ViewHolder {

    private ImageView mainAccommodationImage;
    private RatingBar accommodationRating;
    private TextView accommodationName;
    private TextView accommodationType;
    private TextView maxGuests;
    private TextView address;
    private TextView price;
    public Button updateAccommodationButton;
    public Button viewDetailsButton;

    public MyAccommodationsViewHolder(@NonNull View itemView) {
        super(itemView);
        mainAccommodationImage = itemView.findViewById(R.id.hostAccommodationImageView);
        accommodationRating = itemView.findViewById(R.id.hostAccommodationsAccommodationRating);
        accommodationType = itemView.findViewById(R.id.hostAccommodationsAccommodationType);
        accommodationName = itemView.findViewById(R.id.hostAccommodationsAccommodationName);
        price = itemView.findViewById(R.id.hostAccommodationsPrice);
        address = itemView.findViewById(R.id.hostAccommodationsAddress);
        maxGuests = itemView.findViewById(R.id.hostAccommodationsMaxGuests);
        updateAccommodationButton = itemView.findViewById(R.id.hostAccommodationsUpdateAccommodationButton);
        viewDetailsButton = itemView.findViewById(R.id.hostAccommodationsViewDetailsButton);
    }

    public void bind(OwnersAccommodationDTO accommodation) {
        accommodationName.setText(accommodation.getName());
        accommodationType.setText(accommodation.getType());
        maxGuests.setText(String.valueOf(accommodation.getMaxGuests()));
        address.setText(accommodation.getAddress());
        price.setText(String.valueOf(accommodation.getPrice()));
        accommodationRating.setRating((float)accommodation.getStars());
        String base64Image = accommodation.getMainPictureBytes();
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64Image);
            mainAccommodationImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
    }

}