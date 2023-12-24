package com.example.bookingapptim14.broadcastReceivers;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.PendingIntent;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.bookingapptim14.R;

public class ConnectivityReceiver extends BroadcastReceiver {

    private static final int MY_PERMISSIONS_REQUEST_NOTIFICATION = 121;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals("com.example.bookingapptim14.CONNECTIVITY_CHANGE")) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo == null || !networkInfo.isConnected()) {
                sendNotification(context);
            }
        }
    }

    private void sendNotification(Context context) {

        Intent settingsIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        PendingIntent settingsPendingIntent = PendingIntent.getActivity(context, 0, settingsIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = "my_channel_id";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle("No internet Connection")
                .setContentText("Please turn on Internet so you can continue with your work.")
                .setContentIntent(settingsPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NOTIFICATION_POLICY)
                != PackageManager.PERMISSION_GRANTED) {

        } else {
            notificationManager.notify(123, notificationBuilder.build());
        }
    }
}
