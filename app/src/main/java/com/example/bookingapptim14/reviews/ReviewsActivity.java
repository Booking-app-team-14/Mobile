package com.example.bookingapptim14.reviews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.Adapters.ReviewsAdapter;
import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.Review;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import androidx.fragment.app.Fragment;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity {

    private RecyclerView reviewsRecyclerView;
    private ReviewsAdapter reviewsAdapter;
    private List<Review> reviewsList;
    private EditText reviewCommentInput;
    private RatingBar reviewRatingBar;
    private Button submitReviewButton;
    private String jwtToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        // Inicijalizacija elemenata
        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        reviewCommentInput = findViewById(R.id.commentInput);
        reviewRatingBar = findViewById(R.id.ratingBar);
        submitReviewButton = findViewById(R.id.submitReviewButton);

        reviewsList = new ArrayList<>();
        reviewsAdapter = new ReviewsAdapter(reviewsList);
        reviewsRecyclerView.setAdapter(reviewsAdapter);

        Intent intent = getIntent();
        if (intent != null) {
            Long accommodationId = intent.getLongExtra("accommodation_id", -1);
            if (accommodationId != -1) {
                fetchAccommodationReviews(accommodationId);
            } else {
                Toast.makeText(this, "Accommodation ID not provided", Toast.LENGTH_SHORT).show();
                finish();
            }

            submitReviewButton.setOnClickListener(v -> {
                String comment = reviewCommentInput.getText().toString().trim();
                float rating = reviewRatingBar.getRating();

                if (comment.isEmpty() && rating <= 0) {
                    Toast.makeText(ReviewsActivity.this, "Please provide a comment or a rating", Toast.LENGTH_SHORT).show();
                } else {
                    // If the comment is empty, set it to an empty string
                    if (comment.isEmpty()) {
                        comment = "";
                    }
                    // If the rating is not set, set it to -1
                    if (rating <= 0) {
                        rating = -1;
                    }
                    submitReview(accommodationId, rating, comment);
                }
            });

        }
    }

    private void fetchAccommodationReviews(Long accommodationId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Placeholder URL, replace with your actual backend endpoint
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/accommodations/" + accommodationId + "/accommodationReviews/pending");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder content = new StringBuilder();
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                        in.close();
                        conn.disconnect();

                        // Parsing JSON response to Review objects
                        Gson gson = new GsonBuilder().create();
                        Type listType = new TypeToken<List<Review>>(){}.getType();
                        final List<Review> reviews = gson.fromJson(content.toString(), listType);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                reviewsList.clear();
                                reviewsList.addAll(reviews);
                                reviewsAdapter.notifyDataSetChanged();
                            }
                        });
                    } else {
                        // Handle error
                        Log.e("ReviewsActivity", "Error response code: " + responseCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void submitReview(Long accommodationId, float rating, String comment) {
        new Thread(() -> {
            try {
                URL url = new URL(BuildConfig.IP_ADDR + "/api/accommodations/accommodationReviews");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json; utf-8");

                // Dodavanje Authorization zaglavlja sa JWT tokenom
                String token = getTokenFromSharedPreferences(); // metoda koja dohvata token iz SharedPreferences
                conn.setRequestProperty("Authorization", "Bearer " + token);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("accommodationId", accommodationId);
                jsonParam.put("rating", (int) rating);
                jsonParam.put("comment", comment);

                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(jsonParam.toString());
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    runOnUiThread(() -> {
                        Toast.makeText(ReviewsActivity.this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
                        reviewCommentInput.setText("");
                        reviewRatingBar.setRating(0);
                        fetchAccommodationReviews(accommodationId);
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(ReviewsActivity.this, "Failed to submit review", Toast.LENGTH_SHORT).show());
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(ReviewsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }).start();
    }

    private String getTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        return sharedPreferences.getString("jwtToken", "");
    }
}
