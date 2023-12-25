package com.example.bookingapptim14;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookingapptim14.guest.MainActivityGuest;


public class RegisterScreen extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;

    private EditText editTextConfirmPassword;
    private Button registerButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        //inicijalizacija elemenata sa layouta
        usernameEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.buttonRegister);
        TextView loginLink = findViewById(R.id.textViewLogin);
        //editTextConfirmPassword
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        ImageView imageView = findViewById(R.id.imageViewLogo);
        ObjectAnimator rotationAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.rotate_animation);
        rotationAnimator.setTarget(imageView);
        rotationAnimator.start();




        Drawable passwordIcon = getResources().getDrawable(R.drawable.img_3);
        passwordIcon.setBounds(15, 1, (int) (passwordIcon.getIntrinsicWidth() * 0.05), (int) (passwordIcon.getIntrinsicHeight() * 0.05));
        passwordEditText.setCompoundDrawables(null, null, passwordIcon, null);

        Drawable passwordIcon2 = getResources().getDrawable(R.drawable.img_3);
        passwordIcon2.setBounds(15, 1, (int) (passwordIcon2.getIntrinsicWidth() * 0.05), (int) (passwordIcon.getIntrinsicHeight() * 0.05));
        editTextConfirmPassword.setCompoundDrawables(null, null, passwordIcon2, null);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(RegisterScreen.this, ConfirmRegistrationScreen.class);
                //startActivity(intent);
                showSuccessMessage();
                Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });
    }

    private void showSuccessMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Successful registration\n")
                .setMessage("You have successfully created your account!"
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