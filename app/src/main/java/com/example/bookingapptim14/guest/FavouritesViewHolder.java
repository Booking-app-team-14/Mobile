package com.example.bookingapptim14.guest;

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
import com.example.bookingapptim14.models.SearchAccommodation;
import com.example.bookingapptim14.models.dtos.OwnersAccommodationDTO;

import java.util.Base64;

public class FavouritesViewHolder extends RecyclerView.ViewHolder {

    private ImageView mainAccommodationImage;
    private RatingBar accommodationRating;
    private TextView accommodationName;
    private TextView accommodationType;
    private TextView maxGuests;
    private TextView address;
    private TextView price;
    public Button viewDetailsButton;

    public FavouritesViewHolder(@NonNull View itemView) {
        super(itemView);
        mainAccommodationImage = itemView.findViewById(R.id.hostAccommodationImageView);
        accommodationRating = itemView.findViewById(R.id.hostAccommodationsAccommodationRating);
        accommodationType = itemView.findViewById(R.id.hostAccommodationsAccommodationType);
        accommodationName = itemView.findViewById(R.id.hostAccommodationsAccommodationName);
        price = itemView.findViewById(R.id.hostAccommodationsPrice);
        address = itemView.findViewById(R.id.hostAccommodationsAddress);
        maxGuests = itemView.findViewById(R.id.hostAccommodationsMaxGuests);
        viewDetailsButton = itemView.findViewById(R.id.details);
    }

    public void bind(SearchAccommodation accommodation) {
        accommodationName.setText(accommodation.getName());
        accommodationType.setText(accommodation.getType());
        maxGuests.setText(String.valueOf(accommodation.getMaxGuests()));
        address.setText(accommodation.getAddress());
        price.setText(String.valueOf(accommodation.getPrice()));
        accommodationRating.setRating((float) accommodation.getStars());
        String base64Image = accommodation.getMainPictureBytes();
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64Image);
            Bitmap bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            Bitmap scaled = scaleDown(bm, 200, true);
            mainAccommodationImage.setImageBitmap(scaled);
        }
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min(
                maxImageSize / realImage.getWidth(),
                maxImageSize / realImage.getHeight());
        int width = Math.round(ratio * realImage.getWidth());
        int height = Math.round(ratio * realImage.getHeight());

        return Bitmap.createScaledBitmap(realImage, width, height, filter);
    }
}
