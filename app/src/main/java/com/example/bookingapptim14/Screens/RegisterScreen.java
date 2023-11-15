package com.example.bookingapptim14.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;

import com.example.bookingapptim14.R;


public class RegisterScreen extends AppCompatActivity {
    private Button confirmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dodaj kod za prelazak na HomeScreen
                Intent intent = new Intent(RegisterScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });

    }

}