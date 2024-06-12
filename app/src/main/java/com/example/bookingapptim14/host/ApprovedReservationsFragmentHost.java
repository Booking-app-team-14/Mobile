package com.example.bookingapptim14.host;

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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookingapptim14.Adapters.AccommodationApprovalAdapter;
import com.example.bookingapptim14.Adapters.ApprovedReservationsAdapter;
import com.example.bookingapptim14.Adapters.HostAccommodationsAdapter;
import com.example.bookingapptim14.Adapters.LocalDateDeserializer;
import com.example.bookingapptim14.Adapters.LocalDateTimeDeserializer;
import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.OwnersAccommodationDTO;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewDTO;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewReportsDTO;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewReportsData;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ReservationRequestDTO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ApprovedReservationsFragmentHost extends Fragment {

    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximityEventListener;
    private RecyclerView reservationsRecyclerView;
    private ApprovedReservationsAdapter adapter;
    private String jwtToken;
    private long userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_approved_reservations_host, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        userId = sharedPreferences.getLong("userId", 0);

        reservationsRecyclerView = view.findViewById(R.id.approvedReservationsRecyclerView);
        adapter = new ApprovedReservationsAdapter(new ArrayList<>());
        reservationsRecyclerView.setAdapter(adapter);
        reservationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchReservations();

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
                        reservationsRecyclerView.smoothScrollBy(0, 1200);
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

    private void fetchReservations() {
        // GET /api/requests/owner/id/{id} -> ReservationRequestDTOs
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/requests/owner/id/" + userId);
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

                        List<ApprovedReservationData> approvedReservations = new ArrayList<>();

                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                                .create();
                        Type listType = new TypeToken<List<ReservationRequestDTO>>(){}.getType();
                        List<ReservationRequestDTO> reservationRequestDTOs = gson.fromJson(content.toString(), listType);

                        for (ReservationRequestDTO dto : reservationRequestDTOs) {
                           if (dto.getRequestStatus().equals("ACCEPTED")) {
                               // GET api/users/{id}/usernameAndNumberOfCancellations -> vraca string username + " | " + numberOfCancellationsString
                                 URL url2 = new URL(BuildConfig.IP_ADDR + "/api/users/" + dto.getGuestId() + "/usernameAndNumberOfCancellations");
                                    HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
                                    conn2.setRequestMethod("GET");
                                    conn2.setDoInput(true);
                                    conn2.setRequestProperty("Authorization", "Bearer " + jwtToken);

                                    int responseCode2 = conn2.getResponseCode();

                                    if (responseCode2 == HttpURLConnection.HTTP_OK) {
                                        BufferedReader in2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
                                        String inputLine2;
                                        StringBuilder content2 = new StringBuilder();
                                        while ((inputLine2 = in2.readLine()) != null) {
                                            content2.append(inputLine2);
                                        }
                                        in2.close();
                                        conn2.disconnect();

                                        String[] usernameAndNumberOfCancellations = content2.toString().split(" \\| ");
                                        String username = usernameAndNumberOfCancellations[0];
                                        String numberOfCancellationsString = usernameAndNumberOfCancellations[1];;

                                        ApprovedReservationData approvedReservation = new ApprovedReservationData(dto, dto.getDateRequested(), username, numberOfCancellationsString);
                                        approvedReservations.add(approvedReservation);
                                    } else {
                                        System.out.println("GET request failed!");
                                    }
                           }
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setReservations(approvedReservations);
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

}