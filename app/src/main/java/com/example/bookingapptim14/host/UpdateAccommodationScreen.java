package com.example.bookingapptim14.host;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapptim14.R;

public class UpdateAccommodationScreen extends AppCompatActivity {
    private static final int PICK_IMAGES_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_accommodation);

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

        Button selectPhotosButton = findViewById(R.id.editTextCapacity);
        selectPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Otvori galeriju slika
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });


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
                Intent intent = new Intent(UpdateAccommodationScreen.this, MainActivityHost.class);
                startActivity(intent);
            }
        });


    }

    private void showSuccessMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Successfully updated accommodation")
                .setMessage("The request for approval of accommodation changes has been sent to the admin\n"
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGES_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri imageUri = clipData.getItemAt(i).getUri();
                        displayImage(imageUri);
                    }
                } else {
                    Uri imageUri = data.getData();
                    displayImage(imageUri);

                }
            }
        }
    }

    private void displayImage(Uri imageUri) {
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageURI(imageUri);
    }
}
