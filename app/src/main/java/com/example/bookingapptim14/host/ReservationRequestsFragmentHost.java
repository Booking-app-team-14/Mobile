package com.example.bookingapptim14.host;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.example.bookingapptim14.R;
import com.example.bookingapptim14.enums.ConfirmationMethod;
import com.example.bookingapptim14.models.ReservationRequest;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReservationRequestsFragmentHost extends Fragment {
    /*
        private RecyclerView recyclerViewRequests;
        private ReservationRequestsAdapter requestsAdapter;
        private List<ReservationRequest> requestList;
        private ConfirmationMethod confirmationMethod;

       */
    private Button btnDateRangePicker;
    private TextView textViewDateRange;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation_requests_host, container, false);
        btnDateRangePicker = view.findViewById(R.id.buttonDateRangeReservationRequests);

        // Set OnClickListener to open Date Range Picker
        textViewDateRange = view.findViewById(R.id.textViewDateRange);
        btnDateRangePicker.setOnClickListener(v -> openDateRangePicker());
        //recyclerViewRequests = view.findViewById(R.id.recyclerViewRequests);
    /*    // Set layout manager for RecyclerView
        recyclerViewRequests.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set up the list of requests (replace this with your data)
        requestList = generateDummyRequests();

        // Set confirmation method (replace this with your actual method)
        confirmationMethod = ConfirmationMethod.MANUAL;

        // Initialize and set the adapter for RecyclerView
        requestsAdapter = new ReservationRequestsAdapter(requestList, confirmationMethod);
        recyclerViewRequests.setAdapter(requestsAdapter);
*/
        return view;
    }

    // Method to generate sample reservation requests (replace with actual data retrieval logic)
    private List<ReservationRequest> generateDummyRequests() {
        // Generate dummy requests for testing
        // Replace this with your actual logic to fetch requests
        List<ReservationRequest> dummyList = new ArrayList<>();
        // dummyList.add(new ReservationRequest("User 1"));
        //dummyList.add(new ReservationRequest("User 2"));
        // ... Add more requests as needed
        return dummyList;
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