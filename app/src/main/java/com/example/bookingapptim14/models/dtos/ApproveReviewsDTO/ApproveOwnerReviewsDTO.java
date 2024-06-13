package com.example.bookingapptim14.models.dtos.ApproveReviewsDTO;

import java.time.LocalDateTime;

public class ApproveOwnerReviewsDTO {

    private Long id;
    private String comment;
    private int rating;
    private LocalDateTime timestamp;
    private boolean reported;
    private boolean approved;
    private Long senderId;
    private Long recipientId;

    public ApproveOwnerReviewsDTO() { }

    public ApproveOwnerReviewsDTO(Long id, String comment, int rating, LocalDateTime timestamp, boolean reported, boolean approved, Long senderId, Long recipientId) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.timestamp = timestamp;
        this.reported = reported;
        this.approved = approved;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }
}
