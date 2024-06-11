package com.example.bookingapptim14.guest;

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
import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.enums.RequestStatus;
import com.example.bookingapptim14.models.Accommodation;
import com.example.bookingapptim14.models.Availability;
import com.example.bookingapptim14.models.ReservationRequest;
import com.google.gson.Gson;

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

public class AccommodationDetailsActivityGuest extends AppCompatActivity {

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
        setContentView(R.layout.activity_accomodation_details_guest);

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

                    Gson gson = new Gson();
                    accommodation = gson.fromJson(response.toString(), Accommodation.class);

                    runOnUiThread(() -> {
                        initializeUI();
                        setupViewPager();
                        setupMapView();
                        setupBookingButton();
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(AccommodationDetailsActivityGuest.this, "Failed to fetch accommodation details", Toast.LENGTH_SHORT).show();
                        finish();
                    });

                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(AccommodationDetailsActivityGuest.this, "Error fetching accommodation details", Toast.LENGTH_SHORT).show();
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
        String base64Image="";
        Set<String> images=accommodation.getImageBytes();
        for (String b1: images){
            base64Image = b1;
            break;
        };
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

    private void setupBookingButton() {
        bookingButton = findViewById(R.id.common_button);
        bookingButton.setOnClickListener(v -> showDialog());
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_reservation_request_guest, null);
        builder.setView(dialogView);

        ListView listViewAvailabilities = dialogView.findViewById(R.id.listViewAvailabilities);
        List<Availability> availabilities = accommodation.getAvailabilities().stream().collect(Collectors.toList());
        AvailabilityAdapter adapter = new AvailabilityAdapter(this, availabilities);
        listViewAvailabilities.setAdapter(adapter);
        adjustListViewHeight(listViewAvailabilities);

        DatePicker datePickerStart = dialogView.findViewById(R.id.datePickerStart);
        DatePicker datePickerEnd = dialogView.findViewById(R.id.datePickerEnd);
        NumberPicker numberPickerGuests = dialogView.findViewById(R.id.numberPickerGuests);
        Button buttonCalculate = dialogView.findViewById(R.id.buttonCalculate);
        TextView textViewRecap = dialogView.findViewById(R.id.textViewRecap);

        numberPickerGuests.setMinValue(accommodation.getMinNumberOfGuests());
        numberPickerGuests.setMaxValue(accommodation.getMaxNumberOfGuests());
        numberPickerGuests.setWrapSelectorWheel(true);

        Calendar calendar = Calendar.getInstance();
        datePickerStart.setMinDate(calendar.getTimeInMillis());

