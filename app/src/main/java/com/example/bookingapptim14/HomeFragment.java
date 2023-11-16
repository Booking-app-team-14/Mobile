package com.example.bookingapptim14;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

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
/*
    public void showFilterDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Filter Options");

        // Inflate the layout for the dialog
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_filter_options, null);
        builder.setView(dialogView);

        // Set up checkboxes and their functionality
        CheckBox checkbox1 = dialogView.findViewById(R.id.checkbox1);
        CheckBox checkbox2 = dialogView.findViewById(R.id.checkbox2);
        // Add more checkboxes as needed

        // Set up your dialog buttons (e.g., Apply, Cancel)

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
*/

}