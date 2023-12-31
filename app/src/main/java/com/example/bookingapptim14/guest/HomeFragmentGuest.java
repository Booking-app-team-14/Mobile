package com.example.bookingapptim14.guest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.bookingapptim14.R;

public class HomeFragmentGuest extends Fragment {
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
    }

