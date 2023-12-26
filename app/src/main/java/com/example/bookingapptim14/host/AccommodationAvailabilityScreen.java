package com.example.bookingapptim14.host;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.RegisterScreen;

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
                Intent intent = new Intent(AccommodationAvailabilityScreen.this, MainActivityHost.class);
                startActivity(intent);
            }
        });


    }

    private void showSuccessMessage() {
        Toast.makeText(AccommodationAvailabilityScreen.this, "You have successfully created your accommodation! The approval request has been sent to the admin.", Toast.LENGTH_LONG).show();
    }
}



