package com.example.bookingapptim14.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.bookingapptim14.R;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView stavka1 = findViewById(R.id.imageViewStavka1);
        ImageView stavka2 = findViewById(R.id.imageViewStavka2);
        ImageView stavka3 = findViewById(R.id.imageViewStavka3);
        ImageView stavka4 = findViewById(R.id.imageViewStavka4);

        stavka1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigacija na MainScreen
                startActivity(new Intent(HomeScreen.this, MainScreen.class));
            }
        });

        stavka2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigacija na AccountScreen
                startActivity(new Intent(HomeScreen.this, AccountScreen.class));
            }
        });

        stavka3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigacija na ReservationsScreen
                startActivity(new Intent(HomeScreen.this, AccommodationDetailsScreen.class));
            }
        });

        stavka4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigacija na NotificationsScreen
                startActivity(new Intent(HomeScreen.this, NotificationsScreen.class));
            }
        });
    }

}