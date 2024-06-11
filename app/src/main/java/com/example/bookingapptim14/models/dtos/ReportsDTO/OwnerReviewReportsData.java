package com.example.bookingapptim14.models.dtos.ReportsDTO;

import java.time.LocalDateTime;

public class OwnerReviewReportsData extends OwnerReviewReportsDTO {

    private OwnerReviewDTO ownerReview;
    private String senderUsername;
    private String senderProfilePictureBytes;
    private String recipientUsername;
    private String recipientProfilePictureBytes;
    private String ratingBeforeString;

    public OwnerReviewReportsData() { }

    public OwnerReviewReportsData(OwnerReviewReportsDTO dto, OwnerReviewDTO ownerReview, String senderUsername, String senderProfilePictureBytes, String recipientUsername, String recipientProfilePictureBytes, String ratingBeforeString) {
        super(dto.getId(), dto.getReviewId(), dto.getReason(), dto.getStatus(), dto.getSentAt());
        this.ownerReview = ownerReview;
        this.senderUsername = senderUsername;
        this.senderProfilePictureBytes = senderProfilePictureBytes;
        this.recipientUsername = recipientUsername;
        this.recipientProfilePictureBytes = recipientProfilePictureBytes;
        this.ratingBeforeString = ratingBeforeString;
    }

    public OwnerReviewReportsData(Long id, Long reviewId, String reason, ReportStatus status, LocalDateTime sentAt, OwnerReviewDTO ownerReview, String senderUsername, String senderProfilePictureBytes, String recipientUsername, String recipientProfilePictureBytes, String ratingBeforeString) {
        super(id, reviewId, reason, status, sentAt);
        this.ownerReview = ownerReview;
        this.senderUsername = senderUsername;
        this.senderProfilePictureBytes = senderProfilePictureBytes;
        this.recipientUsername = recipientUsername;
        this.recipientProfilePictureBytes = recipientProfilePictureBytes;
        this.ratingBeforeString = ratingBeforeString;
    }

    public OwnerReviewDTO getOwnerReview() {
        return ownerReview;
    }

    public void setOwnerReview(OwnerReviewDTO ownerReview) {
        this.ownerReview = ownerReview;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getSenderProfilePictureBytes() {
        return senderProfilePictureBytes;
    }

    public void setSenderProfilePictureBytes(String senderProfilePictureBytes) {
        this.senderProfilePictureBytes = senderProfilePictureBytes;
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    public String getRecipientProfilePictureBytes() {
        return recipientProfilePictureBytes;
    }

    public void setRecipientProfilePictureBytes(String recipientProfilePictureBytes) {
        this.recipientProfilePictureBytes = recipientProfilePictureBytes;
    }

    public String getRatingBeforeString() {
        return ratingBeforeString;
    }

    public void setRatingBeforeString(String ratingBeforeString) {
        this.ratingBeforeString = ratingBeforeString;
    }
}
