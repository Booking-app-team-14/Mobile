package com.example.bookingapptim14.models.dtos.ReportsDTO;

import java.time.LocalDateTime;

public class AccommodationReviewReportsData extends AccommodationReviewReportsDTO {

    private AccommodationReviewDTO accommodationReviewDTO;
    private String userProfilePictureBytes;
    private String userUsername;
    private String accommodationName;
    private String accommodationType;
    private String accommodationImageBytes;

    public AccommodationReviewReportsData() { }

    public AccommodationReviewReportsData(AccommodationReviewReportsDTO dto, AccommodationReviewDTO accommodationReviewDTO, String userProfilePictureBytes, String userUsername, String accommodationName, String accommodationType, String accommodationImageBytes) {
        super(dto.getId(), dto.getAccommodationReviewId(), dto.getReason(), dto.getStatus(), dto.getSentAt());
        this.accommodationReviewDTO = accommodationReviewDTO;
        this.userProfilePictureBytes = userProfilePictureBytes;
        this.userUsername = userUsername;
        this.accommodationName = accommodationName;
        this.accommodationType = accommodationType;
        this.accommodationImageBytes = accommodationImageBytes;
    }

    public AccommodationReviewReportsData(Long id, Long accommodationReviewId, String reason, ReportStatus status, LocalDateTime sentAt, AccommodationReviewDTO accommodationReviewDTO, String userProfilePictureBytes, String userUsername, String accommodationName, String accommodationType, String accommodationImageBytes) {
        super(id, accommodationReviewId, reason, status, sentAt);
        this.accommodationReviewDTO = accommodationReviewDTO;
        this.userProfilePictureBytes = userProfilePictureBytes;
        this.userUsername = userUsername;
        this.accommodationName = accommodationName;
        this.accommodationType = accommodationType;
        this.accommodationImageBytes = accommodationImageBytes;
    }

    public AccommodationReviewDTO getAccommodationReviewDTO() {
        return accommodationReviewDTO;
    }

    public void setAccommodationReviewDTO(AccommodationReviewDTO accommodationReviewDTO) {
        this.accommodationReviewDTO = accommodationReviewDTO;
    }

    public String getUserProfilePictureBytes() {
        return userProfilePictureBytes;
    }

    public void setUserProfilePictureBytes(String userProfilePictureBytes) {
        this.userProfilePictureBytes = userProfilePictureBytes;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
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

    public String getAccommodationImageBytes() {
        return accommodationImageBytes;
    }

    public void setAccommodationImageBytes(String accommodationImageBytes) {
        this.accommodationImageBytes = accommodationImageBytes;
    }
}
