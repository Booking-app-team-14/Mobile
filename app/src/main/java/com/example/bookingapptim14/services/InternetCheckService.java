package com.example.bookingapptim14.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.bookingapptim14.R;

public class InternetCheckService extends Service {
    private static final int INTERVAL = 60000; // 60 sekundi
    private Handler handler = new Handler();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.postDelayed(checkInternetRunnable, INTERVAL);
        return START_STICKY;
    }

    private Runnable checkInternetRunnable = new Runnable() {
        @Override
        public void run() {

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {

            } else {

                    Intent connectivityIntent = new Intent("com.example.bookingapptim14.CONNECTIVITY_CHANGE");
                    sendBroadcast(connectivityIntent);
            }
            handler.postDelayed(this, INTERVAL);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(checkInternetRunnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
