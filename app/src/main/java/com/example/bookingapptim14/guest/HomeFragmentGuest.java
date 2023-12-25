package com.example.bookingapptim14.guest;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.enums.AccommodationType;
import com.example.bookingapptim14.models.Accommodation;
import com.example.bookingapptim14.models.SearchAccommodation;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragmentGuest extends Fragment {

    private  LinearLayout linearLayout;
    private List<SearchAccommodation> accommodationsList;
    private TextView textViewDateRange;
    private Button btnDateRangePicker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_guest, container, false);
        ImageButton heartButton = view.findViewById(R.id.heartButton);

        accommodationsList = new ArrayList<>();

        // Dodavanje elemenata u listu
        accommodationsList.add(new SearchAccommodation(1L, "Sunny Beach House", "Enjoy the sun and sea at this delightful beach house.", AccommodationType.HOTEL, "accommodation_13", 4.6,2, 180.0,true));
        accommodationsList.add(new SearchAccommodation(2L, "Mountain View Cabin", "Breathtaking mountain views from this cozy cabin retreat.",AccommodationType.APARTMENT, "accommodation_1", 4.9,2, 150.0,true));
        accommodationsList.add(new SearchAccommodation(3L, "Lakeside Villa", "Stunning villa by the lake with serene views.", AccommodationType.VILLA, "accommodation_2", 4.8, 3, 250.0, true));
        accommodationsList.add(new SearchAccommodation(4L, "Downtown Loft", "Modern loft in the heart of the city.", AccommodationType.APARTMENT, "accommodation_3", 4.3, 2, 120.0, true));
        accommodationsList.add(new SearchAccommodation(5L, "Rustic Country House", "Escape to the countryside in this cozy house.", AccommodationType.ROOM, "accommodation_5", 4.7, 4, 200.0, true));
        accommodationsList.add(new SearchAccommodation(6L, "Seaside Bungalow", "Relaxing bungalow near the beach.", AccommodationType.STUDIO, "accommodation_4", 4.5, 2, 160.0, true));
        accommodationsList.add(new SearchAccommodation(7L, "Luxury Penthouse", "Experience luxury in this exquisite penthouse.", AccommodationType.APARTMENT, "accommodation_6", 4.9, 4, 350.0, true));
        accommodationsList.add(new SearchAccommodation(8L, "Countryside Cottage", "Charming cottage in a peaceful rural setting.", AccommodationType.ROOM, "accommodation_7", 4.6, 3, 180.0, true));
        accommodationsList.add(new SearchAccommodation(9L, "Tropical Paradise Villa", "Tropical villa surrounded by lush greenery.", AccommodationType.VILLA, "accommodation_8", 4.7, 5, 300.0, true));
        accommodationsList.add(new SearchAccommodation(10L, "Beachfront Resort", "Relaxing resort right by the beach.", AccommodationType.STUDIO, "accommodation_9", 4.8, 4, 280.0, true));
        accommodationsList.add(new SearchAccommodation(11L, "Chic Urban Studio", "Stylish studio apartment in a trendy neighborhood.", AccommodationType.APARTMENT, "accommodation_10", 4.4, 1, 90.0, true));
        accommodationsList.add(new SearchAccommodation(12L, "Mountain Retreat Chalet", "Escape to this cozy chalet nestled in the mountains.", AccommodationType.VILLA, "accommodation_11", 4.6, 3, 210.0, true));


        linearLayout = view.findViewById(R.id.linearView);


        for (SearchAccommodation accommodation : accommodationsList) {
            View cardView = getLayoutInflater().inflate(R.layout.card_vew, null);

            TextView descriptionTextView = cardView.findViewById(R.id.descriptionTextView);
            TextView ratingTextView = cardView.findViewById(R.id.ratingTextView);
            TextView priceTextView = cardView.findViewById(R.id.priceTextView);
            ImageView accommodationImageView = cardView.findViewById(R.id.imageView);


            descriptionTextView.setText(accommodation.getDescription());
            ratingTextView.setText("Rating: " + accommodation.getRating());
            priceTextView.setText("Price/Night: $" + accommodation.getPricePerNight());


            int drawableResourceId = getResources().getIdentifier(accommodation.getImage(), "drawable", requireContext().getPackageName());
            accommodationImageView.setImageResource(drawableResourceId);



                linearLayout.addView(cardView);
        }
        final boolean[] isFavorite = {false};
        heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite[0] = !isFavorite[0];

                heartButton.setSelected(isFavorite[0]);



            }
        });
        ImageView filterIcon = view.findViewById(R.id.filterIcon);

        filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFilterDialog();
            }
        });
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
        View cardView1 = getLayoutInflater().inflate(R.layout.card_vew, null);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AccommodationDetailsActivityGuest.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.filter_dialog, null);

        NumberPicker numberPicker = dialogView.findViewById(R.id.numberPickerGuests);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        textViewDateRange = dialogView.findViewById(R.id.textViewDateRange);
        btnDateRangePicker = dialogView.findViewById(R.id.btnDateRangePicker);

        // Set OnClickListener to open Date Range Picker
        btnDateRangePicker.setOnClickListener(v -> openDateRangePicker());
        builder.setView(dialogView)
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openDateRangePicker() {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select Dates");

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DAY_OF_MONTH, 1); // Default range of 1 day
        builder.setSelection(new Pair<>(startDate.getTimeInMillis(), endDate.getTimeInMillis()));

        MaterialDatePicker<Pair<Long, Long>> picker = builder.build();

        picker.addOnPositiveButtonClickListener(selection -> {
            long startMillis = selection.first;
            long endMillis = selection.second;

            Calendar startCal = Calendar.getInstance();
            startCal.setTimeInMillis(startMillis);
            Calendar endCal = Calendar.getInstance();
            endCal.setTimeInMillis(endMillis);

            String dateRange = String.format(
                    "Check-in: %s\nCheck-out: %s",
                    android.text.format.DateFormat.format("dd/MM/yyyy", startCal),
                    android.text.format.DateFormat.format("dd/MM/yyyy", endCal)
            );
            textViewDateRange.setText(dateRange);
        });

        picker.show(getChildFragmentManager(), picker.toString());
    }

    private void filterAccommodations(String query) {
        List<SearchAccommodation> filteredList = new ArrayList<>();

        for (SearchAccommodation accommodation : accommodationsList) {
            if (accommodation.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(accommodation);
            }
        }

        // Ovdje ažurirajte prikaz s novom filtriranom listom
        updateUI(filteredList);
    }

    private void updateUI(List<SearchAccommodation> filteredList) {

        linearLayout.removeAllViews();

        for (SearchAccommodation accommodation : filteredList) {
            View cardView = getLayoutInflater().inflate(R.layout.card_vew, null);

            TextView descriptionTextView = cardView.findViewById(R.id.descriptionTextView);
            TextView ratingTextView = cardView.findViewById(R.id.ratingTextView);
            TextView priceTextView = cardView.findViewById(R.id.priceTextView);
            ImageView accommodationImageView = cardView.findViewById(R.id.imageView);


            descriptionTextView.setText(accommodation.getDescription());
            ratingTextView.setText("Rating: " + accommodation.getRating());
            priceTextView.setText("Price/Night: $" + accommodation.getPricePerNight());


            int drawableResourceId = getResources().getIdentifier(accommodation.getImage(), "drawable", requireContext().getPackageName());
            accommodationImageView.setImageResource(drawableResourceId);




            linearLayout.addView(cardView);
        }
    }
}