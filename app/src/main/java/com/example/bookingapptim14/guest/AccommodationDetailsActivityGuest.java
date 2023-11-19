package com.example.bookingapptim14.guest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bookingapptim14.R;

public class AccommodationDetailsActivityGuest extends Activity {

    Button bookingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_details_guest);

        bookingButton = findViewById(R.id.common_button);
        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_reservation_request_guest, null);
        builder.setView(dialogView);

        builder.setPositiveButton("Reserve", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(AccommodationDetailsActivityGuest.this,"Succesfully resequested",Toast.LENGTH_SHORT);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                // Cancel action
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
