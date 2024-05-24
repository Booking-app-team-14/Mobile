package com.example.bookingapptim14.guest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.bookingapptim14.Adapters.GuestNotificationsAdapter;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.NotificationDTO.Guest.GuestNotificationsData;

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

// TODO: also use websockets and push notifications for real-time notifications

public class NotificationsFragmentGuest extends Fragment implements GuestNotificationsAdapter.OnNotificationListener {

    private RecyclerView notificationsRecyclerView;
    private GuestNotificationsAdapter guestNotificationsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications_guest, container, false);

        notificationsRecyclerView = view.findViewById(R.id.guestNotificationsRecyclerView);
        guestNotificationsAdapter = new GuestNotificationsAdapter(new ArrayList<>(), this);
        notificationsRecyclerView.setAdapter(guestNotificationsAdapter);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ToggleButton toggleButton = view.findViewById(R.id.guestNotificationsToggleButton);

        // TODO: get the preference from the server
        // GET api/users/{userId}/not-wanted-notifications
        // if empty array, user wants notifications -> toggleButton.setChecked(false);
        // else -> toggleButton.setChecked(true);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TextView notificationsTextView = view.findViewById(R.id.guestNotificationsEnabledTextView);
                    notificationsTextView.setText("Notifications: disabled");
                    // TODO: Implement the logic for disabling notifications
                    // PUT api/users/${userId}/not-wanted-notifications, consumes String (RESERVATION_REQUEST_RESPONSE)
                    // after that, fetchNotifications() again to get the notifications for the type toggled
                } else {
                    TextView notificationsTextView = view.findViewById(R.id.guestNotificationsEnabledTextView);
                    notificationsTextView.setText("Notifications: enabled");

                    // isti kod kao gore, moze da se ekstraktuje u metodu

                    // TODO: Implement the logic for enabling notifications
                    // PUT api/users/${userId}/not-wanted-notifications, consumes String (RESERVATION_REQUEST_RESPONSE)
                    // after that, fetchNotifications() again to get the notifications for the type toggled
                }
            }
        });

        fetchNotifications();

        return view;
    }

    public void fetchNotifications() {
        // TODO: fetch data
        // GET api/notifications/{userId}/true -> GuestNotificationsData

        // For now, use test data
        GlobalData data = GlobalData.getInstance();
        List<GuestNotificationsData> testNotifications = data.getGuestNotifications();
        guestNotificationsAdapter.setNotifications(testNotifications);
    }

    @Override
    public void onNotificationDeleted(GuestNotificationsData notification) {
        // TODO: Handle deletion logic here
        // DELETE api/notifications/{notificationId}
        guestNotificationsAdapter.removeItem(notification);
    }

}