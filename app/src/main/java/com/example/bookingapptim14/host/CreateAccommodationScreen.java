package com.example.bookingapptim14.host;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapptim14.R;

public class CreateAccommodationScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_accommodation);

        ImageView imageView = findViewById(R.id.imageViewLogo);
        ObjectAnimator rotationAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.rotate_animation);
        rotationAnimator.setTarget(imageView);
        rotationAnimator.start();

        Spinner spinnerType = findViewById(R.id.spinnerType);

        String[] types = {"STUDIO", "ROOM", "APARTMENT", "VILLA", "HOTEL"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerType.setAdapter(adapter);
        spinnerType.setSelection(-1);



       /* Button createAccommodationButton = findViewById(R.id.buttonCreateAccommodation);

        createAccommodationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSuccessMessage();
            }
        });*/

        Button buttonNextPage = findViewById(R.id.buttonNextPage);

        // Postavite OnClickListener za dugme
        buttonNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccommodationScreen.this, AccommodationAvailabilityScreen.class);

                startActivity(intent);
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


