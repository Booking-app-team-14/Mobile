package com.example.bookingapptim14.admin.reports;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookingapptim14.Adapters.AdminAccommodationReviewReportsAdapter;
import com.example.bookingapptim14.Adapters.AdminOwnerReviewReportAdapter;
import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewDTO;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewReportsDTO;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewReportsData;
import com.example.bookingapptim14.models.dtos.ReportsDTO.OwnerReviewDTO;
import com.example.bookingapptim14.models.dtos.ReportsDTO.OwnerReviewReportsDTO;
import com.example.bookingapptim14.models.dtos.ReportsDTO.OwnerReviewReportsData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OwnerReviewReportsAdminFragment extends Fragment implements AdminOwnerReviewReportAdapter.OnReportListener {

    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximityEventListener;
    private RecyclerView userReportsRecyclerView;
    private AdminOwnerReviewReportAdapter adapter;
    private String jwtToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_review_reports_admin, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");

        userReportsRecyclerView = view.findViewById(R.id.adminOwnerReviewReportsRecyclerView);
        adapter = new AdminOwnerReviewReportAdapter(new ArrayList<>(), this);
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
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/ownerReviewReports");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        List<OwnerReviewReportsData> ownerReviewReports = new ArrayList<>();

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<OwnerReviewReportsDTO>>(){}.getType();
                        List<OwnerReviewReportsDTO> ownerReviewReportsDTOs = gson.fromJson(response.toString(), listType);

                        for (OwnerReviewReportsDTO dto : ownerReviewReportsDTOs) {
                            // GET /api/reviews/{reviewId} -> OwnerReviewDTO
                            URL reviewUrl = new URL(BuildConfig.IP_ADDR + "/api/reviews/" + dto.getReviewId());
                            HttpURLConnection reviewConn = (HttpURLConnection) reviewUrl.openConnection();
                            reviewConn.setRequestMethod("GET");
                            reviewConn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                            int reviewResponseCode = reviewConn.getResponseCode();
                            if (reviewResponseCode == HttpURLConnection.HTTP_OK) {
                                BufferedReader reviewIn = new BufferedReader(new InputStreamReader(reviewConn.getInputStream()));
                                String reviewInputLine;
                                StringBuffer reviewResponse = new StringBuffer();
                                while ((reviewInputLine = reviewIn.readLine()) != null) {
                                    reviewResponse.append(reviewInputLine);
                                }
                                reviewIn.close();
                                reviewConn.disconnect();

                                OwnerReviewDTO ownerReviewDTO = gson.fromJson(reviewResponse.toString(), OwnerReviewDTO.class);

                                // GET users/{senderId}/image-type-username -> senderUsername + " | " + senderProfilePictureType + " | " + senderProfilePictureBytes
                                URL senderUrl = new URL(BuildConfig.IP_ADDR + "/users/" + ownerReviewDTO.getSenderId() + "/image-type-username");
                                HttpURLConnection senderConn = (HttpURLConnection) senderUrl.openConnection();
                                senderConn.setRequestMethod("GET");
                                senderConn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                                int senderResponseCode = senderConn.getResponseCode();

                                String senderUsername = "";
                                String senderProfilePictureType = "";
                                String senderProfilePictureBytes = "";

                                if (senderResponseCode == HttpURLConnection.HTTP_OK) {
                                    BufferedReader senderIn = new BufferedReader(new InputStreamReader(senderConn.getInputStream()));
                                    String senderInputLine;
                                    StringBuffer senderResponse = new StringBuffer();
                                    while ((senderInputLine = senderIn.readLine()) != null) {
                                        senderResponse.append(senderInputLine);
                                    }
                                    senderIn.close();
                                    senderConn.disconnect();

                                    String[] senderData = senderResponse.toString().split(" \\| ");
                                    senderUsername = senderData[0];
                                    senderProfilePictureType = senderData[1];
                                    senderProfilePictureBytes = senderData[2];
                                }

                                // GET users/{recipientId}/image-type-username -> recipientUsername + " | " + recipientProfilePictureType + " | " + recipientProfilePictureBytes
                                URL recipientUrl = new URL(BuildConfig.IP_ADDR + "/users/" + ownerReviewDTO.getRecipientId() + "/image-type-username");
                                HttpURLConnection recipientConn = (HttpURLConnection) recipientUrl.openConnection();
                                recipientConn.setRequestMethod("GET");
                                recipientConn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                                int recipientResponseCode = recipientConn.getResponseCode();

                                String recipientUsername = "";
                                String recipientProfilePictureType = "";
                                String recipientProfilePictureBytes = "";

                                if (recipientResponseCode == HttpURLConnection.HTTP_OK) {
                                    BufferedReader recipientIn = new BufferedReader(new InputStreamReader(recipientConn.getInputStream()));
                                    String recipientInputLine;
                                    StringBuffer recipientResponse = new StringBuffer();
                                    while ((recipientInputLine = recipientIn.readLine()) != null) {
                                        recipientResponse.append(recipientInputLine);
                                    }
                                    recipientIn.close();
                                    recipientConn.disconnect();

                                    String[] recipientData = recipientResponse.toString().split(" \\| ");
                                    recipientUsername = recipientData[0];
                                    recipientProfilePictureType = recipientData[1];
                                    recipientProfilePictureBytes = recipientData[2];
                                }

                                // GET api/owners/{recipientId}/ratingString -> ratingBeforeString
                                URL ratingUrl = new URL(BuildConfig.IP_ADDR + "/api/owners/" + ownerReviewDTO.getRecipientId() + "/ratingString");
                                HttpURLConnection ratingConn = (HttpURLConnection) ratingUrl.openConnection();
                                ratingConn.setRequestMethod("GET");
                                ratingConn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                                int ratingResponseCode = ratingConn.getResponseCode();

                                String ratingBeforeString = "";

                                if (ratingResponseCode == HttpURLConnection.HTTP_OK) {
                                    BufferedReader ratingIn = new BufferedReader(new InputStreamReader(ratingConn.getInputStream()));
                                    String ratingInputLine;
                                    StringBuffer ratingResponse = new StringBuffer();
                                    while ((ratingInputLine = ratingIn.readLine()) != null) {
                                        ratingResponse.append(ratingInputLine);
                                    }
                                    ratingIn.close();
                                    ratingConn.disconnect();

                                    ratingBeforeString = ratingResponse.toString();
                                }

                                OwnerReviewReportsData ownerReviewReportsData = new OwnerReviewReportsData(dto, ownerReviewDTO, senderUsername, senderProfilePictureBytes, recipientUsername, recipientProfilePictureBytes, ratingBeforeString);
                                ownerReviewReports.add(ownerReviewReportsData);
                            }
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setOwnerReviewReports(ownerReviewReports);
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
    public void onReviewRemoved(OwnerReviewReportsData report) {
        // DELETE api/ownerReviewReports/ownerReviews/{reviewReportId}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/ownerReviewReports/ownerReviews/" + report.getId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("DELETE");
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
    public void onReviewReportDismissed(OwnerReviewReportsData report) {
        // DELETE api/ownerReviewReports/{reviewReportId}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/ownerReviewReports/" + report.getId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("DELETE");
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.removeItem(report);
                                Toast.makeText(getContext(), "Report dismissed!", Toast.LENGTH_SHORT).show();
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