package com.example.bookingapptim14.guest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.bookingapptim14.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

public class ReservationRequestActivityGuest extends AppCompatActivity {

    Button confirmButton;
    TextView dateRangeRecapTextView, guestCountRecapTextView, totalAmountRecapTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_request_guest);


        dateRangeRecapTextView = findViewById(R.id.dateRangeRecapTextView);
        guestCountRecapTextView = findViewById(R.id.totalAmountOfGuests);
        totalAmountRecapTextView = findViewById(R.id.totalAmountLabel);

        Button confirmButton = findViewById(R.id.calculateButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ReservationActivity", "Button Clicked");
                String dateRange = "Date Range: 2023-11-25 - 2023-11-29";
                String guestCount = "Number of Guests: 4";
                String totalAmount = "Total Amount: $111110";

                dateRangeRecapTextView.setText(dateRange);
                guestCountRecapTextView.setText(guestCount);
                totalAmountRecapTextView.setText(totalAmount);
            }
        });
    }


}