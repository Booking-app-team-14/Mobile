package com.example.bookingapptim14.models.dtos.ApproveReviewsDTO;

import java.time.LocalDateTime;

public class ApproveOwnerReviewsData extends ApproveOwnerReviewsDTO {

    private String guestUsername;
    private String guestProfilePictureType;
    private String guestProfilePictureBytes;
    private String ownerUsername;
    private String ownerProfilePictureType;
    private String ownerProfilePictureBytes;
    private String ratingBefore;

    public ApproveOwnerReviewsData() { }

    public ApproveOwnerReviewsData(ApproveOwnerReviewsDTO dto, String guestUsername, String guestProfilePictureType, String guestProfilePictureBytes, String ownerUsername, String ownerProfilePictureType, String ownerProfilePictureBytes, String ratingBefore) {
        super(dto.getId(), dto.getComment(), dto.getRating(), dto.getTimestamp(), dto.isReported(), dto.isApproved(), dto.getSenderId(), dto.getRecipientId());
        this.guestUsername = guestUsername;
        this.guestProfilePictureType = guestProfilePictureType;
        this.guestProfilePictureBytes = guestProfilePictureBytes;
        this.ownerUsername = ownerUsername;
        this.ownerProfilePictureType = ownerProfilePictureType;
        this.ownerProfilePictureBytes = ownerProfilePictureBytes;
        this.ratingBefore = ratingBefore;
    }

    public ApproveOwnerReviewsData(Long id, String comment, int rating, LocalDateTime timestamp, boolean reported, boolean approved, Long senderId, Long recipientId, String guestUsername, String guestProfilePictureType, String guestProfilePictureBytes, String ownerUsername, String ownerProfilePictureType, String ownerProfilePictureBytes, String ratingBefore) {
        super(id, comment, rating, timestamp, reported, approved, senderId, recipientId);
        this.guestUsername = guestUsername;
        this.guestProfilePictureType = guestProfilePictureType;
        this.guestProfilePictureBytes = guestProfilePictureBytes;
        this.ownerUsername = ownerUsername;
        this.ownerProfilePictureType = ownerProfilePictureType;
        this.ownerProfilePictureBytes = ownerProfilePictureBytes;
        this.ratingBefore = ratingBefore;
    }

    public String getGuestUsername() {
        return guestUsername;
    }

    public void setGuestUsername(String guestUsername) {
        this.guestUsername = guestUsername;
    }

    public String getGuestProfilePictureType() {
        return guestProfilePictureType;
    }

    public void setGuestProfilePictureType(String guestProfilePictureType) {
        this.guestProfilePictureType = guestProfilePictureType;
    }

    public String getGuestProfilePictureBytes() {
        return guestProfilePictureBytes;
    }

    public void setGuestProfilePictureBytes(String guestProfilePictureBytes) {
        this.guestProfilePictureBytes = guestProfilePictureBytes;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getOwnerProfilePictureType() {
        return ownerProfilePictureType;
    }

    public void setOwnerProfilePictureType(String ownerProfilePictureType) {
        this.ownerProfilePictureType = ownerProfilePictureType;
    }

    public String getOwnerProfilePictureBytes() {
        return ownerProfilePictureBytes;
    }

    public void setOwnerProfilePictureBytes(String ownerProfilePictureBytes) {
        this.ownerProfilePictureBytes = ownerProfilePictureBytes;
    }

    public String getRatingBefore() {
        return ratingBefore;
    }

    public void setRatingBefore(String ratingBefore) {
        this.ratingBefore = ratingBefore;
    }
}
