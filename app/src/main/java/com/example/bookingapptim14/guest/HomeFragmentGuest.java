package com.example.bookingapptim14.guest;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapptim14.Adapters.LocalDateDeserializer;
import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.SearchAccommodation;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.slider.RangeSlider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragmentGuest extends Fragment implements SensorEventListener {

    private LinearLayout linearLayout;
    private List<SearchAccommodation> accommodationsList;
    private TextView textViewDateRange;
    private Button btnDateRangePicker;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static final int SHAKE_THRESHOLD = 800;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;

    private String jwtToken;
    private long userId;

    private Map<Long, Boolean> favoriteStatusMap = new HashMap<>();
    LocalDate startDateSelected;
    LocalDate endDateSelected;
    String startDate;
    String endDate;
    RangeSlider priceRangeSlider;
    NumberPicker numberPickerGuests;
    RatingBar ratingBar;
    CheckBox checkboxWifi, checkboxParking, checkboxSauna, checkboxGym, checkboxGames;
    CheckBox checkboxApartment, checkboxStudio, checkboxHotel, checkboxVilla, checkboxRoom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        userId = sharedPreferences.getLong("userId", 0);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_guest, container, false);

        linearLayout = view.findViewById(R.id.linearView);

        // Fetch accommodations from the API
        fetchAccommodations();

        ImageView filterIcon = view.findViewById(R.id.filterIcon);
        filterIcon.setOnClickListener(v -> showFilterDialog());

        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    updateUI(accommodationsList);
                } else {
                    filterAccommodations(newText);
                }
                return true;
            }
        });

        return view;
    }

    private void fetchAccommodations() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/accommodations-mobile/sort/price/asc");
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

                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                                .create();
                        Type listType = new TypeToken<List<SearchAccommodation>>() {}.getType();
                        accommodationsList = gson.fromJson(content.toString(), listType);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateUI(accommodationsList);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    filterAccommodationsByPriceDesc();
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void filterAccommodationsByPriceDesc() {
        if (accommodationsList == null) {
            return;
        }
        List<SearchAccommodation> sortedList = new ArrayList<>(accommodationsList);
        // GET request price/desc
        Collections.sort(sortedList, (a1, a2) -> Float.compare((float)a2.getPrice(),(float)a1.getPrice()));
        updateUI(sortedList);
    }

    public void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.filter_dialog, null);

        NumberPicker numberPicker = dialogView.findViewById(R.id.numberPickerGuests);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);
        startDate = "";
        endDate = "";
        TextView textViewDateRange = dialogView.findViewById(R.id.textViewDateRange);
        Button btnDateRangePicker = dialogView.findViewById(R.id.btnDateRangePicker);

        RangeSlider priceRangeSlider = dialogView.findViewById(R.id.priceRangeSlider);
        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);

        CheckBox checkboxWifi = dialogView.findViewById(R.id.checkbox_wifi);
        CheckBox checkboxParking = dialogView.findViewById(R.id.checkbox_parking);
        CheckBox checkboxSauna = dialogView.findViewById(R.id.checkbox_sauna);
        CheckBox checkboxGym = dialogView.findViewById(R.id.checkbox_gym);
        CheckBox checkboxGames = dialogView.findViewById(R.id.checkbox_games);

        CheckBox checkboxApartment = dialogView.findViewById(R.id.checkbox_apartment);
        CheckBox checkboxStudio = dialogView.findViewById(R.id.checkbox_studio);
        CheckBox checkboxHotel = dialogView.findViewById(R.id.checkbox_hotel);
        CheckBox checkboxVilla = dialogView.findViewById(R.id.checkbox_villa);
        CheckBox checkboxRoom = dialogView.findViewById(R.id.checkbox_room);

        btnDateRangePicker.setOnClickListener(v -> openDateRangePicker(textViewDateRange));

        builder.setView(dialogView)
                .setPositiveButton("Apply", (dialog, id) -> {
                    int numberOfGuests = numberPicker.getValue();
                    List<Float> priceRange = priceRangeSlider.getValues();
                    float minPrice = priceRange.get(0);
                    float maxPrice = priceRange.get(1);
                    float minRating = ratingBar.getRating();

                    boolean wifi = checkboxWifi.isChecked();
                    boolean parking = checkboxParking.isChecked();
                    boolean sauna = checkboxSauna.isChecked();
                    boolean gym = checkboxGym.isChecked();
                    boolean games = checkboxGames.isChecked();

                    boolean apartment = checkboxApartment.isChecked();
                    boolean studio = checkboxStudio.isChecked();
                    boolean hotel = checkboxHotel.isChecked();
                    boolean villa = checkboxVilla.isChecked();
                    boolean room = checkboxRoom.isChecked();

                    Map<String, Object> filters = new HashMap<>();
                    filters.put("numberOfGuests", numberOfGuests);
                    filters.put("minPrice", minPrice);
                    filters.put("maxPrice", maxPrice);
                    filters.put("minRating", minRating);
                    filters.put("wifi", wifi);
                    filters.put("parking", parking);
                    filters.put("sauna", sauna);
                    filters.put("gym", gym);
                    filters.put("games", games);

                    filters.put("apartment", apartment);
                    filters.put("studio", studio);
                    filters.put("hotel", hotel);
                    filters.put("villa", villa);
                    filters.put("room", room);
                    filters.put("startDate", startDateSelected);
                    filters.put("endDate", endDateSelected);
                    filterAccommodations(filters);
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openDateRangePicker(TextView textViewDateRange) {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select Dates");

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DAY_OF_MONTH, 1);
        builder.setSelection(new Pair<>(startDate.getTimeInMillis(), endDate.getTimeInMillis()));

        MaterialDatePicker<Pair<Long, Long>> picker = builder.build();
        picker.show(getParentFragmentManager(), picker.toString());

        picker.addOnPositiveButtonClickListener(selection -> {
            long startMillis = selection.first;
            long endMillis = selection.second;
            startDateSelected = Instant.ofEpochMilli(startMillis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            endDateSelected = Instant.ofEpochMilli(endMillis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            textViewDateRange.setText(startDateSelected.toString() + " - " + endDateSelected.toString());
        });
    }

    public void filterAccommodations(Map<String, Object> filters) {
        int numberOfGuests = (int) filters.get("numberOfGuests");
        float minPrice = (float) filters.get("minPrice");
        float maxPrice = (float) filters.get("maxPrice");
        float minRating = (float) filters.get("minRating");

        boolean wifi = (boolean) filters.get("wifi");
        boolean parking = (boolean) filters.get("parking");

        boolean games = (boolean) filters.get("games");
        boolean gym = (boolean) filters.get("gym");
        boolean sauna = (boolean) filters.get("sauna");

        boolean apartment = (boolean) filters.get("apartment");
        boolean studio = (boolean) filters.get("studio");
        boolean hotel = (boolean) filters.get("hotel");
        boolean villa = (boolean) filters.get("villa");
        boolean room = (boolean) filters.get("room");

        //TODO send to GET request: api/accommodations/filter with all the parameters

        List<SearchAccommodation> filteredList = new ArrayList<>();

        for (SearchAccommodation accommodation : accommodationsList) {
            boolean matchesFilters = true;

            if (accommodation.getMaxGuests() < numberOfGuests) {
                matchesFilters = false;
            }
            if (accommodation.getPrice() < minPrice || accommodation.getPrice() > maxPrice) {
                matchesFilters = false;
            }
            if (accommodation.getStars() < minRating) {
                matchesFilters = false;
            }
            /*
            if (wifi && !accommodation.get().contains(Amenity.WIFI)) {
                matchesFilters = false;
            }
            if (parking && !accommodation.getAmenities().contains(Amenity.PARKING)) {
                matchesFilters = false;
            }
            if (pool && !accommodation.getAmenities().contains(Amenity.POOL)) {
                matchesFilters = false;
            }
            if (breakfast && !accommodation.getAmenities().contains(Amenity.BREAKFAST)) {
                matchesFilters = false;
            }
            if (dinner && !accommodation.getAmenities().contains(Amenity.DINNER)) {
                matchesFilters = false;
            }
            if (sauna && !accommodation.getAmenities().contains(Amenity.SAUNA)) {
                matchesFilters = false;
            }
            if (karaokeRoom && !accommodation.getAmenities().contains(Amenity.KARAOKE_ROOM)) {
                matchesFilters = false;
            }
            if (lunch && !accommodation.getAmenities().contains(Amenity.LUNCH)) {
                matchesFilters = false;
            }
            if (spa && !accommodation.getAmenities().contains(Amenity.SPA)) {
                matchesFilters = false;
            }
            if (rent && !accommodation.getAmenities().contains(Amenity.RENT)) {
                matchesFilters = false;
            }
            if (airCondition && !accommodation.getAmenities().contains(Amenity.AIR_CONDITION)) {
                matchesFilters = false;
            }
            if (tv && !accommodation.getAmenities().contains(Amenity.TV)) {
                matchesFilters = false;
            }*/

            /*
            String type = accommodation.getType().toLowerCase();
            if (apartment && !type.equals("apartment")) {
                matchesFilters = false;
            }
            if (studio && !type.equals("studio")) {
                matchesFilters = false;
            }
            if (hotel && !type.equals("hotel")) {
                matchesFilters = false;
            }
            if (villa && !type.equals("villa")) {
                matchesFilters = false;
            }
            if (room && !type.equals("room")) {
                matchesFilters = false;
            }
            */
            if (matchesFilters) {
                filteredList.add(accommodation);
            }
        }

        updateUI(filteredList);
    }

    private void filterAccommodations(String query) {
        List<SearchAccommodation> filteredList = new ArrayList<>();

        for (SearchAccommodation accommodation : accommodationsList) {
            if (accommodation.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(accommodation);
            }
        }

        updateUI(filteredList);
    }

    private void updateUI(List<SearchAccommodation> filteredList) {
        linearLayout.removeAllViews();
        for (SearchAccommodation accommodation : filteredList) {
            View cardView = getLayoutInflater().inflate(R.layout.card_vew, null);

            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AccommodationDetailsActivityGuest.class);
                intent.putExtra("accommodation_id", accommodation.getId());  // Ensure the ID is being passed
                startActivity(intent);
            });

            TextView descriptionTextView = cardView.findViewById(R.id.descriptionTextView);
            TextView ratingTextView = cardView.findViewById(R.id.ratingTextView);
            TextView priceTextView = cardView.findViewById(R.id.priceTextView);
            ImageView accommodationImageView = cardView.findViewById(R.id.imageView);

            descriptionTextView.setText(accommodation.getName());
            ratingTextView.setText("Rating: " + accommodation.getStars());
            priceTextView.setText("Price/Night: $" + accommodation.getPrice());

           String base64Image = accommodation.getMainPictureBytes();
            if (base64Image != null && !base64Image.isEmpty()) {
                byte[] decodedString = Base64.getDecoder().decode(base64Image);
                Bitmap bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                Bitmap scaled = scaleDown(bm, 200, true);
                accommodationImageView.setImageBitmap(scaled);
            }

            ImageButton heartButton = cardView.findViewById(R.id.heartButton);
            Boolean isFavorite = favoriteStatusMap.get(accommodation.getId());
            if (isFavorite == null) {
                favoriteStatusMap.put(accommodation.getId(), false);
                heartButton.setSelected(false);
            } else {
                heartButton.setSelected(isFavorite);
            }

            heartButton.setOnClickListener(v -> {
                Boolean currentFavoriteStatus = favoriteStatusMap.get(accommodation.getId());
                boolean newFavoriteStatus = !currentFavoriteStatus;
                favoriteStatusMap.put(accommodation.getId(), newFavoriteStatus);
                heartButton.setSelected(newFavoriteStatus);
                if (newFavoriteStatus) {
                    addToFavorites(accommodation.getId());
                } else {
                    removeFromFavorites(accommodation.getId());
                }
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 16); // Add margin bottom of 16dp
            cardView.setLayoutParams(params);

            linearLayout.addView(cardView);
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
    private void addToFavorites(long accommodationId) {
        // Implement the PUT request to add to favorites
        new Thread(() -> {
            try {
                URL url = new URL(BuildConfig.IP_ADDR + "/api/users/" + userId + "/favorite-accommodations/" + accommodationId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                int responseCode = conn.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getActivity(), "Failed to add to favorites", Toast.LENGTH_SHORT).show();
                    });
                }
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void removeFromFavorites(long accommodationId) {
        // Implement the DELETE request to remove from favorites
        new Thread(() -> {
            try {
                URL url = new URL(BuildConfig.IP_ADDR + "/api/users/" + userId + "/favorite-accommodations/" + accommodationId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");
                conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                int responseCode = conn.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getActivity(), "Failed to remove from favorites", Toast.LENGTH_SHORT).show();
                    });
                }
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
