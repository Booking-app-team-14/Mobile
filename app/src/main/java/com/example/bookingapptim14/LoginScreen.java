package com.example.bookingapptim14;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapptim14.admin.MainActivityAdmin;
import com.example.bookingapptim14.guest.AccommodationDetailsActivityGuest;
import com.example.bookingapptim14.guest.MainActivityGuest;
import com.example.bookingapptim14.host.MainActivityHost;
import com.example.bookingapptim14.interfaces.AuthenticationService;
import com.example.bookingapptim14.models.JwtAuthenticationRequest;
import com.example.bookingapptim14.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        // inicijalizacija elemenata sa layouta
        usernameEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        TextView signUpLink = findViewById(R.id.textViewSignUp);

        ImageView imageView = findViewById(R.id.imageView);
        ObjectAnimator rotationAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.rotate_animation);
        rotationAnimator.setTarget(imageView);
        rotationAnimator.start();

        Drawable passwordIcon = getResources().getDrawable(R.drawable.img_3);
        passwordIcon.setBounds(15, 1, (int) (passwordIcon.getIntrinsicWidth() * 0.05), (int) (passwordIcon.getIntrinsicHeight() * 0.05));
        passwordEditText.setCompoundDrawables(null, null, passwordIcon, null);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                AuthenticationService authService = RetrofitClient.getInstance().create(AuthenticationService.class);
                JwtAuthenticationRequest authRequest = new JwtAuthenticationRequest(username, password);

                Call<String> call = authService.createAuthenticationToken(authRequest);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        int statusCode = response.code();

                        if (response.isSuccessful()) {
                            String jwtToken = response.body();
                            showSuccessMessage();
                            Intent intent = new Intent(LoginScreen.this, MainActivityGuest.class);
                            startActivity(intent);
                        } else {
                            showUnsuccessMessage();
                            Log.e("AuthenticationError", "Authentication failed with status code: " + statusCode);
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        showUnsuccessMessage();
                        Log.e("AuthenticationError", "Authentication request failed", t);
                        t.printStackTrace();

                    }

                });
            }
        });
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent);
            }
        });
    }
    public void onForgotPasswordClick(View view) {
        //dodati kod
    }
    private void showSuccessMessage() {
        Toast.makeText(LoginScreen.this, "You successfully logged in!", Toast.LENGTH_SHORT).show();
    }
    private void showUnsuccessMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Unsuccessful login\n")
                .setMessage("Bad credentials! Try again!"
                )
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
