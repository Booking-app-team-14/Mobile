package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.guest.GuestNotificationsViewHolder;
import com.example.bookingapptim14.host.HostNotificationsViewHolder;
import com.example.bookingapptim14.models.dtos.NotificationDTO.Guest.GuestNotificationsData;
import com.example.bookingapptim14.models.dtos.NotificationDTO.Host.HostNotificationsData;

import java.util.List;

public class HostNotificationsAdapter extends RecyclerView.Adapter<HostNotificationsViewHolder>{

    private List<HostNotificationsData> notifications;
    private OnNotificationListener onNotificationListener;

    public HostNotificationsAdapter(List<HostNotificationsData> notifications, OnNotificationListener onNotificationListener) {
        this.notifications = notifications;
        this.onNotificationListener = onNotificationListener;
    }

    @NonNull
    @Override
    public HostNotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_host_notifications, parent, false);
        return new HostNotificationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HostNotificationsViewHolder holder, int position) {
        HostNotificationsData notification = notifications.get(position);
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

    public void setNotifications(List<HostNotificationsData> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    public void removeItem(HostNotificationsData notification) {
        int position = notifications.indexOf(notification);
        if (position == -1) {
            return;
        }
        notifications.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnNotificationListener {
        void onNotificationDeleted(HostNotificationsData notification);
    }

}
