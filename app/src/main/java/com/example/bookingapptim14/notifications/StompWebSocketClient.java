package com.example.bookingapptim14.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.SplashScreen;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class StompWebSocketClient extends WebSocketClient {

    private Context context;

    public StompWebSocketClient(String url, Context context) throws URISyntaxException {
        super(new URI(url));
        this.context = context;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d("STOMP", "Connected");
        send("SUBSCRIBE\nid:sub-0\ndestination:/topic/notifications\n\n\u0000");
    }

    @Override
    public void onMessage(String message) {
        // Handle incoming message

        Log.d("STOMP", message);

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

                        if (message.equals(username)) {
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
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // Handle close
    }

    @Override
    public void onError(Exception ex) {
        // Handle error
        Log.d("STOMP", ex.getMessage());
    }
}