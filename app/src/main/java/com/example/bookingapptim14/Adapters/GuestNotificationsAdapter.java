package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.guest.GuestNotificationsViewHolder;
import com.example.bookingapptim14.models.dtos.NotificationDTO.Guest.GuestNotificationsData;

import java.util.List;

public class GuestNotificationsAdapter extends RecyclerView.Adapter<GuestNotificationsViewHolder>{

    private List<GuestNotificationsData> notifications;
    private OnNotificationListener onNotificationListener;

    public GuestNotificationsAdapter(List<GuestNotificationsData> notifications, OnNotificationListener onNotificationListener) {
        this.notifications = notifications;
        this.onNotificationListener = onNotificationListener;
    }

    @NonNull
    @Override
    public GuestNotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest_notifications, parent, false);
        return new GuestNotificationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestNotificationsViewHolder holder, int position) {
        GuestNotificationsData notification = notifications.get(position);
        holder.bind(notification);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNotificationListener.onNotificationDeleted(notification);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void setNotifications(List<GuestNotificationsData> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    public void removeItem(GuestNotificationsData notification) {
        int position = notifications.indexOf(notification);
        if (position == -1) {
            return;
        }
        notifications.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnNotificationListener {
        void onNotificationDeleted(GuestNotificationsData notification);
    }

}
