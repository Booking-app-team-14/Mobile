package com.example.bookingapptim14.guest;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.Amenity;
import com.example.bookingapptim14.models.SearchAccommodation;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.slider.RangeSlider;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HomeFragmentGuest extends Fragment {

    private LinearLayout linearLayout;
    private List<SearchAccommodation> accommodationsList;
    private TextView textViewDateRange;
    private Button btnDateRangePicker;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_guest, container, false);
        ImageButton heartButton = view.findViewById(R.id.heartButton);

        accommodationsList = GlobalData.getInstance().getSearchAccommodations();

        linearLayout = view.findViewById(R.id.linearView);


        for (SearchAccommodation accommodation : accommodationsList) {
            View cardView = getLayoutInflater().inflate(R.layout.card_vew, null);

            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AccommodationDetailsActivityGuest.class);
                intent.putExtra("accommodation", accommodation);

                startActivity(intent);
            });

            TextView descriptionTextView = cardView.findViewById(R.id.descriptionTextView);
            TextView ratingTextView = cardView.findViewById(R.id.ratingTextView);
            TextView priceTextView = cardView.findViewById(R.id.priceTextView);
            ImageView accommodationImageView = cardView.findViewById(R.id.imageView);

            descriptionTextView.setText(accommodation.getName());
            ratingTextView.setText("Rating: " + accommodation.getRating());
            priceTextView.setText("Price/Night: $" + accommodation.getPricePerNight());

            int drawableResourceId = getResources().getIdentifier(accommodation.getImage(), "drawable", requireContext().getPackageName());
            accommodationImageView.setImageResource(drawableResourceId);

            final boolean[] isFavorite = {false};
            heartButton.setOnClickListener(v -> {
                isFavorite[0] = !isFavorite[0];
                heartButton.setSelected(isFavorite[0]);
            });

            linearLayout.addView(cardView);
        }

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

        View cardView1 = view.findViewById(R.id.cardView);
        cardView1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AccommodationDetailsActivityGuest.class);
            startActivity(intent);
        });

        return view;
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
    public void collectValues() {

        List<Float> priceRange = priceRangeSlider.getValues();
        double minPrice = priceRange.get(0);
        double maxPrice = priceRange.get(1);


        double minRating = ratingBar.getRating();


        int minGuests = numberPickerGuests.getValue();


        Set<Long> amenityIds = new HashSet<>();
        if (checkboxWifi.isChecked()) amenityIds.add(2L);
        if (checkboxParking.isChecked()) amenityIds.add(1L);
        if (checkboxSauna.isChecked()) amenityIds.add(3L);
        if (checkboxGym.isChecked()) amenityIds.add(4L);
        if (checkboxGames.isChecked()) amenityIds.add(5L);


        Set<String> accommodationTypes = new HashSet<>();
        if (checkboxApartment.isChecked()) accommodationTypes.add("APARTMENT");
        if (checkboxStudio.isChecked()) accommodationTypes.add("STUDIO");
        if (checkboxHotel.isChecked()) accommodationTypes.add("HOTEL");
        if (checkboxVilla.isChecked()) accommodationTypes.add("VILLA");
        if (checkboxRoom.isChecked()) accommodationTypes.add("ROOM");

        if (startDateSelected != null)
            startDate = startDateSelected.toString();
        if (endDateSelected != null)
            endDate = endDateSelected.toString();

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

        List<SearchAccommodation> filteredList = new ArrayList<>();

        for (SearchAccommodation accommodation : accommodationsList) {
            boolean matchesFilters = true;

            if (accommodation.getMinNumberOfGuests() < numberOfGuests) {
                matchesFilters = false;
            }
            if (accommodation.getPricePerNight() < minPrice || accommodation.getPricePerNight() > maxPrice) {
                matchesFilters = false;
            }
            if (accommodation.getRating() < minRating) {
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
                intent.putExtra("accommodation", accommodation);
                startActivity(intent);
            });

            TextView descriptionTextView = cardView.findViewById(R.id.descriptionTextView);
            TextView ratingTextView = cardView.findViewById(R.id.ratingTextView);
            TextView priceTextView = cardView.findViewById(R.id.priceTextView);
            ImageView accommodationImageView = cardView.findViewById(R.id.imageView);

            descriptionTextView.setText(accommodation.getName());
            ratingTextView.setText("Rating: " + accommodation.getRating());
            priceTextView.setText("Price/Night: $" + accommodation.getPricePerNight());

            int drawableResourceId = getResources().getIdentifier(accommodation.getImage(), "drawable", requireContext().getPackageName());
            accommodationImageView.setImageResource(drawableResourceId);

            ImageButton heartButton = cardView.findViewById(R.id.heartButton);
            final boolean[] isFavorite = {false};
            heartButton.setOnClickListener(v -> {
                isFavorite[0] = !isFavorite[0];
                heartButton.setSelected(isFavorite[0]);
            });

            linearLayout.addView(cardView);
        }
    }
}