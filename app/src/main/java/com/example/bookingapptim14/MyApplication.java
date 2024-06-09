package com.example.bookingapptim14;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.example.bookingapptim14.notifications.WebSocketManager;

public class MyApplication extends Application {

    private int activityReferences = 0;
    private boolean isActivityChangingConfigurations = false;

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

            @Override
            public void onActivityStarted(Activity activity) {
                if (++activityReferences == 1 && !isActivityChangingConfigurations) {
                    // App enters foreground
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {}

            @Override
            public void onActivityPaused(Activity activity) {}

            @Override
            public void onActivityStopped(Activity activity) {
                isActivityChangingConfigurations = activity.isChangingConfigurations();
                if (--activityReferences == 0 && !isActivityChangingConfigurations) {
                    // App enters background
                    WebSocketManager manager = WebSocketManager.getInstanceIfExists();
                    if (manager != null) {
                        manager.closeConnection();
                    }
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

            @Override
            public void onActivityDestroyed(Activity activity) {}
        });
    }
}