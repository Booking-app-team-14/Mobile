package com.example.bookingapptim14.guest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapptim14.R;

import java.util.Calendar;

public class AccommodationDetailsActivityGuest extends Activity {

    Button bookingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomodation_details_guest);

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
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_reservation_request_guest, null);
        builder.setView(dialogView);


        DatePicker datePickerStart = dialogView.findViewById(R.id.datePickerStart);
        DatePicker datePickerEnd = dialogView.findViewById(R.id.datePickerEnd);
        NumberPicker numberPickerGuests = dialogView.findViewById(R.id.numberPickerGuests);
        Button buttonCalculate = dialogView.findViewById(R.id.buttonCalculate);
        TextView textViewRecap = dialogView.findViewById(R.id.textViewRecap);

        numberPickerGuests.setMinValue(1);
        numberPickerGuests.setMaxValue(10);
        numberPickerGuests.setWrapSelectorWheel(true);

        Calendar calendar = Calendar.getInstance();
        datePickerStart.setMinDate(calendar.getTimeInMillis());

        datePickerStart.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update check-out date when check-in date changes (+1 day)
                        Calendar checkOutCalendar = Calendar.getInstance();
                        checkOutCalendar.set(year, monthOfYear, dayOfMonth);
                        checkOutCalendar.add(Calendar.DAY_OF_MONTH, 1);
                        datePickerEnd.setMinDate(checkOutCalendar.getTimeInMillis());

                        // If the check-out date is before the check-in date, reset it to +1 day of check-in
                        if (datePickerEnd.getDayOfMonth() < dayOfMonth) {
                            datePickerEnd.updateDate(year, monthOfYear, dayOfMonth + 1);
                        }
                    }
                });
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAndDisplayRecap(datePickerStart, datePickerEnd, numberPickerGuests, textViewRecap);
            }
        });

        builder.setPositiveButton("Reserve", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void calculateAndDisplayRecap(DatePicker datePickerStart, DatePicker datePickerEnd,
                                          NumberPicker editTextGuests, TextView textViewRecap) {

        int startDay = datePickerStart.getDayOfMonth();
        int startMonth = datePickerStart.getMonth() + 1;
        int startYear = datePickerStart.getYear();

        int endDay = datePickerEnd.getDayOfMonth();
        int endMonth = datePickerEnd.getMonth() + 1;
        int endYear = datePickerEnd.getYear();

        // Get number of guests
        int numOfGuests = editTextGuests.getValue();
        String recap = "Start Date: " + startDay + "/" + startMonth + "/" + startYear +
                "\nEnd Date: " + endDay + "/" + endMonth + "/" + endYear +
                "\nNumber of Guests: " + numOfGuests +
                "\nTotal Amount: $XXX";

        textViewRecap.setText(recap);
        textViewRecap.setVisibility(View.VISIBLE);
    }
}