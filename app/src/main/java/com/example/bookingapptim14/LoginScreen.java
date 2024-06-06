package com.example.bookingapptim14;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapptim14.utils.ClientUtils;
import com.example.bookingapptim14.dtos.JwtAuthenticationRequest;
import com.example.bookingapptim14.guest.MainActivityGuest;
import com.example.bookingapptim14.interfaces.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        // Initialize layout elements
        usernameEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        TextView signUpLink = findViewById(R.id.textViewSignUp);

        ImageView imageView = findViewById(R.id.imageView);
        ObjectAnimator rotationAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.rotate_animation);
        rotationAnimator.setTarget(imageView);
        rotationAnimator.start();

        // Set icon for password field
        Drawable passwordIcon = getResources().getDrawable(R.drawable.img_3);
        passwordIcon.setBounds(15, 1, (int) (passwordIcon.getIntrinsicWidth() * 0.05), (int) (passwordIcon.getIntrinsicHeight() * 0.05));
        passwordEditText.setCompoundDrawables(null, null, passwordIcon, null);

        // Initialize ApiService
        apiService = ClientUtils.apiService;

        // Set login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        // Set sign-up link click listener
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        Log.e("LOGIN", username + " " + password);
        JwtAuthenticationRequest request = new JwtAuthenticationRequest(username, password);

        apiService.login(request).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    saveToken(token);
                    showSuccessMessage();
                    navigateToMainActivityGuest();
                } else {
                    showUnsuccessMessage();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("LOGIN", "Failure: " + t.getMessage());
                t.printStackTrace();
                showUnsuccessMessage();
            }
        });
    }

    private void saveToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("jwt_token", token);
        editor.apply();
    }

    private void navigateToMainActivityGuest() {
        Intent intent = new Intent(LoginScreen.this, MainActivityGuest.class);
        startActivity(intent);
        finish();
    }

    private void showSuccessMessage() {
        Toast.makeText(LoginScreen.this, "You successfully logged in!", Toast.LENGTH_SHORT).show();
    }

    private void showUnsuccessMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Unsuccessful login\n")
                .setMessage("Bad credentials! Try again!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onForgotPasswordClick(View view) {
        // Add code for forgot password
    }
}
