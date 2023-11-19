package com.example.bookingapptim14.host;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.bookingapptim14.R;

public class AccomodationDetailsActivityHost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomodation_details_host);

        ImageButton popupButton =findViewById(R.id._bg__bi_three_dots_vertical);

        popupButton.setOnClickListener(v -> showPopupMenu(v));
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);


        popupMenu.getMenu().add("Update an accommodation");
        popupMenu.getMenu().add("Generate a report");


        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getTitle().toString()) {
                case "Update an accommodation":
                case "Generate a report":

                    return true;
                default:
                    return false;
            }
        });

        // Show the PopupMenu
        popupMenu.show();
    }
}