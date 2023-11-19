package com.example.bookingapptim14;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapptim14.guest.MainActivityGuest;
import com.example.bookingapptim14.guest.ProfileFragmentGuest;

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

        //podesavanje velicine ikonica
        Drawable emailIcon = getResources().getDrawable(R.drawable.img_4);
        emailIcon.setBounds(15, 1, (int) (emailIcon.getIntrinsicWidth() * 0.06), (int) (emailIcon.getIntrinsicHeight() * 0.06));
        usernameEditText.setCompoundDrawables(emailIcon, null, null, null);

        Drawable passwordIcon = getResources().getDrawable(R.drawable.img_1);
        passwordIcon.setBounds(15, 1, (int) (passwordIcon.getIntrinsicWidth() * 0.15), (int) (passwordIcon.getIntrinsicHeight() * 0.15));
        passwordEditText.setCompoundDrawables(null, null, passwordIcon, null);

        //dugme za prijavu
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dodati proveru unosa emaila i lozinke
                Intent intent = new Intent(LoginScreen.this, MainActivityGuest.class);
                startActivity(intent);
            }
        });

        //hyperlink za registraciju
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
}
