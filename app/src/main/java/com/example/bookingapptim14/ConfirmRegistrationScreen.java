package com.example.bookingapptim14;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


public class ConfirmRegistrationScreen extends AppCompatActivity {

    //private TextView onResendEmailClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_email_screen);

        TextView resendEmailLink = findViewById(R.id.textViewResendEmail);



        ImageView imageView = findViewById(R.id.imageViewLogo);
        ObjectAnimator rotationAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.rotate_animation);
        rotationAnimator.setTarget(imageView);
        rotationAnimator.start();


        resendEmailLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ConfirmRegistrationScreen.this, RegisterScreen.class);
                startActivity(intent);
            }
        });


        //textViewGmail
        TextView feedback = (TextView) findViewById(R.id.textViewGmail);
        feedback.setText(Html.fromHtml("<a href=\"mailto:ask@me.it\">Open Gmail</a>"));
        feedback.setTextColor(Color.WHITE);
        feedback.setMovementMethod(LinkMovementMethod.getInstance());




    }




}
