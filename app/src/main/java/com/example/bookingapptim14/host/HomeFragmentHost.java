package com.example.bookingapptim14.host;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;

import androidx.cardview.widget.CardView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.enums.AccommodationType;
import com.example.bookingapptim14.models.Location;
import com.example.bookingapptim14.models.SearchAccommodation;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;


public class HomeFragmentHost extends Fragment {

    private LinearLayout linearLayout;
    private List<SearchAccommodation> accommodationsList;
    private TextView textViewDateRange;
    private Button btnDateRangePicker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_host, container, false);

        accommodationsList = new ArrayList<>();


        accommodationsList.add(new SearchAccommodation(1L, "Sunny Beach House", "Enjoy the sun and sea at this delightful beach house.", AccommodationType.HOTEL, new Location(1L,"Serbia","Zlatibor","Katunska 4"), "accommodation_13", 2.6,2,4, 180.0, new HashSet<>(),true));
        accommodationsList.add(new SearchAccommodation(2L, "Mountain View Cabin", "Breathtaking mountain views from this cozy cabin retreat.",AccommodationType.APARTMENT, new Location(2L,"Serbia","Novi Sad","Hilandarska 2"), "rectangle", 4.9,2 ,7,150.0,new HashSet<>(),true));
        accommodationsList.add(new SearchAccommodation(3L, "Lakeside Villa", "Stunning villa by the lake with serene views.", AccommodationType.VILLA, new Location(3L,"Serbia","Belgrade","Mirijevska 51a"), "accommodation_2", 4.0, 3, 10,250.0,new HashSet<>(), true));
        accommodationsList.add(new SearchAccommodation(4L, "Downtown Loft", "Modern loft in the heart of the city.", AccommodationType.APARTMENT,new Location(4L,"Croatia","Zagreb","Katunska 4"), "accommodation_3", 3.3, 4,6, 220.0,new HashSet<>(), true));
        accommodationsList.add(new SearchAccommodation(5L, "Rustic Country House", "Escape to the countryside in this cozy house.", AccommodationType.ROOM,new Location(5L,"USA","New York","Time Square"), "accommodation_5", 4.7, 5,8, 210.0,new HashSet<>(), true));
        accommodationsList.add(new SearchAccommodation(6L, "Seaside Bungalow", "Relaxing bungalow near the beach.", AccommodationType.STUDIO,new Location(6L,"Bulgaria","Plovdiv","Katunska 4"), "accommodation_4", 4.5, 2,10, 160.0,new HashSet<>(), true));
        accommodationsList.add(new SearchAccommodation(7L, "Luxury Penthouse", "Experience luxury in this exquisite penthouse.", AccommodationType.APARTMENT,new Location(7L,"Serbia","Zlatibor","Katunska 2"), "accommodation_6", 5.0, 4,8, 350.0,new HashSet<>(), true));
        accommodationsList.add(new SearchAccommodation(8L, "Countryside Cottage", "Charming cottage in a peaceful rural setting.", AccommodationType.ROOM, new Location(8L,"Croatia","Zagreb","Katunska 66"),"accommodation_7", 3.6, 3,5, 180.0,new HashSet<>(), true));
        accommodationsList.add(new SearchAccommodation(9L, "Tropical Paradise Villa", "Tropical villa surrounded by lush greenery.", AccommodationType.VILLA,new Location(9l,"Serbia","Zlatibor","Katunska 56789"), "accommodation_8", 2.7, 5,7, 300.0,new HashSet<>(), true));
        accommodationsList.add(new SearchAccommodation(10L, "Beachfront Resort", "Relaxing resort right by the beach.", AccommodationType.STUDIO, new Location(10l,"Norway","Oslo","Katunska 4"), "accommodation_9", 4.8, 4, 12,280.0,new HashSet<>(), true));
        accommodationsList.add(new SearchAccommodation(11L, "Chic Urban Studio", "Stylish studio apartment in a trendy neighborhood.", AccommodationType.APARTMENT,new Location(11l,"Hungary","Budapest","Katunska 4"), "accommodation_10", 3.4, 1,3, 90.0,new HashSet<>(), true));
        accommodationsList.add(new SearchAccommodation(12L, "Mountain Retreat Chalet", "Escape to this cozy chalet nestled in the mountains.", AccommodationType.VILLA,new Location(12l,"Serbia","Zlatibor","Katunska 543"), "accommodation_11", 2.6, 3,5, 210.0,new HashSet<>(), true));


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


        ImageView filterIcon = view.findViewById(R.id.filterIcon);

        filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFilterDialog();
            }
        });
        CardView cardView = view.findViewById(R.id.cardView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AccomodationDetailsActivityHost.class);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton popupButton = view.findViewById(R.id.toolbarButton);

        popupButton.setOnClickListener(v -> showPopupMenu(v));
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);


        popupMenu.getMenu().add("Create new accommodation");
        popupMenu.getMenu().add("Generate a report for all your accommodation");

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getTitle().toString()) {
                case "Create new accommodation":
                    Intent createAccommodationIntent = new Intent(requireContext(), CreateAccommodationScreen.class);
                    startActivity(createAccommodationIntent);
                    return true;
                case "Generate a report for all your accommodation":
                    return true;
                default:
                    return false;
            }
        });


        popupMenu.show();
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

}