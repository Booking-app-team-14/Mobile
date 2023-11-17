package com.example.bookingapptim14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookingapptim14.admin.MainActivityAdmin;
import com.example.bookingapptim14.guest.MainActivityGuest;
import com.example.bookingapptim14.host.MainActivityHost;

public class LoginScreen extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                if (usernameEditText.getText().toString().equals("guest")){
                    intent = new Intent(LoginScreen.this, MainActivityGuest.class);
                }
                else if (usernameEditText.getText().toString().equals("host")){
                    intent = new Intent(LoginScreen.this, MainActivityHost.class);
                }
                else if (usernameEditText.getText().toString().equals("admin")){
                    intent = new Intent(LoginScreen.this, MainActivityAdmin.class);
                }
                else{
                    Toast.makeText(LoginScreen.this, "Enter username", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(intent);
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }

}