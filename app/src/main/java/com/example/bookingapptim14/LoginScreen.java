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
import com.example.bookingapptim14.guest.ProfileFragmentGuest;
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

        // Inicijalizacija elemenata sa layout-a
        usernameEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        TextView signUpLink = findViewById(R.id.textViewSignUp);

        ImageView imageView = findViewById(R.id.imageView);
        ObjectAnimator rotationAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.rotate_animation);
        rotationAnimator.setTarget(imageView);
        rotationAnimator.start();

        // Postavljanje veličine ikonice za email EditText
        Drawable emailIcon = getResources().getDrawable(R.drawable.img_4);
        emailIcon.setBounds(15, 1, (int) (emailIcon.getIntrinsicWidth() * 0.06), (int) (emailIcon.getIntrinsicHeight() * 0.06));
        usernameEditText.setCompoundDrawables(emailIcon, null, null, null);

        Drawable passwordIcon = getResources().getDrawable(R.drawable.img_6);
        passwordIcon.setBounds(15, 1, (int) (passwordIcon.getIntrinsicWidth() * 0.07), (int) (passwordIcon.getIntrinsicHeight() * 0.05));
        passwordEditText.setCompoundDrawables(passwordIcon, null, null, null);

        // Dodajte OnClickListener za dugme za prijavu
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ovde treba dodati kod za proveru unosa korisničkog imena i lozinke.
                // Ako je unos ispravan, prebacite se na HomeScreenActivity.
                Intent intent = new Intent(LoginScreen.this, ProfileFragmentGuest.class);
                startActivity(intent);
            }
        });

        // Dodajte OnClickListener za dugme za registraciju
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ovde dodajte kod za prelazak na ekran za registraciju.
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent);
            }
        });
    }

    // Ostale metode života aktivnosti...

    public void onForgotPasswordClick(View view) {
        // Ovde dodajte kod koji će se izvršiti kada korisnik klikne na "Forgot Password".
        // Na primer, možete otvoriti novu aktivnost za resetovanje lozinke.
    }
}
