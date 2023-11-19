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

import com.example.bookingapptim14.admin.MainActivityAdmin;
import com.example.bookingapptim14.guest.MainActivityGuest;
import com.example.bookingapptim14.host.MainActivityHost;

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
        //Drawable emailIcon = getResources().getDrawable(R.drawable.img_4);
        //emailIcon.setBounds(15, 1, (int) (emailIcon.getIntrinsicWidth() * 0.06), (int) (emailIcon.getIntrinsicHeight() * 0.06));
        //usernameEditText.setCompoundDrawables(emailIcon, null, null, null);

        Drawable passwordIcon = getResources().getDrawable(R.drawable.img_3);
        passwordIcon.setBounds(15, 1, (int) (passwordIcon.getIntrinsicWidth() * 0.05), (int) (passwordIcon.getIntrinsicHeight() * 0.05));
        passwordEditText.setCompoundDrawables(null, null, passwordIcon, null);

        //dugme za prijavu
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
