package com.example.bookingapptim14.models.dtos.ApproveReviewsDTO;

import java.time.LocalDateTime;

public class ApproveAccommodationReviewsData extends ApproveAccommodationReviewsDTO {

    private String guestUsername;
    private String guestProfilePictureType;
    private String guestProfilePictureBytes;
    private String accommodationName;
    private String accommodationType;
    private String accommodationPictureBytes;
    private String ratingString;

    public ApproveAccommodationReviewsData() {}

    public ApproveAccommodationReviewsData(ApproveAccommodationReviewsDTO dto, String guestUsername, String guestProfilePictureType, String guestProfilePictureBytes, String accommodationName, String accommodationType, String accommodationPictureBytes, String ratingString) {
        super(dto.getId(), dto.getAccommodationId(), dto.getUserId(), dto.getRating(), dto.getComment(), dto.getStatus(), dto.getSentAt());
        this.guestUsername = guestUsername;
        this.guestProfilePictureType = guestProfilePictureType;
        this.guestProfilePictureBytes = guestProfilePictureBytes;
        this.accommodationName = accommodationName;
        this.accommodationType = accommodationType;
        this.accommodationPictureBytes = accommodationPictureBytes;
        this.ratingString = ratingString;
    }

    public ApproveAccommodationReviewsData(Long id, Long accommodationId, Long userId, Integer rating, String comment, ReviewStatus status, LocalDateTime sentAt, String guestUsername, String guestProfilePictureType, String guestProfilePictureBytes, String accommodationName, String accommodationType, String accommodationPictureBytes, String ratingString) {
        super(id, accommodationId, userId, rating, comment, status, sentAt);
        this.guestUsername = guestUsername;
        this.guestProfilePictureType = guestProfilePictureType;
        this.guestProfilePictureBytes = guestProfilePictureBytes;
        this.accommodationName = accommodationName;
        this.accommodationType = accommodationType;
        this.accommodationPictureBytes = accommodationPictureBytes;
        this.ratingString = ratingString;
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

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public String getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(String accommodationType) {
        this.accommodationType = accommodationType;
    }

    public String getAccommodationPictureBytes() {
        return accommodationPictureBytes;
    }

    public void setAccommodationPictureBytes(String accommodationPictureBytes) {
        this.accommodationPictureBytes = accommodationPictureBytes;
    }

    public String getRatingString() {
        return ratingString;
    }

    public void setRatingString(String ratingString) {
        this.ratingString = ratingString;
    }
}
