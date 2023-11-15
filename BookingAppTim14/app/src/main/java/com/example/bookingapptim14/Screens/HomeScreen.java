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
                startActivity(new Intent(HomeScreen.this, ReservationsScreen.class));
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
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart() homeScreen", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume()", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop()", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy()", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart()", Toast.LENGTH_SHORT).show();
    }
}