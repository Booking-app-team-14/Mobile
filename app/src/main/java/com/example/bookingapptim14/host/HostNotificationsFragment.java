package com.example.bookingapptim14.host;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

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
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.Adapters.HostNotificationsAdapter;
import com.example.bookingapptim14.Adapters.LocalDateDeserializer;
import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.enums.NotificationType;
import com.example.bookingapptim14.models.dtos.NotificationDTO.Host.HostNotificationsData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HostNotificationsFragment extends Fragment implements HostNotificationsAdapter.OnNotificationListener {



    private RecyclerView notificationsRecyclerView;
    private HostNotificationsAdapter guestNotificationsAdapter;
    private String jwtToken;
    private long userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications_host, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        userId = sharedPreferences.getLong("userId", 0);

        notificationsRecyclerView = view.findViewById(R.id.hostNotificationsRecyclerView);
        guestNotificationsAdapter = new HostNotificationsAdapter(new ArrayList<>(), this);
        notificationsRecyclerView.setAdapter(guestNotificationsAdapter);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ToggleButton toggleButton = view.findViewById(R.id.hostNotificationsToggleButton);

        new Thread(() -> {
            try {
                URL url = new URL(BuildConfig.IP_ADDR + "/api/users/" + userId + "/not-wanted-notifications");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                int responseCode = conn.getResponseCode();
                Log.d("HostNotificationsFragment", "GET request response code: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder content = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    conn.disconnect();

                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                            .create();
                    Type listType = new TypeToken<List<NotificationType>>(){}.getType();
                    List<NotificationType> notificationTypes = gson.fromJson(content.toString(), listType);

                    Log.d("HostNotificationsFragment", "Fetched notification types: " + notificationTypes);

                    getActivity().runOnUiThread(() -> {
                        if (notificationTypes.isEmpty()) {
                            toggleButton.setChecked(false);
                        } else {
                            toggleButton.setChecked(true);
                        }
                    });
                } else {
                    Log.e("HostNotificationsFragment", "GET request failed with response code: " + responseCode);
                }
            } catch (Exception e) {
                Log.e("HostNotificationsFragment", "Exception during GET request", e);
            }
        }).start();

        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            TextView notificationsTextView = view.findViewById(R.id.hostNotificationsEnabledTextView);
            notificationsTextView.setText(isChecked ? "Notifications: disabled" : "Notifications: enabled");
            sendPutRequest();
        });

        fetchNotifications();
        return view;
    }

    private void sendPutRequest() {
        new Thread(() -> {
            try {
                URL url = new URL(BuildConfig.IP_ADDR + "/api/users/" + userId + "/not-wanted-notifications");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "text/plain");
                conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                String notificationType = "RESERVATION_REQUEST_CREATED";
                conn.getOutputStream().write(notificationType.getBytes());

                int responseCode = conn.getResponseCode();
                Log.d("HostNotificationsFragment", "PUT request response code: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    getActivity().runOnUiThread(this::fetchNotifications);
                } else {
                    Log.e("HostNotificationsFragment", "PUT request failed with response code: " + responseCode);
                }
            } catch (Exception e) {
                Log.e("HostNotificationsFragment", "Exception during PUT request", e);
            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void fetchNotifications() {
        new Thread(() -> {
            try {
                URL url = new URL(BuildConfig.IP_ADDR + "/api/notifications/" + userId + "/true");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                int responseCode = conn.getResponseCode();
                Log.d("HostNotificationsFragment", "GET notifications response code: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder content = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    conn.disconnect();

                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                            .create();
                    Type listType = new TypeToken<List<HostNotificationsData>>(){}.getType();
                    List<HostNotificationsData> notifications = gson.fromJson(content.toString(), listType);
                    Log.d("HostNotificationsFragment", "Fetched " + notifications.size() + " notifications");

                    getActivity().runOnUiThread(() -> {
                        guestNotificationsAdapter.setNotifications(notifications);
                        guestNotificationsAdapter.notifyDataSetChanged();
                        Log.d("HostNotificationsFragment", "UI updated with fetched notifications");
                    });
                } else {
                    Log.e("HostNotificationsFragment", "GET notifications request failed with response code: " + responseCode);
                }
            } catch (Exception e) {
                Log.e("HostNotificationsFragment", "Exception during GET notifications request", e);
            }
        }).start();
    }

    @Override
    public void onNotificationDeleted(HostNotificationsData notification) {
        new Thread(() -> {
            try {
                URL url = new URL(BuildConfig.IP_ADDR + "/api/notifications/" + notification.getId());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");
                conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                int responseCode = conn.getResponseCode();
                Log.d("HostNotificationsFragment", "DELETE notification response code: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    getActivity().runOnUiThread(() -> {
                        guestNotificationsAdapter.removeItem(notification);
                        Log.d("HostNotificationsFragment", "Notification deleted and UI updated");
                    });
                } else {
                    Log.e("HostNotificationsFragment", "DELETE request failed with response code: " + responseCode);
                }
            } catch (Exception e) {
                Log.e("HostNotificationsFragment", "Exception during DELETE request", e);
            }
        }).start();
    }
}
