package com.example.bookingapptim14.host;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.bookingapptim14.Adapters.ReservationRequestsAdapter;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.enums.ConfirmationMethod;
import com.example.bookingapptim14.models.ReservationRequest;

import java.util.ArrayList;
import java.util.List;

public class ReservationsFragmentHost extends Fragment {
/*
    private RecyclerView recyclerViewRequests;
    private ReservationRequestsAdapter requestsAdapter;
    private List<ReservationRequest> requestList;
    private ConfirmationMethod confirmationMethod;
   */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservations_host, container, false);

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

}