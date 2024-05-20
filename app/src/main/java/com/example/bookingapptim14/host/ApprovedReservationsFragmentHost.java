package com.example.bookingapptim14.host;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapptim14.Adapters.AccommodationApprovalAdapter;
import com.example.bookingapptim14.R;

import java.util.ArrayList;

public class ApprovedReservationsFragmentHost extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_approved_reservations_host, container, false);

//        accommodationRequestsRecyclerView = view.findViewById(R.id.accommodationRequestsRecyclerView);
//        adapter = new AccommodationApprovalAdapter(new ArrayList<>(), this);
//        accommodationRequestsRecyclerView.setAdapter(adapter);
//        accommodationRequestsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        fetchAccommodationRequests();

        return view;
    }

}