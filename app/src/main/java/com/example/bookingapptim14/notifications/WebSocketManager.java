package com.example.bookingapptim14.notifications;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.SplashScreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.LifecycleEvent;

public class WebSocketManager {
    private static WebSocketManager instance;
    private StompClient mStompClient;
    private Context context;

    private WebSocketManager(Context context) {
        this.context = context;
        String url = "ws://localhost:8080/socket";
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url);

        mStompClient.lifecycle()
                .subscribe(lifecycleEvent -> {
                    if (lifecycleEvent.getType() == LifecycleEvent.Type.OPENED) {
                        subscribeToTopic("/topic/notifications");
                    }
                });

        mStompClient.connect();
    }

    private void subscribeToTopic(String topic) {
        mStompClient.topic(topic)
                .subscribe(stompMessage -> {
                    // Handle received message
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                    String jwtToken = sharedPreferences.getString("jwtToken", "");

                    // GET /api/users/token/{token} -> Long
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(BuildConfig.IP_ADDR + "/api//users/username/token/" + jwtToken);
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

                                    String username = content.toString();

                                    if (stompMessage.equals(username)) {
                                        SplashScreen.createNotification(context, "Notification", "You have a new notification!");
                                    }
                                } else {
                                    System.out.println("GET request failed!");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                });
    }

    public static synchronized WebSocketManager getInstance(Context context) {
        if (instance == null) {
            instance = new WebSocketManager(context);
        }
        return instance;
    }

    public static WebSocketManager getInstanceIfExists() {
        return instance;
    }

    public void closeConnection() {
        if (mStompClient != null) {
            mStompClient.disconnect();
            mStompClient = null;
        }
    }
}