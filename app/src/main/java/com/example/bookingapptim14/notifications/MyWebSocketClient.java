package com.example.bookingapptim14.notifications;

import android.content.Context;

import com.example.bookingapptim14.SplashScreen;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class MyWebSocketClient extends WebSocketClient {

    private Context context;

    public MyWebSocketClient(URI serverUri, Context context) {
        super(serverUri);
        this.context = context;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        // Connection opened, you can send data now

    }

    @Override
    public void onMessage(String message) {
        // Message received from the server
        // Here you can handle the received message
        // This will now include messages from the /topic/notifications topic

//        // Check if the received message is the same as the user's username
//        // TODO: get the current user's username from the server
//        if (message.equals(currentUser.getUsername())) {
//            // If it is, create a push notification
//            SplashScreen.createNotification(context, "Notification", "You have a new notification!");
//        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // Server is closing the connection
    }

    @Override
    public void onError(Exception ex) {
        // Error occurred
    }
}