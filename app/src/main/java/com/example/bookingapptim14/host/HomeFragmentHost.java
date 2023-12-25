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

import com.example.bookingapptim14.GlobalData;
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

        accommodationsList = GlobalData.getInstance().getSearchAccommodations();

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