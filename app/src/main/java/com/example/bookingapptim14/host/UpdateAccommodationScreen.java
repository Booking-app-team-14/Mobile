package com.example.bookingapptim14.host;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.bookingapptim14.BuildConfig;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.Adapters.AmenityAdapter;
import com.example.bookingapptim14.Adapters.ImageAdapter;
import com.example.bookingapptim14.CustomScrollView;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.AccommodationUpdateDTO;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.AmenityDTO;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.CheckableAmenity;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.LocationDTO;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.UpdateAvailabilityDTO;
import com.example.bookingapptim14.models.dtos.Image;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewReportsData;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationData;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ReservationRequestDTO;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class UpdateAccommodationScreen extends AppCompatActivity {

    MapView map = null;
    boolean automaticHandling;
    boolean perGuest;
    ArrayList<Bitmap> imageBitmaps = new ArrayList<>();
    private static final int PICK_IMAGES = 1;
    ImageAdapter imageAdapter;
    private String jwtToken;
    private long userId;
    private AmenityAdapter amenityAdapter;
    private Long accommodationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        setContentView(R.layout.activity_update_accommodation);

        accommodationId = getIntent().getLongExtra("accommodationId", -1);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        userId = sharedPreferences.getLong("userId", 0);

        ImageButton buttonBack = (ImageButton) findViewById(R.id.updateAccommodationBackButton);
        buttonBack.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) {
            getOnBackPressedDispatcher().onBackPressed();
        }});

        Context context = this;
        // GET api/amenities -> AmenityDTOs
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/amenities");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuilder content = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                        in.close();
                        conn.disconnect();

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<AmenityDTO>>(){}.getType();
                        List<AmenityDTO> amenities = gson.fromJson(content.toString(), listType);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                List<CheckableAmenity> checkableAmenities = new ArrayList<>();
                                for (AmenityDTO amenity : amenities) {
                                    checkableAmenities.add(new CheckableAmenity(amenity));
                                }

                                RecyclerView recyclerView = findViewById(R.id.updateAccommodationRecyclerView);
                                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                                amenityAdapter = new AmenityAdapter(checkableAmenities);
                                recyclerView.setAdapter(amenityAdapter);
                            }
                        });
                    } else {
                        System.out.println("GET request failed!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // GET api/accommodations/update/{accommodationId} -> AccommodationUpdateDTO
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/accommodations/update/" + accommodationId);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuilder content = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                        in.close();
                        conn.disconnect();

                        Gson gson = new Gson();
                        AccommodationUpdateDTO accommodation = gson.fromJson(content.toString(), AccommodationUpdateDTO.class);

                        // TODO: [VUK] popravi error
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "ovde puca pre", Toast.LENGTH_SHORT).show();
                            }
                        });

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                for (Image image : accommodation.getImages()) {
                                    byte[] imageBytes = Base64.decode(image.getImageBytes(), Base64.DEFAULT);
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                                    imageBitmaps.add(bitmap);
                                }
                                imageAdapter.notifyDataSetChanged();

                                EditText editTextName = findViewById(R.id.updateAccommodationEditTextName);
                                editTextName.setText(accommodation.getName());

                                EditText editTextDescription = findViewById(R.id.updateAccommodationEditTextDescription);
                                editTextDescription.setText(accommodation.getDescription());

                                EditText editTextMinGuests = findViewById(R.id.updateAccommodationEditTextMinGuests);
                                editTextMinGuests.setText(String.valueOf(accommodation.getMinNumberOfGuests()));

                                EditText editTextMaxGuests = findViewById(R.id.updateAccommodationEditTextMaxGuests);
                                editTextMaxGuests.setText(String.valueOf(accommodation.getMaxNumberOfGuests()));

                                Spinner spinnerAccommodationType = findViewById(R.id.updateAccommodationSpinnerType);

                                String[] types = {"STUDIO", "ROOM", "APARTMENT", "VILLA", "HOTEL"};

                                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, types);
                                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerAccommodationType.setAdapter(spinnerAdapter);
                                if (accommodation.getType().equals("STUDIO")) {
                                    spinnerAccommodationType.setSelection(0);
                                } else if (accommodation.getType().equals("ROOM")) {
                                    spinnerAccommodationType.setSelection(1);
                                } else if (accommodation.getType().equals("APARTMENT")) {
                                    spinnerAccommodationType.setSelection(2);
                                } else if (accommodation.getType().equals("VILLA")) {
                                    spinnerAccommodationType.setSelection(3);
                                } else if (accommodation.getType().equals("HOTEL")) {
                                    spinnerAccommodationType.setSelection(4);
                                }

                                map = (MapView) findViewById(R.id.updateAccommodationMap);
                                map.setMultiTouchControls(true);
                                map.getController().setZoom(15.0);

                                locateOnMap(accommodation.getLocation().getAddress() + ", " + accommodation.getLocation().getCity() + ", " + accommodation.getLocation().getCountry());

                                EditText editTextAddress = findViewById(R.id.updateAccommodationEditTextAddress);
                                editTextAddress.setText(accommodation.getLocation().getAddress() + ", " + accommodation.getLocation().getCity() + ", " + accommodation.getLocation().getCountry());

                                RadioGroup radioGroupConfirmation = findViewById(R.id.updateAccommodationConfirmationRadioGroup);
                                if (accommodation.isAutomaticHandling()) {
                                    radioGroupConfirmation.check(R.id.updateAccommodationRadioButtonAutomatic);
                                    automaticHandling = true;
                                } else {
                                    radioGroupConfirmation.check(R.id.updateAccommodationRadioButtonManual);
                                    automaticHandling = false;
                                }

                                radioGroupConfirmation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                        if (checkedId == R.id.updateAccommodationRadioButtonAutomatic) {
                                            automaticHandling = true;
                                        } else if (checkedId == R.id.updateAccommodationRadioButtonManual) {
                                            automaticHandling = false;
                                        }
                                    }
                                });

                                RadioGroup radioGroupPrice = findViewById(R.id.updateAccommodationDefinePriceRadioGroup);
                                if (accommodation.isPricePerGuest()) {
                                    radioGroupPrice.check(R.id.updateAccommodationRadioButtonPerGuest);
                                    perGuest = true;
                                } else {
                                    radioGroupPrice.check(R.id.updateAccommodationRadioButtonPerNight);
                                    perGuest = false;
                                }

                                radioGroupPrice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                        if (checkedId == R.id.updateAccommodationRadioButtonPerNight) {
                                            perGuest = false;
                                        } else if (checkedId == R.id.updateAccommodationRadioButtonPerGuest) {
                                            perGuest = true;
                                        }
                                    }
                                });

                                EditText editTextDefaultPrice = findViewById(R.id.updateAccommodationEditTextPrice);
                                String formattedPrice = String.format(Locale.getDefault(), "%.2f", accommodation.getDefaultPrice());
                                editTextDefaultPrice.setText(formattedPrice);

                                EditText editTextCancellationDeadline = findViewById(R.id.updateAccommodationEditTextCancellationDeadline);
                                editTextCancellationDeadline.setText(String.valueOf(accommodation.getCancellationDeadline()));

                                Button addAvailabilityButton = findViewById(R.id.updateAccommodationAddAvailabilityAndSpecialPriceButton);
                                addAvailabilityButton.setOnClickListener(v -> addAvailabilityAndSpecialPriceView(null));

                                for (UpdateAvailabilityDTO availabilityAndSpecialPrice : accommodation.getAvailability()) {
                                    addAvailabilityAndSpecialPriceView(availabilityAndSpecialPrice);
                                }

                                for (Long amenityId : accommodation.getAmenities()){
                                    amenityAdapter.checkAmenity(amenityId);
                                }

                                Button updateAccommodationButton = findViewById(R.id.updateAccommodationButtonUpdateDetails);

                                updateAccommodationButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        updateAccommodationInformation();
                                    }
                                });

                                CustomScrollView scrollView = findViewById(R.id.updateAccommodationScrollView);
                                map.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        switch (event.getAction()) {
                                            case MotionEvent.ACTION_DOWN:
                                                scrollView.setScrollingEnabled(false);
                                                return false;

                                            case MotionEvent.ACTION_UP:
                                                scrollView.setScrollingEnabled(true);
                                                return true;

                                            case MotionEvent.ACTION_MOVE:
                                                scrollView.setScrollingEnabled(false);
                                                return false;

                                            default:
                                                return true;
                                        }
                                    }
                                });

                                findViewById(R.id.updateAccommodationScrollView).setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        View currentFocus = getCurrentFocus();
                                        if (currentFocus instanceof EditText) {
                                            currentFocus.clearFocus();
                                        }
                                        return false;
                                    }
                                });

                                EditText editTextReason = findViewById(R.id.updateAccommodationEditTextReason);

                                editTextDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                    @Override
                                    public void onFocusChange(View v, boolean hasFocus) {
                                        if (hasFocus) {
                                            scrollView.setScrollingEnabled(false);
                                        } else {
                                            scrollView.setScrollingEnabled(true);
                                            // Hide keyboard
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                        }
                                    }
                                });

                                editTextReason.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                    @Override
                                    public void onFocusChange(View v, boolean hasFocus) {
                                        if (hasFocus) {
                                            scrollView.setScrollingEnabled(false);
                                        } else {
                                            scrollView.setScrollingEnabled(true);
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                        }
                                    }
                                });

                                Button buttonLocateOnMap = findViewById(R.id.updateAccommodationButtonLocateOnMap);
                                buttonLocateOnMap.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        EditText editTextAddress = findViewById(R.id.updateAccommodationEditTextAddress);
                                        String address = editTextAddress.getText().toString();
                                        if (address.isEmpty()) {
                                            Toast.makeText(UpdateAccommodationScreen.this, "Please enter an address.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            locateOnMap(address);
                                        }
                                    }
                                });

                            }
                        });
                    } else {
                        System.out.println("GET request failed!");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Failed to load accommodation data.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Failed. Catch", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();

        Button openGalleryButton = findViewById(R.id.updateAccommodationOpenGalleryButton);
        openGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGES);
            }
        });

        RecyclerView accommodationImageRecyclerView = findViewById(R.id.updateAccommodationImageRecyclerView);
        accommodationImageRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imageAdapter = new ImageAdapter(imageBitmaps, this);
        accommodationImageRecyclerView.setAdapter(imageAdapter);
    }

    private void updateAccommodationInformation() {
        AccommodationUpdateDTO accommodationUpdateDTO = new AccommodationUpdateDTO();

        accommodationUpdateDTO.setId(accommodationId);

        EditText editTextName = findViewById(R.id.updateAccommodationEditTextName);
        String accommodationNameString = editTextName.getText().toString();
        if (accommodationNameString.isEmpty()) {
            Toast.makeText(this, "Please enter the accommodation name.", Toast.LENGTH_SHORT).show();
            return;
        }
        accommodationUpdateDTO.setName(accommodationNameString);

        EditText editTextDescription = findViewById(R.id.updateAccommodationEditTextDescription);
        String accommodationDescriptionString = editTextDescription.getText().toString();
        if (accommodationDescriptionString.isEmpty()) {
            Toast.makeText(this, "Please enter the accommodation description.", Toast.LENGTH_SHORT).show();
            return;
        }
        accommodationUpdateDTO.setDescription(accommodationDescriptionString);

        Spinner spinnerAccommodationType = findViewById(R.id.updateAccommodationSpinnerType);
        String accommodationTypeString = spinnerAccommodationType.getSelectedItem().toString();
        accommodationUpdateDTO.setType(accommodationTypeString);

        EditText editTextMinGuests = findViewById(R.id.updateAccommodationEditTextMinGuests);
        String minGuestsString = editTextMinGuests.getText().toString();
        EditText editTextMaxGuests = findViewById(R.id.updateAccommodationEditTextMaxGuests);
        String maxGuestsString = editTextMaxGuests.getText().toString();
        if (minGuestsString.isEmpty() || maxGuestsString.isEmpty()) {
            Toast.makeText(this, "Please enter the minimum and maximum number of guests.", Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            int minGuests = Integer.parseInt(minGuestsString);
            int maxGuests = Integer.parseInt(maxGuestsString);
            if (minGuests < 1 || maxGuests < 1){
                Toast.makeText(this, "The minimum and maximum number of guests must be greater than 0.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (minGuests > maxGuests) {
                Toast.makeText(this, "The minimum number of guests cannot be greater than the maximum number of guests.", Toast.LENGTH_SHORT).show();
                return;
            }
            accommodationUpdateDTO.setMinNumberOfGuests(minGuests);
            accommodationUpdateDTO.setMaxNumberOfGuests(maxGuests);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers for the minimum and maximum number of guests.", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText editTextAddress = findViewById(R.id.updateAccommodationEditTextAddress);
        String enteredAddress = editTextAddress.getText().toString();
        LocationDTO location = parseAddress(enteredAddress);
        if (location == null) {
            Toast.makeText(this, "Please enter the address in the format: address, city, country", Toast.LENGTH_SHORT).show();
            return;
        }
        accommodationUpdateDTO.setLocation(location);

        accommodationUpdateDTO.setAutomaticHandling(automaticHandling);
        accommodationUpdateDTO.setPricePerGuest(perGuest);

        EditText editTextDefaultPrice = findViewById(R.id.updateAccommodationEditTextPrice);
        String defaultPriceString = editTextDefaultPrice.getText().toString();
        try {
            double defaultPrice = Double.parseDouble(defaultPriceString);
            if (defaultPrice < 1) {
                Toast.makeText(this, "The default price must be greater than 0.", Toast.LENGTH_SHORT).show();
                return;
            }
            accommodationUpdateDTO.setDefaultPrice(defaultPrice);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number for the default price.", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText editTextCancellationDeadline = findViewById(R.id.updateAccommodationEditTextCancellationDeadline);
        String cancellationDeadlineString = editTextCancellationDeadline.getText().toString();
        try {
            int cancellationDeadline = Integer.parseInt(cancellationDeadlineString);
            if (cancellationDeadline < 0) {
                Toast.makeText(this, "The cancellation deadline must be 0 or more.", Toast.LENGTH_SHORT).show();
                return;
            }
            accommodationUpdateDTO.setCancellationDeadline(cancellationDeadline);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number for the cancellation deadline.", Toast.LENGTH_SHORT).show();
            return;
        }

        Set<UpdateAvailabilityDTO> availability = getAvailabilitiesAndSpecialPrices();
        if (availability == null) {
            return;
        }
        accommodationUpdateDTO.setAvailability(availability);

        EditText updateAccommodationEditTextReason = findViewById(R.id.updateAccommodationEditTextReason);
        if (updateAccommodationEditTextReason.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter a reason for the changes.", Toast.LENGTH_SHORT).show();
            return;
        }
        accommodationUpdateDTO.setMessage(updateAccommodationEditTextReason.getText().toString());

        RecyclerView recyclerView = findViewById(R.id.updateAccommodationRecyclerView);
        AmenityAdapter amenityAdapter = (AmenityAdapter) recyclerView.getAdapter();
        Set<Long> amenityIds = amenityAdapter.getCheckedAmenitiesIds();
        accommodationUpdateDTO.setAmenities(amenityIds);

        Set<Image> images = new HashSet<>();
        for (Bitmap bitmap : imageAdapter.getImages()) {
            String imageBytes = getBase64StringFromBitmap(bitmap);
            String imageType = getImageType(imageBytes);
            images.add(new Image(imageBytes, imageType));
        }
        accommodationUpdateDTO.setImages(images);

        // PUT api/accommodations/update, consumes AccommodationUpdateDTO
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();
                    String json = gson.toJson(accommodationUpdateDTO);

                    URL url = new URL(BuildConfig.IP_ADDR + "/api/accommodations/update");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json; utf-8");
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);
                    conn.setDoOutput(true);

                    conn.getOutputStream().write(json.getBytes());

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UpdateAccommodationScreen.this, "The request for approval of accommodation changes has been sent to the admin.", Toast.LENGTH_SHORT).show();
                                getOnBackPressedDispatcher().onBackPressed();
                            }
                        });
                    } else {
                        System.out.println("PUT request failed!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGES) {
            if (resultCode == RESULT_OK) {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        Bitmap bitmap;
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            bitmap = BitmapFactory.decodeStream(inputStream);
                            String imageType = getImageType(getBase64StringFromBitmap(bitmap));
                            if (imageType.equals("jpg") || imageType.equals("png") || imageType.equals("gif")) {
                                imageBitmaps.add(bitmap);
                            } else {
                                Toast.makeText(this, "Invalid image type. Only jpg, png, and gif are allowed.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    imageAdapter.notifyDataSetChanged();
                } else if (data.getData() != null) {
                    Uri uri = data.getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        String imageType = getImageType(getBase64StringFromBitmap(bitmap));
                        if (imageType.equals("jpg") || imageType.equals("png") || imageType.equals("gif")) {
                            imageBitmaps.add(bitmap);
                        } else {
                            Toast.makeText(this, "Invalid image type. Only jpg, png, and gif are allowed.", Toast.LENGTH_SHORT).show();
                        }
                        imageAdapter.notifyDataSetChanged();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void showDatePickerDialog(View v) {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            String selectedDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                    .format(new Date(selection));
            ((EditText) v).setText(selectedDate);
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    public void addAvailabilityAndSpecialPriceView(UpdateAvailabilityDTO availabilityAndSpecialPrice) {
        // Get the LinearLayout
        LinearLayout container = findViewById(R.id.updateAccommodationAvailabilitiesAndSpecialPricesContainer);

        // Inflate the special_price_view.xml file
        LayoutInflater inflater = LayoutInflater.from(this);
        View availabilityAndSpecialPriceView = inflater.inflate(R.layout.availability_and_special_price_view, container, false);

        if (availabilityAndSpecialPrice != null) {
            // Set the data in the view
            TextInputEditText startDateEditText = availabilityAndSpecialPriceView.findViewById(R.id.specialPriceStartDate);
            String startDateString = availabilityAndSpecialPrice.getStartDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            startDateEditText.setText(startDateString);

            TextInputEditText endDateEditText = availabilityAndSpecialPriceView.findViewById(R.id.specialPriceEndDate);
            String endDateString = availabilityAndSpecialPrice.getEndDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            endDateEditText.setText(endDateString);

            EditText amountEditText = availabilityAndSpecialPriceView.findViewById(R.id.specialPriceAmount);
            if (availabilityAndSpecialPrice.getSpecialPrice() != null) {
                String formattedPrice = String.format(Locale.getDefault(), "%.2f", availabilityAndSpecialPrice.getSpecialPrice());
                amountEditText.setText(formattedPrice);
            }
        }

        // Set an OnClickListener on the remove button
        Button removeButton = availabilityAndSpecialPriceView.findViewById(R.id.removeAvailabilityAndSpecialPriceButton);
        removeButton.setOnClickListener(v -> container.removeView(availabilityAndSpecialPriceView));

        // Add the special price view to the LinearLayout
        container.addView(availabilityAndSpecialPriceView);
    }

    public Set<UpdateAvailabilityDTO> getAvailabilitiesAndSpecialPrices() {
        // Get the LinearLayout
        LinearLayout container = findViewById(R.id.updateAccommodationAvailabilitiesAndSpecialPricesContainer);

        Set<UpdateAvailabilityDTO> availabilitiesAndSpecialPrices = new HashSet<>();

        // Iterate over the child views of the LinearLayout
        for (int i = 0; i < container.getChildCount(); i++) {
            View specialPriceView = container.getChildAt(i);

            // Get the data from the special price view
            TextInputEditText startDateEditText = specialPriceView.findViewById(R.id.specialPriceStartDate);
            String startDateString = "";
            if (startDateEditText.getText() != null) {
                startDateString = startDateEditText.getText().toString();
            }

            String endDateString = "";
            TextInputEditText endDateEditText = specialPriceView.findViewById(R.id.specialPriceEndDate);
            if (endDateEditText.getText() != null) {
                endDateString = endDateEditText.getText().toString();
            }

            if (startDateString.isEmpty() || endDateString.isEmpty()) {
                Toast.makeText(this, "Please enter the start and end dates for the availability.", Toast.LENGTH_SHORT).show();
                return null;
            }

            // Parse the dates from the strings
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate startDate = LocalDate.parse(startDateString, formatter);
            LocalDate endDate = LocalDate.parse(endDateString, formatter);

            if (startDate.isAfter(endDate)) {
                Toast.makeText(this, "The start date cannot be after the end date for the availability.", Toast.LENGTH_SHORT).show();
                return null;
            }

            EditText amountEditText = specialPriceView.findViewById(R.id.specialPriceAmount);
            Double amount = null;
            if (!amountEditText.getText().toString().isEmpty()) {
                amount = Double.parseDouble(amountEditText.getText().toString());
                if (amount < 1) {
                    Toast.makeText(this, "The special price must be greater than 0.", Toast.LENGTH_SHORT).show();
                    return null;
                }
            } else {
                amount = Double.parseDouble(findViewById(R.id.updateAccommodationEditTextPrice).toString());
            }

            availabilitiesAndSpecialPrices.add(new UpdateAvailabilityDTO(startDate, endDate, amount));
        }

        return availabilitiesAndSpecialPrices;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (map != null) {
            map.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (map != null) {
            map.onPause();
        }
    }

    private void locateOnMap(String address) {
        new Thread(() -> {
            try {
                String apiKey = "7234404387b94ae4bdc2c7d6bb31bf58";
                String geocodingUrl = "https://api.opencagedata.com/geocode/v1/json?q=" + URLEncoder.encode(address, "UTF-8") + "&key=" + apiKey;

                URL url = new URL(geocodingUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray results = jsonObject.getJSONArray("results");
                    if (results.length() > 0) {
                        JSONObject location = results.getJSONObject(0).getJSONObject("geometry");
                        double lat = location.getDouble("lat");
                        double lng = location.getDouble("lng");

                        runOnUiThread(() -> {
                            GeoPoint geoPoint = new GeoPoint(lat, lng);
                            map.getController().setCenter(geoPoint);

                            for (int i = 0; i < map.getOverlays().size(); i++) {
                                // If the overlay is an instance of Marker, remove it
                                if (map.getOverlays().get(i) instanceof Marker) {
                                    map.getOverlays().remove(i);
                                    // Decrement i to account for the removed overlay
                                    i--;
                                }
                            }

                            Marker startMarker = new Marker(map);
                            startMarker.setPosition(geoPoint);
                            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                            startMarker.setTitle(address);
                            map.getOverlays().add(startMarker);
                        });
                    } else {
                        Log.e("Geocoding", "No results found.");
                    }
                } else {
                    Log.e("Geocoding", "HTTP error code: " + responseCode);
                }
            } catch (Exception e) {
                Log.e("Geocoding", "Error during geocoding", e);
            }
        }).start();
    }

    public LocationDTO parseAddress(String enteredAddress) {
        String[] parts = enteredAddress.split(",");
        if (parts.length != 3) {
            // The entered address does not follow the rules
            return null;
        }

        String country = parts[2].trim();
        String city = parts[1].trim();
        String address = parts[0].trim();

        return new LocationDTO(country, city, address);
    }

    private String getBase64StringFromBitmap(Bitmap bitmap) {
        if (bitmap == null || (bitmap.getWidth() == 0 && bitmap.getHeight() == 0)) {
            return "";
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public String getImageType(String base64Image) {
        byte[] imageBytes = Base64.decode(base64Image, Base64.DEFAULT);

        if (imageBytes == null || imageBytes.length < 8) {
            return "unknown";
        }

        // Check the bytes to determine the image type
        if (imageBytes[0] == (byte) 0xFF && imageBytes[1] == (byte) 0xD8) {
            return "jpg";
        } else if (imageBytes[0] == (byte) 0x89 && imageBytes[1] == 'P' && imageBytes[2] == 'N' && imageBytes[3] == 'G') {
            return "png";
        } else if (imageBytes[0] == 'G' && imageBytes[1] == 'I' && imageBytes[2] == 'F') {
            return "gif";
        } else {
            return "unknown";
        }
    }

}
