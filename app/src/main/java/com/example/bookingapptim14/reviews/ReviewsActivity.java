package com.example.bookingapptim14.reviews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.Adapters.ReviewsAdapter;
import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.Review;
import com.example.bookingapptim14.models.User;
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
    private TextView averageRatingText;

    private Button actionButton;

    private long userId;

    private String userRole;

    //String userRole = getUserRole();

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
        userId = getCurrentUserId();

        reviewsList = new ArrayList<>();
        //reviewsAdapter = new ReviewsAdapter(reviewsList);
        //reviewsAdapter = new ReviewsAdapter(reviewsList, this);

        averageRatingText = findViewById(R.id.averageRatingTextView);

        userRole = getUserRole();
        reviewsAdapter = new ReviewsAdapter(reviewsList, this, userId, userRole);
        reviewsRecyclerView.setAdapter(reviewsAdapter);

        //currentUserId = getCurrentUserId();


        //userId = getCurrentUserId();

        Intent intent = getIntent();
        if (intent != null) {
            Long accommodationId = intent.getLongExtra("accommodation_id", -1);
            if (accommodationId != -1) {
                fetchAccommodationReviews(accommodationId);
                fetchAverageRating(accommodationId);
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
                    fetchAverageRating(accommodationId);
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
                        Type listType = new TypeToken<List<Review>>() {
                        }.getType();
                        final List<Review> reviews = gson.fromJson(content.toString(), listType);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                reviewsList.clear();
                                reviewsList.addAll(reviews);
                                reviewsAdapter.notifyDataSetChanged();
                                updateNoReviewsMessageVisibility();
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

    public void deleteReviewById(Long reviewId, int position) {
        new Thread(() -> {
            try {
                URL url = new URL(BuildConfig.IP_ADDR + "/api/accommodationReviews/" + reviewId); //+ reviewId
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");

                // Dodavanje Authorization zaglavlja sa JWT tokenom
                String token = getTokenFromSharedPreferences(); // metoda koja dohvata token iz SharedPreferences
                conn.setRequestProperty("Authorization", "Bearer " + token);

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                    runOnUiThread(() -> {
                        reviewsList.remove(position);
                        reviewsAdapter.notifyItemRemoved(position);
                        Toast.makeText(ReviewsActivity.this, "Review deleted successfully", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(ReviewsActivity.this, "Failed to delete review", Toast.LENGTH_SHORT).show());
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(ReviewsActivity.this, "An error occurred", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private String getTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        return sharedPreferences.getString("jwtToken", "");
    }

    private long getCurrentUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        userId = sharedPreferences.getLong("userId", 0);
        return userId;
    }

    private String getUserRole() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");

        if (jwtToken == null || jwtToken.isEmpty()) {
            return null;
        }

        final String[] userRole = {null}; // Mutable container for role

        Thread thread = new Thread(() -> {
            try {
                URL url = new URL(BuildConfig.IP_ADDR + "/api/role");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

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

                    userRole[0] = content.toString();
                } else {
                    Log.e("getUserRole", "Failed to get user role, response code: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
        try {
            thread.join(); // Wait for the thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return userRole[0];
    }

    private void fetchAverageRating(Long accommodationId) {
        new Thread(() -> {
            try {
                URL url = new URL(BuildConfig.IP_ADDR + "/api/accommodation/"+ accommodationId + "/average-rating");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                reader.close();
                connection.disconnect();

                runOnUiThread(() -> {
                    String averageRating = result.toString();
                    averageRatingText.setText("Average Rating: " + averageRating);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }



    private void updateNoReviewsMessageVisibility() {
        TextView noReviewsMessage = findViewById(R.id.noReviewsMessage);
        if (reviewsList.isEmpty()) {
            noReviewsMessage.setVisibility(View.VISIBLE);
            reviewsRecyclerView.setVisibility(View.GONE);
        } else {
            noReviewsMessage.setVisibility(View.GONE);
            reviewsRecyclerView.setVisibility(View.VISIBLE);
        }
    }




}
