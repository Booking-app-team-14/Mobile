package com.example.bookingapptim14.admin.approval;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.Adapters.AccommodationApprovalAdapter;
import com.example.bookingapptim14.Adapters.AdminApprovalAccommodationCommentsAndReviewsAdapter;
import com.example.bookingapptim14.Adapters.AdminApprovalOwnerCommentsAndReviewsAdapter;
import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.admin.AccommodationDetailsActivityAdmin;
import com.example.bookingapptim14.guest.AccommodationDetailsActivityGuest;
import com.example.bookingapptim14.models.AccommodationRequest;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveAccommodationReviewsDTO;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveAccommodationReviewsData;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveOwnerReviewsDTO;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveOwnerReviewsData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CommentsAndReviewsApprovalFragment extends Fragment implements AdminApprovalAccommodationCommentsAndReviewsAdapter.OnAccommodationActionListener, AdminApprovalOwnerCommentsAndReviewsAdapter.OnOwnerActionListener {

    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximityEventListener;
    private RecyclerView accommodationCommentsAndReviewsApprovalRecyclerView;
    private RecyclerView ownerCommentsAndReviewsApprovalRecyclerView;
    private AdminApprovalAccommodationCommentsAndReviewsAdapter accommodationCommentsAndReviewsAdapter;
    private AdminApprovalOwnerCommentsAndReviewsAdapter ownerCommentsAndReviewsAdapter;
    private String jwtToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments_and_reviews_approval, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");

        // Get references to the RecyclerViews
        accommodationCommentsAndReviewsApprovalRecyclerView = view.findViewById(R.id.adminCommentsAndReviewsAccommodationsRecyclerView);
        ownerCommentsAndReviewsApprovalRecyclerView = view.findViewById(R.id.adminCommentsAndReviewsOwnersRecyclerView);

        // Initialize the adapters
        accommodationCommentsAndReviewsAdapter = new AdminApprovalAccommodationCommentsAndReviewsAdapter(new ArrayList<>(), this);
        ownerCommentsAndReviewsAdapter = new AdminApprovalOwnerCommentsAndReviewsAdapter(new ArrayList<>(), this);

        // Set the adapters to the RecyclerViews
        accommodationCommentsAndReviewsApprovalRecyclerView.setAdapter(accommodationCommentsAndReviewsAdapter);
        ownerCommentsAndReviewsApprovalRecyclerView.setAdapter(ownerCommentsAndReviewsAdapter);

        // Set the layout managers to the RecyclerViews
        accommodationCommentsAndReviewsApprovalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ownerCommentsAndReviewsApprovalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initially, let's assume that the accommodationCommentsAndReviewsApprovalRecyclerView is visible
        accommodationCommentsAndReviewsApprovalRecyclerView.setVisibility(View.VISIBLE);
        ownerCommentsAndReviewsApprovalRecyclerView.setVisibility(View.GONE);

        // Get reference to the toggle button
        ToggleButton toggleButton = view.findViewById(R.id.adminCommentsAndReviewsToggleButton);

        // Set an OnCheckedChangeListener
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled/checked
                    accommodationCommentsAndReviewsApprovalRecyclerView.setVisibility(View.GONE);
                    ownerCommentsAndReviewsApprovalRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    // The toggle is disabled/unchecked
                    accommodationCommentsAndReviewsApprovalRecyclerView.setVisibility(View.VISIBLE);
                    ownerCommentsAndReviewsApprovalRecyclerView.setVisibility(View.GONE);
                }
            }
        });

        fetchCommentsAndReviews();

        sensorManager = (SensorManager) getContext().getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (proximitySensor != null) {
            proximityEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if(event.values[0] < proximitySensor.getMaximumRange()) {
                        // Detected something nearby
                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        if (accommodationCommentsAndReviewsApprovalRecyclerView.getVisibility() == View.VISIBLE)
                            accommodationCommentsAndReviewsApprovalRecyclerView.smoothScrollBy(0, 1200);
                        else if (ownerCommentsAndReviewsApprovalRecyclerView.getVisibility() == View.VISIBLE)
                            ownerCommentsAndReviewsApprovalRecyclerView.smoothScrollBy(0, 1200);
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };
        } else {
            Toast.makeText(getContext(), "This device has no proximity sensor", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(proximityEventListener, proximitySensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(proximityEventListener);
    }

    private void fetchCommentsAndReviews() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/accommodations/requests");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuilder content = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                        in.close();
                        conn.disconnect();

                        List<ApproveAccommodationReviewsData> accommodationCommentsAndReviews = new ArrayList<>();

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<ApproveAccommodationReviewsDTO>>(){}.getType();
                        List<ApproveAccommodationReviewsDTO> accommodationReviewsDTOs = gson.fromJson(content.toString(), listType);

                        for (ApproveAccommodationReviewsDTO dto : accommodationReviewsDTOs) {
                            // GET /users/{id}/image-type-username" -> guestUsername + " | " + guestProfilePictureType + " | " + guestProfilePictureBytes
                            URL userUrl = new URL(BuildConfig.IP_ADDR + "/api/users/" + dto.getUserId() + "/image-type-username");
                            HttpURLConnection userConn = (HttpURLConnection) userUrl.openConnection();
                            userConn.setRequestMethod("GET");
                            userConn.setDoInput(true);
                            userConn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                            int userResponseCode = userConn.getResponseCode();

                            String guestUsername = "";
                            String guestProfilePictureType = "";
                            String guestProfilePictureBytes = "";

                            if (userResponseCode == HttpURLConnection.HTTP_OK) {
                                BufferedReader userIn = new BufferedReader(new InputStreamReader(userConn.getInputStream()));
                                String userInputLine;
                                StringBuilder userContent = new StringBuilder();
                                while ((userInputLine = userIn.readLine()) != null) {
                                    userContent.append(userInputLine);
                                }
                                userIn.close();
                                userConn.disconnect();

                                String[] userResponse = userContent.toString().split(" \\| ");
                                guestUsername = userResponse[0];
                                guestProfilePictureType = userResponse[1];
                                guestProfilePictureBytes = userResponse[2];
                            }

                            // GET accommodations/{id}/nameAndType -> accommodationName + " | " + accommodationType
                            URL accommodationUrl = new URL(BuildConfig.IP_ADDR + "/api/accommodations/" + dto.getAccommodationId() + "/nameAndType");
                            HttpURLConnection accommodationConn = (HttpURLConnection) accommodationUrl.openConnection();
                            accommodationConn.setRequestMethod("GET");
                            accommodationConn.setDoInput(true);
                            accommodationConn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                            int accommodationResponseCode = accommodationConn.getResponseCode();

                            String accommodationName = "";
                            String accommodationType = "";

                            if (accommodationResponseCode == HttpURLConnection.HTTP_OK) {
                                BufferedReader accommodationIn = new BufferedReader(new InputStreamReader(accommodationConn.getInputStream()));
                                String accommodationInputLine;
                                StringBuilder accommodationContent = new StringBuilder();
                                while ((accommodationInputLine = accommodationIn.readLine()) != null) {
                                    accommodationContent.append(accommodationInputLine);
                                }
                                accommodationIn.close();
                                accommodationConn.disconnect();

                                String[] accommodationResponse = accommodationContent.toString().split(" \\| ");
                                accommodationName = accommodationResponse[0];
                                accommodationType = accommodationResponse[1];
                            }

                            // GET accommodations/accommodations/{Id}/image -> accommodationPictureBytes as String
                            URL accommodationImageURL = new URL(BuildConfig.IP_ADDR + "/api/accommodations/accommodations/" + dto.getAccommodationId() + "/image");
                            HttpURLConnection accommodationImageConn = (HttpURLConnection) accommodationImageURL.openConnection();
                            accommodationImageConn.setRequestMethod("GET");
                            accommodationImageConn.setDoInput(true);
                            accommodationImageConn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                            int accommodationImageResponseCode = accommodationImageConn.getResponseCode();

                            String accommodationPictureBytes = "";

                            if (accommodationImageResponseCode == HttpURLConnection.HTTP_OK) {
                                BufferedReader accommodationImageIn = new BufferedReader(new InputStreamReader(accommodationImageConn.getInputStream()));
                                String accommodationImageInputLine;
                                StringBuilder accommodationImageContent = new StringBuilder();
                                while ((accommodationImageInputLine = accommodationImageIn.readLine()) != null) {
                                    accommodationImageContent.append(accommodationImageInputLine);
                                }
                                accommodationImageIn.close();
                                accommodationImageConn.disconnect();

                                accommodationPictureBytes = accommodationImageContent.toString();
                            }

                            // GET /accommodations/{id}/ratingString -> rating as String
                            URL ratingUrl = new URL(BuildConfig.IP_ADDR + "/api/accommodations/" + dto.getAccommodationId() + "/ratingString");
                            HttpURLConnection ratingConn = (HttpURLConnection) ratingUrl.openConnection();
                            ratingConn.setRequestMethod("GET");

                            int ratingResponseCode = ratingConn.getResponseCode();

                            String rating = "";

                            if (ratingResponseCode == HttpURLConnection.HTTP_OK) {
                                BufferedReader ratingIn = new BufferedReader(new InputStreamReader(ratingConn.getInputStream()));
                                String ratingInputLine;
                                StringBuilder ratingContent = new StringBuilder();
                                while ((ratingInputLine = ratingIn.readLine()) != null) {
                                    ratingContent.append(ratingInputLine);
                                }
                                ratingIn.close();
                                ratingConn.disconnect();

                                rating = ratingContent.toString();
                            }

                            ApproveAccommodationReviewsData data = new ApproveAccommodationReviewsData(dto,
                                    guestUsername, guestProfilePictureType, guestProfilePictureBytes, accommodationName, accommodationType, accommodationPictureBytes, rating);
                            accommodationCommentsAndReviews.add(data);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                accommodationCommentsAndReviewsAdapter.setAccommodationCommentsAndReviews(accommodationCommentsAndReviews);
                            }
                        });
                    } else {
                        System.out.println("GET request failed!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/owner/requests");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuilder content = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                        in.close();
                        conn.disconnect();

                        List<ApproveOwnerReviewsData> ownerCommentsAndReviews = new ArrayList<>();

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<ApproveOwnerReviewsDTO>>(){}.getType();
                        List<ApproveOwnerReviewsDTO> ownerReviewsDTOs = gson.fromJson(content.toString(), listType);

                        for (ApproveOwnerReviewsDTO dto : ownerReviewsDTOs) {
                            // GET /users/{guestId}/image-type-username" -> guestUsername + " | " + guestProfilePictureType + " | " + guestProfilePictureBytes
                            URL userUrl = new URL(BuildConfig.IP_ADDR + "/api/users/" + dto.getSenderId() + "/image-type-username");
                            HttpURLConnection userConn = (HttpURLConnection) userUrl.openConnection();
                            userConn.setRequestMethod("GET");
                            userConn.setDoInput(true);
                            userConn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                            int userResponseCode = userConn.getResponseCode();

                            String guestUsername = "";
                            String guestProfilePictureType = "";
                            String guestProfilePictureBytes = "";

                            if (userResponseCode == HttpURLConnection.HTTP_OK) {
                                BufferedReader userIn = new BufferedReader(new InputStreamReader(userConn.getInputStream()));
                                String userInputLine;
                                StringBuilder userContent = new StringBuilder();
                                while ((userInputLine = userIn.readLine()) != null) {
                                    userContent.append(userInputLine);
                                }
                                userIn.close();
                                userConn.disconnect();

                                String[] userResponse = userContent.toString().split(" \\| ");
                                guestUsername = userResponse[0];
                                guestProfilePictureType = userResponse[1];
                                guestProfilePictureBytes = userResponse[2];
                            }

                            // GET /users/{ownerId}/image-type-username" -> ownerUsername + " | " + ownerProfilePictureType + " | " + ownerProfilePictureBytes
                            URL ownerUrl = new URL(BuildConfig.IP_ADDR + "/api/users/" + dto.getRecipientId() + "/image-type-username");
                            HttpURLConnection ownerConn = (HttpURLConnection) ownerUrl.openConnection();
                            ownerConn.setRequestMethod("GET");
                            ownerConn.setDoInput(true);
                            ownerConn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                            int ownerResponseCode = ownerConn.getResponseCode();

                            String ownerUsername = "";
                            String ownerProfilePictureType = "";
                            String ownerProfilePictureBytes = "";

                            if (ownerResponseCode == HttpURLConnection.HTTP_OK) {
                                BufferedReader ownerIn = new BufferedReader(new InputStreamReader(ownerConn.getInputStream()));
                                String ownerInputLine;
                                StringBuilder ownerContent = new StringBuilder();
                                while ((ownerInputLine = ownerIn.readLine()) != null) {
                                    ownerContent.append(ownerInputLine);
                                }
                                ownerIn.close();
                                ownerConn.disconnect();

                                String[] ownerResponse = ownerContent.toString().split(" \\| ");
                                ownerUsername = ownerResponse[0];
                                ownerProfilePictureType = ownerResponse[1];
                                ownerProfilePictureBytes = ownerResponse[2];
                            }

                            // GET /owners/{id}/ratingString -> ratingBefore as String
                            URL ratingUrl = new URL(BuildConfig.IP_ADDR + "/api/owners/" + dto.getRecipientId() + "/ratingString");
                            HttpURLConnection ratingConn = (HttpURLConnection) ratingUrl.openConnection();
                            ratingConn.setRequestMethod("GET");

                            int ratingResponseCode = ratingConn.getResponseCode();

                            String ratingBefore = "";

                            if (ratingResponseCode == HttpURLConnection.HTTP_OK) {
                                BufferedReader ratingIn = new BufferedReader(new InputStreamReader(ratingConn.getInputStream()));
                                String ratingInputLine;
                                StringBuilder ratingContent = new StringBuilder();
                                while ((ratingInputLine = ratingIn.readLine()) != null) {
                                    ratingContent.append(ratingInputLine);
                                }
                                ratingIn.close();
                                ratingConn.disconnect();

                                ratingBefore = ratingContent.toString();
                            }

                            ApproveOwnerReviewsData data = new ApproveOwnerReviewsData(dto,
                                    guestUsername, guestProfilePictureType, guestProfilePictureBytes, ownerUsername, ownerProfilePictureType, ownerProfilePictureBytes, ratingBefore);
                            ownerCommentsAndReviews.add(data);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ownerCommentsAndReviewsAdapter.setOwnerCommentsAndReviews(ownerCommentsAndReviews);
                            }
                        });
                    } else {
                        System.out.println("GET request failed!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onAccommodationCommentAndReviewApproved(ApproveAccommodationReviewsData commentAndReview) {
        // PUT /reviews/admin/accommodation/{accommodationId}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/reviews/admin/accommodation/" + commentAndReview.getId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setDoInput(true);
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                accommodationCommentsAndReviewsAdapter.removeItem(commentAndReview);
                                Toast.makeText(getContext(), "Approved!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        System.out.println("PUT request failed!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onAccommodationCommentAndReviewRejected(ApproveAccommodationReviewsData commentAndReview) {
        // DELETE /reviews/admin/accommodation/{accommodationId}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/reviews/admin/accommodation/" + commentAndReview.getId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("DELETE");
                    conn.setDoInput(true);
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                accommodationCommentsAndReviewsAdapter.removeItem(commentAndReview);
                                Toast.makeText(getContext(), "Successfully rejected!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        System.out.println("DELETE request failed!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onAccommodationCommentAndReviewDetailsRequested(ApproveAccommodationReviewsData commentAndReview) {
        Intent intent = new Intent(getActivity(), AccommodationDetailsActivityAdmin.class);
        intent.putExtra("accommodationId", commentAndReview.getAccommodationId());
        startActivity(intent);
    }

    @Override
    public void onOwnerCommentAndReviewApproved(ApproveOwnerReviewsData commentAndReview) {
//         PUT reviews/admin/{reviewId}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/reviews/admin/" + commentAndReview.getId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setDoInput(true);
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ownerCommentsAndReviewsAdapter.removeItem(commentAndReview);
                                Toast.makeText(getContext(), "Approved!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        System.out.println("PUT request failed!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onOwnerCommentAndReviewRejected(ApproveOwnerReviewsData commentAndReview) {
//         DELETE reviews/admin/{reviewId}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/reviews/admin/" + commentAndReview.getId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("DELETE");
                    conn.setDoInput(true);
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ownerCommentsAndReviewsAdapter.removeItem(commentAndReview);
                                Toast.makeText(getContext(), "Successfully rejected!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        System.out.println("DELETE request failed!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
