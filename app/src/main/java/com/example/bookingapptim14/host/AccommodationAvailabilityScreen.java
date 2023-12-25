package com.example.bookingapptim14.host;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapptim14.R;

public class AccommodationAvailabilityScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_availability);

        ViewStub viewStub = findViewById(R.id.viewStub);
        //View inflatedView = viewStub.inflate();


       /* DatePicker datePickerFrom = inflatedView.findViewById(R.id.datePickerFrom);
        DatePicker datePickerTo = inflatedView.findViewById(R.id.datePickerTo);
        TextView textViewPriceLabel = inflatedView.findViewById(R.id.textViewPriceLabel);
        EditText editTextPrice = inflatedView.findViewById(R.id.editTextPrice);
        Button buttonCreateAccommodation = inflatedView.findViewById(R.id.buttonCreateAccommodation);*/

        Button plusButton = findViewById(R.id.plusButton);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewStub.setVisibility(View.VISIBLE);
            }
        });




        Button createAccommodationButton = findViewById(R.id.buttonCreateAccommodation);

        createAccommodationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSuccessMessage();
            }
        });


    }

    private void showSuccessMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Successfully created accommodation")
                .setMessage("You have successfully created your accommodation! The approval request has been sent to the admin."
                )
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //na ok da resetuje podatke??
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}



