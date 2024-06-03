package com.example.bookingapptim14.host;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapptim14.Adapters.AvailabilityAdapter;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.enums.RequestStatus;
import com.example.bookingapptim14.guest.AccommodationDetailsActivityGuest;
import com.example.bookingapptim14.models.Availability;
import com.example.bookingapptim14.models.ReservationRequest;
import com.example.bookingapptim14.models.SearchAccommodation;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AccomodationDetailsActivityHost extends AppCompatActivity {
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomodation_details_host);

        ImageButton popupButton =findViewById(R.id._bg__bi_three_dots_vertical);

        popupButton.setOnClickListener(v -> showPopupMenu(v));
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);


        popupMenu.getMenu().add("Update an accommodation");
        popupMenu.getMenu().add("Generate a report");

        Button updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateAccommodationIntent = new Intent(AccomodationDetailsActivityHost.this, UpdateAccommodationScreen.class);
                startActivity(updateAccommodationIntent);
            }
        });


        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getTitle().toString()) {
                case "Update an accommodation":
                    Intent updateAccommodationIntent = new Intent(AccomodationDetailsActivityHost.this, UpdateAccommodationScreen.class);
                    startActivity(updateAccommodationIntent);
                    return true;
                case "Generate a report":

                    return true;
                default:
                    return false;
            }
        });

        // Show the PopupMenu
        popupMenu.show();
    }

 */
private static final int MY_PERMISSIONS_REQUEST_NOTIFICATION = 1001;
    private Button bookingButton;
    private MapView mapView;
    private SearchAccommodation accommodation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomodation_details_host);

        Intent intent = getIntent();
        if (intent != null) {
            accommodation = (SearchAccommodation) intent.getSerializableExtra("accommodation");
        }

        if (accommodation != null) {
            Configuration.getInstance().setUserAgentValue("MyBookingApp/1.0");
            Configuration.getInstance().load(this, getSharedPreferences("osmdroid", MODE_PRIVATE));
            // Initialize MapView
            mapView = findViewById(R.id.mapView);
            mapView.setTileSource(TileSourceFactory.MAPNIK);
            mapView.setMultiTouchControls(true);
            mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);


            String city = accommodation.getCity();
            String country = accommodation.getCountry();
            String address = accommodation.getAddress();


            GeoPoint point = geocodeLocation(city, country, address);
            if (point != null) {
                mapView.getController().setZoom(15.0);
                mapView.getController().setCenter(point);

                Marker marker = new Marker(mapView);
                marker.setPosition(point);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setTitle("Address Location");
                mapView.getOverlays().add(marker);
                mapView.invalidate();
            } else {
                Toast.makeText(this, "Address not found", Toast.LENGTH_SHORT).show();
            }

            ImageView accommodationImageView = findViewById(R.id.rectangle_1);
            TextView nameTextView = findViewById(R.id.barcino);
            TextView descriptionTextView = findViewById(R.id.lorem_ipsum_dolor_sit_amet_consectetur__eu_est_sapien_congue_faucibus__tempor_iaculis_lobortis_posuere_in_non__est_placerat_vitae_nulla_nunc_porttitor_justo__nisl_cum_fermentum_bibendum_tortor_lorem_interdum_turpis_condimentum_felis_);
            TextView priceTextView = findViewById(R.id.__650);
            TextView ratingTextView = findViewById(R.id._4_8);
            TextView address1 = findViewById(R.id._2464_royal_ln__mesa__new_jersey_);

            int drawableResourceId = getResources().getIdentifier(accommodation.getImage(), "drawable", getPackageName());
            accommodationImageView.setImageResource(drawableResourceId);

            address1.setText(accommodation.getAddress()+", "+accommodation.getCity()+", "+accommodation.getCountry());
            nameTextView.setText(accommodation.getName());
            descriptionTextView.setText(accommodation.getDescription());
            priceTextView.setText("$" + accommodation.getPricePerNight());
            ratingTextView.setText(String.valueOf(accommodation.getRating()));


        }
    }

    private GeoPoint geocodeLocation(String city, String country, String address) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocationName(city + ", " + country + ", " + address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address location = addresses.get(0);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                return new GeoPoint(latitude, longitude);
            } else {
                Log.e("Geocoding", "Address not found: " + city + ", " + country + ", " + address);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Geocoding", "Geocoding failed", e);
            return null;
        }
    }
}
