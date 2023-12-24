package com.example.bookingapptim14.guest;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.bookingapptim14.R;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Calendar;

public class HomeFragmentGuest extends Fragment {

    private TextView textViewDateRange;
    private Button btnDateRangePicker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_guest, container, false);
        ImageButton heartButton = view.findViewById(R.id.heartButton);

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
        CardView cardView = view.findViewById(R.id.cardView);

        cardView.setOnClickListener(new View.OnClickListener() {
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
}