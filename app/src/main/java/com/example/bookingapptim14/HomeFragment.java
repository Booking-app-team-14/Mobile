package com.example.bookingapptim14;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find the view (e.g., Button, CardView) in your layout
        CardView cardView = view.findViewById(R.id.cardView);

        // Set OnClickListener for the view
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AccommodationDetailsScreen activity
                Intent intent = new Intent(getActivity(), AccommodationDetailsScreen.class);
                startActivity(intent);
            }
        });

        return view;
    }
}