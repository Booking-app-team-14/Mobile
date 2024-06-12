package com.example.bookingapptim14.guest;

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
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.bookingapptim14.Adapters.GuestNotificationsAdapter;
import com.example.bookingapptim14.Adapters.LocalDateDeserializer;
import com.example.bookingapptim14.Adapters.LocalDateTimeDeserializer;
import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.enums.NotificationType;
import com.example.bookingapptim14.models.dtos.NotificationDTO.Guest.GuestNotificationsData;
import com.example.bookingapptim14.models.dtos.NotificationDTO.NotificationDTO;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationGuestData;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ReservationRequestDTO;
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

// GET api/notifications/{userId}/false -> gets NotificationDTOs* (polymorphism) but don't trigger seen
// GET api/notifications/{userId}/true -> gets NotificationDTOs* (polymorphism) and mark notification as seen
// receiver - Guest, sender - Owner (for Guests Notifications)
// receiver - Owner, sender - Guest (for Owners Notifications)

// GET api/users/{userId}/not-wanted-notifications -> get user's not wanted notification types
// if empty array, user wants all notifications
// returns list of notification types (String): RESERVATION_REQUEST_CREATED, RESERVATION_REQUEST_CANCELLED, OWNER_REVIEWED, ACCOMMODATION_REVIEWED <- for the Owner
//                                              RESERVATION_REQUEST_RESPONSE <- for the Guest

// PUT api/users/${userId}/not-wanted-notifications, consumes String (one notification type) -> toggle user's not wanted notification type
// after that, GET api/notifications/{userId}/true again to clear or get the notifications for the type toggled

// true i false parameters are String values even though they are boolean values on the server

// TODO: [SVI] also use websockets and push notifications for real-time notifications

public class NotificationsFragmentGuest extends Fragment implements GuestNotificationsAdapter.OnNotificationListener {

    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximityEventListener;
    private RecyclerView notificationsRecyclerView;
    private GuestNotificationsAdapter guestNotificationsAdapter;
    private String jwtToken;
    private long userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications_guest, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        userId = sharedPreferences.getLong("userId", 0);

        notificationsRecyclerView = view.findViewById(R.id.guestNotificationsRecyclerView);
        guestNotificationsAdapter = new GuestNotificationsAdapter(new ArrayList<>(), this);
        notificationsRecyclerView.setAdapter(guestNotificationsAdapter);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ToggleButton toggleButton = view.findViewById(R.id.guestNotificationsToggleButton);
        CompoundButton.OnCheckedChangeListener toggleListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TextView notificationsTextView = view.findViewById(R.id.guestNotificationsEnabledTextView);
                    notificationsTextView.setText("Notifications: disabled");
                    sendPutRequest();
                } else {
                    TextView notificationsTextView = view.findViewById(R.id.guestNotificationsEnabledTextView);
                    notificationsTextView.setText("Notifications: enabled");
                    sendPutRequest();
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/users/" + userId + "/not-wanted-notifications");
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

                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                                .create();
                        Type listType = new TypeToken<List<NotificationType>>(){}.getType();
                        List<NotificationType> notificationTypes = gson.fromJson(content.toString(), listType);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (notificationTypes.isEmpty()) {
                                    toggleButton.setChecked(false);
                                    TextView notificationsTextView = view.findViewById(R.id.guestNotificationsEnabledTextView);
                                    notificationsTextView.setText("Notifications: enabled");
                                    toggleButton.setOnCheckedChangeListener(toggleListener);
                                } else {
                                    toggleButton.setChecked(true);
                                    TextView notificationsTextView = view.findViewById(R.id.guestNotificationsEnabledTextView);
                                    notificationsTextView.setText("Notifications: disabled");
                                    toggleButton.setOnCheckedChangeListener(toggleListener);
                                }
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

        fetchNotifications();

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
                        notificationsRecyclerView.smoothScrollBy(0, 1200);
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

    private void sendPutRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/users/" + userId + "/not-wanted-notifications");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "text/plain");
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    String notificationType = "RESERVATION_REQUEST_RESPONSE";
                    conn.getOutputStream().write(notificationType.getBytes());

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fetchNotifications();
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
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(proximityEventListener, proximitySensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(proximityEventListener);
    }

    public void fetchNotifications() {
        // GET api/notifications/{userId}/true -> GuestNotificationsData
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/notifications/" + userId + "/true");
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

                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                                .create();
                        Type listType = new TypeToken<List<GuestNotificationsData>>(){}.getType();
                        List<GuestNotificationsData> notifications = gson.fromJson(content.toString(), listType);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                guestNotificationsAdapter.setNotifications(notifications);
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
    public void onNotificationDeleted(GuestNotificationsData notification) {
        // DELETE api/notifications/{notificationId}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/notifications/" + notification.getId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("DELETE");
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                guestNotificationsAdapter.removeItem(notification);
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