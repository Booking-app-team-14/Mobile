package com.example.bookingapptim14.guest;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
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
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.bookingapptim14.Adapters.AvailabilityAdapter;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.enums.RequestStatus;
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

public class AccommodationDetailsActivityGuest extends Activity {
    private static final int MY_PERMISSIONS_REQUEST_NOTIFICATION = 1001;
    private Button bookingButton;
    private MapView mapView;
    private SearchAccommodation accommodation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomodation_details_guest);

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

            bookingButton = findViewById(R.id.common_button);
            bookingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                }
            });
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

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_reservation_request_guest, null);
        builder.setView(dialogView);

        ListView listViewAvailabilities = dialogView.findViewById(R.id.listViewAvailabilities); // Corrected line
        AvailabilityAdapter adapter = new AvailabilityAdapter(this, GlobalData.getInstance().getAvailabilities());
        listViewAvailabilities.setAdapter(adapter);
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listViewAvailabilities);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        // Set the height of the ListView dynamically
        ViewGroup.LayoutParams params = listViewAvailabilities.getLayoutParams();
        params.height = totalHeight + (listViewAvailabilities.getDividerHeight() * (adapter.getCount() - 1));
        listViewAvailabilities.setLayoutParams(params);

        DatePicker datePickerStart = dialogView.findViewById(R.id.datePickerStart);
        DatePicker datePickerEnd = dialogView.findViewById(R.id.datePickerEnd);
        NumberPicker numberPickerGuests = dialogView.findViewById(R.id.numberPickerGuests);
        Button buttonCalculate = dialogView.findViewById(R.id.buttonCalculate);
        TextView textViewRecap = dialogView.findViewById(R.id.textViewRecap);

        numberPickerGuests.setMinValue(1);
        numberPickerGuests.setMaxValue(10);
        numberPickerGuests.setWrapSelectorWheel(true);

        Calendar calendar = Calendar.getInstance();
        datePickerStart.setMinDate(calendar.getTimeInMillis());

        datePickerStart.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar checkOutCalendar = Calendar.getInstance();
                        checkOutCalendar.set(year, monthOfYear, dayOfMonth);
                        checkOutCalendar.add(Calendar.DAY_OF_MONTH, 1);
                        datePickerEnd.setMinDate(checkOutCalendar.getTimeInMillis());

                        if (datePickerEnd.getDayOfMonth() < dayOfMonth) {
                            datePickerEnd.updateDate(year, monthOfYear, dayOfMonth + 1);
                        }
                    }
                });

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                } else {
                    Toast.makeText(AccommodationDetailsActivityGuest.this, "Selected dates are not available.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setPositiveButton("Reserve", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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

                Toast.makeText(AccommodationDetailsActivityGuest.this, "Reservation Successfully Sent", Toast.LENGTH_SHORT).show();

                Context context = AccommodationDetailsActivityGuest.this;
                String channelId = "my_channel_id";

                int notificationId = 1;
                Intent detailsIntent = new Intent(context, context.getClass());
                detailsIntent.putExtra("accommodation_id", 2);

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
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean isDateRangeAvailable(LocalDate startDate, LocalDate endDate) {
        for (Availability availability : GlobalData.getInstance().getAvailabilities()) {
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
            for (Availability availability :  GlobalData.getInstance().getAvailabilities()) {
                if (!currentDate.isBefore(availability.getStartDate()) && !currentDate.isAfter(availability.getEndDate())) {
                    totalAmount += availability.getSpecialPrice() * numOfGuests;
                    break;
                }
            }
            currentDate = currentDate.plusDays(1);
        }

        return totalAmount;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_NOTIFICATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