        datePickerStart.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), (view, year, monthOfYear, dayOfMonth) -> {
            Calendar checkOutCalendar = Calendar.getInstance();
            checkOutCalendar.set(year, monthOfYear, dayOfMonth);
            checkOutCalendar.add(Calendar.DAY_OF_MONTH, 1);
            datePickerEnd.setMinDate(checkOutCalendar.getTimeInMillis());

            if (datePickerEnd.getDayOfMonth() < dayOfMonth) {
                datePickerEnd.updateDate(year, monthOfYear, dayOfMonth + 1);
            }
        });

        builder.setPositiveButton("Reserve", null);
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(dialog -> alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false));
        alertDialog.show();

        Button buttonReserve = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        buttonReserve.setOnClickListener(v -> handleReservation(dialogView, alertDialog));

        buttonCalculate.setOnClickListener(v -> handleCalculateClick(datePickerStart, datePickerEnd, numberPickerGuests, textViewRecap, buttonReserve));
    }

    private void adjustListViewHeight(ListView listView) {
        AvailabilityAdapter adapter = (AvailabilityAdapter) listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void handleCalculateClick(DatePicker datePickerStart, DatePicker datePickerEnd, NumberPicker numberPickerGuests, TextView textViewRecap, Button buttonReserve) {
        int startDay = datePickerStart.getDayOfMonth();
        int startMonth = datePickerStart.getMonth() + 1;
        int startYear = datePickerStart.getYear();

        int endDay = datePickerEnd.getDayOfMonth();
        int endMonth = datePickerEnd.getMonth() + 1;
        int endYear = datePickerEnd.getYear();

        LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);
        LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);

        if (isDateRangeAvailable(startDate, endDate)) {
            int numOfGuests = numberPickerGuests.getValue();
            double totalPrice = calculateTotalAmount(startDate, endDate, numOfGuests);
            String recap = "Start Date: " + startDay + "/" + startMonth + "/" + startYear +
                    "\nEnd Date: " + endDay + "/" + endMonth + "/" + endYear +
                    "\nNumber of Guests: " + numOfGuests +
                    "\nTotal Amount: $" + totalPrice;

            textViewRecap.setText(recap);
            textViewRecap.setVisibility(View.VISIBLE);

            buttonReserve.setEnabled(true);
        } else {
            Toast.makeText(AccommodationDetailsActivityGuest.this, "Selected dates are not available.", Toast.LENGTH_SHORT).show();
            buttonReserve.setEnabled(false);
        }
    }

    private boolean isDateRangeAvailable(LocalDate startDate, LocalDate endDate) {
        for (Availability availability : accommodation.getAvailabilities()) {
            if (!startDate.isBefore(availability.getStartDate()) && !endDate.isAfter(availability.getEndDate())) {
                return true;
            }
        }
        return false;
    }

    private double calculateTotalAmount(LocalDate startDate, LocalDate endDate, int numOfGuests) {
        double totalAmount = 0.0;
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            for (Availability availability : accommodation.getAvailabilities()) {
                if (!currentDate.isBefore(availability.getStartDate()) && !currentDate.isAfter(availability.getEndDate())) {
                    totalAmount += availability.getSpecialPrice() * numOfGuests;
                    break;
                }
            }
            currentDate = currentDate.plusDays(1);
        }

        return totalAmount;
    }

    private void handleReservation(View dialogView, AlertDialog alertDialog) {
        DatePicker datePickerStart = dialogView.findViewById(R.id.datePickerStart);
        DatePicker datePickerEnd = dialogView.findViewById(R.id.datePickerEnd);
        NumberPicker numberPickerGuests = dialogView.findViewById(R.id.numberPickerGuests);

        int startDay = datePickerStart.getDayOfMonth();
        int startMonth = datePickerStart.getMonth() + 1;
        int startYear = datePickerStart.getYear();

        int endDay = datePickerEnd.getDayOfMonth();
        int endMonth = datePickerEnd.getMonth() + 1;
        int endYear = datePickerEnd.getYear();

        Long accommodationId = accommodation.getId();
        int numOfGuests = numberPickerGuests.getValue();
        LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);
        LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);
        double totalPrice = calculateTotalAmount(startDate, endDate, numOfGuests);

        ReservationRequest reservationRequest = new ReservationRequest(1L, accommodationId, totalPrice, startDate, endDate, numOfGuests, RequestStatus.SENT);
        sendReservationRequest(reservationRequest);
    }

    private void sendReservationRequest(ReservationRequest reservationRequest) {
        new Thread(() -> {
            try {
                URL url = new URL(BuildConfig.IP_ADDR + "/api/requests" + "?requestDTO="+ reservationRequest);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                Gson gson = new Gson();
                String jsonInputString = gson.toJson(reservationRequest);

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder content = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    conn.disconnect();

                    runOnUiThread(() -> Toast.makeText(AccommodationDetailsActivityGuest.this, "Reservation Successfully Sent", Toast.LENGTH_SHORT).show());
                } else {
                    Log.e("Reservation", "POST request failed!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        sendNotification();
    }

    private void sendNotification() {
        Context context = AccommodationDetailsActivityGuest.this;
        String channelId = "my_channel_id";
        int notificationId = 1;

        Intent detailsIntent = new Intent(context, context.getClass());
        detailsIntent.putExtra("accommodation_id", accommodation.getId());

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, detailsIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle("Successfully reserved")
                .setContentText("Your accommodation is successfully reserved.")
                .addAction(R.drawable.ic_lock, "SHOW", pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AccommodationDetailsActivityGuest.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, MY_PERMISSIONS_REQUEST_NOTIFICATION);
        } else {
            notificationManager.notify(notificationId, notificationBuilder.build());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_NOTIFICATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
