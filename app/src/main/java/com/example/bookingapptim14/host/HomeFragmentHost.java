package com.example.bookingapptim14.host;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.widget.PopupMenu;

import androidx.cardview.widget.CardView;

import com.example.bookingapptim14.R;


public class HomeFragmentHost extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_host, container, false);

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

}