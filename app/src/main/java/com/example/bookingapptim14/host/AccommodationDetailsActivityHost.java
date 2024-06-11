package com.example.bookingapptim14.host;

import static android.content.Context.MODE_PRIVATE;

import static com.example.bookingapptim14.broadcastReceivers.ConnectivityReceiver.MY_PERMISSIONS_REQUEST_NOTIFICATION;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bookingapptim14.Adapters.AvailabilityAdapter;
import com.example.bookingapptim14.Adapters.ImageSliderAdapter;
import com.example.bookingapptim14.Adapters.LocalDateDeserializer;
import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.enums.RequestStatus;
import com.example.bookingapptim14.models.Accommodation;
import com.example.bookingapptim14.models.Availability;
import com.example.bookingapptim14.models.ReservationRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class AccommodationDetailsActivityHost extends AppCompatActivity {

    private Button bookingButton;
    private MapView mapView;
    private Accommodation accommodation;
    private ImageView viewPager;
    private ImageSliderAdapter adapter;
    private Long userId;
    private String jwtToken;
    private List<byte[]> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomodation_details_host);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        userId = sharedPreferences.getLong("userId", 0);

        Intent intent = getIntent();
        if (intent != null) {
            Long accommodationId = intent.getLongExtra("accommodation_id", -1);
            if (accommodationId != -1) {
                fetchAccommodationDetails(accommodationId);
            } else {
                Toast.makeText(this, "Accommodation ID not provided", Toast.LENGTH_SHORT).show();
                finish();
            }
        }


    }

    private void fetchAccommodationDetails(Long accommodationId) {
        new Thread(() -> {
            try {
                URL url = new URL(BuildConfig.IP_ADDR + "/api/accommodations/" + accommodationId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();

                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                            .create();
                    accommodation = gson.fromJson(response.toString(), Accommodation.class);

                    runOnUiThread(() -> {
                        initializeUI();
                        setupViewPager();
                        setupMapView();
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(AccommodationDetailsActivityHost.this, "Failed to fetch accommodation details", Toast.LENGTH_SHORT).show();
                        finish();
                    });

                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(AccommodationDetailsActivityHost.this, "Error fetching accommodation details", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        }).start();
    }

    private void initializeUI() {
        TextView nameTextView = findViewById(R.id.barcino);
        TextView descriptionTextView = findViewById(R.id.lorem_ipsum_dolor_sit_amet_consectetur__eu_est_sapien_congue_faucibus__tempor_iaculis_lobortis_posuere_in_non__est_placerat_vitae_nulla_nunc_porttitor_justo__nisl_cum_fermentum_bibendum_tortor_lorem_interdum_turpis_condimentum_felis_);
        TextView priceTextView = findViewById(R.id.__650);
        TextView ratingTextView = findViewById(R.id._4_8);
        TextView address1 = findViewById(R.id._2464_royal_ln__mesa__new_jersey_);

        address1.setText(accommodation.getAddress() + ", " + accommodation.getCity() + ", " + accommodation.getCountry());
        nameTextView.setText(accommodation.getName());
        descriptionTextView.setText(accommodation.getDescription());
        priceTextView.setText("$" + accommodation.getPricePerNight());
        ratingTextView.setText(String.valueOf(accommodation.getRating()));
    }

    private void setupViewPager() {
        viewPager = findViewById(R.id.rectangle_1);
        String base64Image = "";
        Set<String> images = accommodation.getImageBytes();
        for (String b1 : images) {
            base64Image = b1;
            break;
        }
        ;
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64Image);
            Bitmap bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            //Bitmap scaled = scaleDown(bm, 200, true);
            viewPager.setImageBitmap(bm);
        }


        adapter = new ImageSliderAdapter(this, imageList);
    }


    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min(
                maxImageSize / realImage.getWidth(),
                maxImageSize / realImage.getHeight());
        int width = Math.round(ratio * realImage.getWidth());
        int height = Math.round(ratio * realImage.getHeight());

        return Bitmap.createScaledBitmap(realImage, width, height, filter);
    }

    private void setupMapView() {
        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);

        GeoPoint point = geocodeLocation(accommodation.getCity(), accommodation.getCountry(), accommodation.getAddress());
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
