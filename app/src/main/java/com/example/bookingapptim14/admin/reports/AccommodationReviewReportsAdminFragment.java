package com.example.bookingapptim14.admin.reports;

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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookingapptim14.Adapters.AdminAccommodationReviewReportsAdapter;
import com.example.bookingapptim14.Adapters.AdminUserReportsAdapter;
import com.example.bookingapptim14.Adapters.LocalDateDeserializer;
import com.example.bookingapptim14.Adapters.LocalDateTimeDeserializer;
import com.example.bookingapptim14.Adapters.ReviewStatusDeserializer;
import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;

import com.example.bookingapptim14.admin.AccommodationDetailsActivityAdmin;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveAccommodationReviewsDTO;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveAccommodationReviewsData;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ReviewStatus;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewDTO;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewReportsDTO;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewReportsData;
import com.example.bookingapptim14.models.dtos.ReportsDTO.UserReportsData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccommodationReviewReportsAdminFragment extends Fragment implements AdminAccommodationReviewReportsAdapter.OnReportListener {

    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximityEventListener;
    private RecyclerView userReportsRecyclerView;
    private AdminAccommodationReviewReportsAdapter adapter;
    private String jwtToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accommodation_review_reports_admin, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");

        userReportsRecyclerView = view.findViewById(R.id.adminAccommodationReviewReportsRecyclerView);
        adapter = new AdminAccommodationReviewReportsAdapter(new ArrayList<>(), this);
        userReportsRecyclerView.setAdapter(adapter);
        userReportsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchAccommodationReviewReports();

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
                        userReportsRecyclerView.smoothScrollBy(0, 1200);
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

    public void fetchAccommodationReviewReports() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/reviewReports");
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

                        List<AccommodationReviewReportsData> accommodationReviewReports = new ArrayList<>();

                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                                //.registerTypeAdapter(ReviewStatus.class, new ReviewStatusDeserializer())
                                .create();
                        Type listType = new TypeToken<List<AccommodationReviewReportsDTO>>(){}.getType();
                        List<AccommodationReviewReportsDTO> accommodationReviewsDTOs = gson.fromJson(content.toString(), listType);

                        for (AccommodationReviewReportsDTO dto : accommodationReviewsDTOs) {
                            // GET /accommodationReviews/{reviewId} -> AccommodationReviewDTO
                            URL reviewUrl = new URL(BuildConfig.IP_ADDR + "/api/accommodationReviews/" + dto.getAccommodationReviewId());
                            HttpURLConnection reviewConn = (HttpURLConnection) reviewUrl.openConnection();
                            reviewConn.setRequestMethod("GET");
                            reviewConn.setDoInput(true);
                            reviewConn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                            int reviewResponseCode = reviewConn.getResponseCode();
                            if (reviewResponseCode == HttpURLConnection.HTTP_OK) {
                                BufferedReader reviewIn = new BufferedReader(new InputStreamReader(reviewConn.getInputStream()));
                                String reviewInputLine;
                                StringBuilder reviewContent = new StringBuilder();
                                while ((reviewInputLine = reviewIn.readLine()) != null) {
                                    reviewContent.append(reviewInputLine);
                                }
                                reviewIn.close();
                                reviewConn.disconnect();

                                AccommodationReviewDTO accommodationReview = gson.fromJson(reviewContent.toString(), AccommodationReviewDTO.class);
//                                Type listType2 = new TypeToken<List<AccommodationReviewDTO>>(){}.getType();
//                                List<AccommodationReviewDTO> accommodationReviewList = gson.fromJson(content.toString(), listType2);
//                                AccommodationReviewDTO accommodationReview = accommodationReviewList.get(0);


                                Log.d("OVDE ID", accommodationReview.getId() + "");
                                Log.d("OVDE CONTNET", content.toString());

                                // GET users/{id}/image-type-username -> userUsername + " | " + userProfilePictureType + " | " + userProfilePictureBytes
                                Log.d("OVDE", accommodationReview.getUserId() + "");
                                URL userUrl = new URL(BuildConfig.IP_ADDR + "/api/users/" + accommodationReview.getUserId() + "/image-type-username");
                                HttpURLConnection userConn = (HttpURLConnection) userUrl.openConnection();
                                userConn.setRequestMethod("GET");
                                userConn.setDoInput(true);
                                userConn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                                int userResponseCode = userConn.getResponseCode();

                                String userUsername = "";
                                String userProfilePictureType = "";
                                String userProfilePictureBytes = "";

                                if (userResponseCode == HttpURLConnection.HTTP_OK) {
                                    BufferedReader userIn = new BufferedReader(new InputStreamReader(userConn.getInputStream()));
                                    String userInputLine;
                                    StringBuilder userContent = new StringBuilder();
                                    while ((userInputLine = userIn.readLine()) != null) {
                                        userContent.append(userInputLine);
                                    }
                                    userIn.close();
                                    userConn.disconnect();

                                    String[] userParts = userContent.toString().split(" \\| ");
                                    userUsername = userParts[0];
                                    userProfilePictureType = userParts[1];
                                    userProfilePictureBytes = userParts[2];
                                }

                                // GET accommodations/{accommodationId}/nameAndTypeAndBytes -> accommodationName + " | " + accommodationType + " | " + accommodationImageBytes
                                URL accommodationUrl = new URL(BuildConfig.IP_ADDR + "/api/accommodations/" + accommodationReview.getAccommodationId() + "/nameAndTypeAndBytes");
                                HttpURLConnection accommodationConn = (HttpURLConnection) accommodationUrl.openConnection();
                                accommodationConn.setRequestMethod("GET");
                                accommodationConn.setDoInput(true);
                                accommodationConn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                                int accommodationResponseCode = accommodationConn.getResponseCode();

                                String accommodationName = "";
                                String accommodationType = "";
                                String accommodationImageBytes = "";

                                if (accommodationResponseCode == HttpURLConnection.HTTP_OK) {
                                    BufferedReader accommodationIn = new BufferedReader(new InputStreamReader(accommodationConn.getInputStream()));
                                    String accommodationInputLine;
                                    StringBuilder accommodationContent = new StringBuilder();
                                    while ((accommodationInputLine = accommodationIn.readLine()) != null) {
                                        accommodationContent.append(accommodationInputLine);
                                    }
                                    accommodationIn.close();
                                    accommodationConn.disconnect();

                                    String[] accommodationParts = accommodationContent.toString().split(" \\| ");
                                    accommodationName = accommodationParts[0];
                                    accommodationType = accommodationParts[1];
                                    accommodationImageBytes = accommodationParts[2];
                                }

                                AccommodationReviewReportsData report = new AccommodationReviewReportsData(dto, accommodationReview, userProfilePictureBytes, userUsername, accommodationName, accommodationType, accommodationImageBytes);
                                accommodationReviewReports.add(report);
                            }
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setAccommodationReviewReports(accommodationReviewReports);
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
    public void onReviewRemoved(AccommodationReviewReportsData report) {
        // DELETE api/reviewReports/accommodationReviews/{reviewReportId}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/reviewReports/accommodationReviews/" + report.getId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("DELETE");
                    conn.setDoInput(true);
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.removeItem(report);
                                Toast.makeText(getContext(), "Review removed!", Toast.LENGTH_SHORT).show();
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
    public void onReviewReportDismissed(AccommodationReviewReportsData report) {
        // DELETE api/reviewReports/{reviewReportId}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/reviewReports/" + report.getId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("DELETE");
                    conn.setDoInput(true);
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.removeItem(report);
                                Toast.makeText(getContext(), "Review report dismissed!", Toast.LENGTH_SHORT).show();
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
    public void onDetailsRequested(AccommodationReviewReportsData report) {
        Intent intent = new Intent(getActivity(), AccommodationDetailsActivityAdmin.class);
        intent.putExtra("accommodation_id", report.getAccommodationReviewDTO().getAccommodationId());
        startActivity(intent);
    }

}