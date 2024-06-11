package com.example.bookingapptim14.models.dtos.NotificationDTO;

import com.example.bookingapptim14.enums.NotificationType;

public class NotificationDTO {

    private Long id;
    private Long senderId;
    private Long receiverId;
    private String sentAt; // epoch seconds
    private boolean seen;
    private NotificationType type;

    public NotificationDTO() { }

    public NotificationDTO(Long id, Long senderId, Long receiverId, String sentAt, boolean seen, NotificationType type) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.sentAt = sentAt;
        this.seen = seen;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
}
