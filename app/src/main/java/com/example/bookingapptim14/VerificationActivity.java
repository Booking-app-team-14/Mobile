package com.example.bookingapptim14;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class VerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        TextView tvStatus = findViewById(R.id.tv_verification_status);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        Uri data = getIntent().getData();
        if (data != null) {
            String userId = data.getQueryParameter("userId");
            verifyUserAccount(userId, tvStatus, progressBar);
        }
    }

    private void verifyUserAccount(String userId, TextView tvStatus, ProgressBar progressBar) {
        String url = "http://192.168.0.108:8080/verify/users/" + userId;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                response -> {
                    // Verifikacija uspešna
                    progressBar.setVisibility(View.GONE);
                    tvStatus.setText("Your account has been successfully verified!");
                },
                error -> {
                    // Greška tokom verifikacije
                    progressBar.setVisibility(View.GONE);
                    tvStatus.setText("Verification failed: " + error.getMessage());
                });

        queue.add(stringRequest);
    }
}
