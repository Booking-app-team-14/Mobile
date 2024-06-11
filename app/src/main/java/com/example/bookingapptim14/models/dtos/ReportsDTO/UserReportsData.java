package com.example.bookingapptim14.models.dtos.ReportsDTO;

import java.time.LocalDateTime;

public class UserReportsData extends UserReportsDTO {

    private String reportingUserUsername;
    private String reportingUserProfilePictureType;
    private String reportingUserProfilePictureBytes;
    private String reportedUserUsername;
    private String reportedUserProfilePictureType;
    private String reportedUserProfilePictureBytes;

    public UserReportsData() { }

    public UserReportsData(UserReportsDTO dto, String reportingUserUsername, String reportingUserProfilePictureType, String reportingUserProfilePictureBytes, String reportedUserUsername, String reportedUserProfilePictureType, String reportedUserProfilePictureBytes) {
        super(dto.getId(), dto.getReportingUserId(), dto.getReportedUserId(), dto.getReportedUserNumberOfReports(), dto.getDescription(), dto.getSentAt());
        this.reportingUserUsername = reportingUserUsername;
        this.reportingUserProfilePictureType = reportingUserProfilePictureType;
        this.reportingUserProfilePictureBytes = reportingUserProfilePictureBytes;
        this.reportedUserUsername = reportedUserUsername;
        this.reportedUserProfilePictureType = reportedUserProfilePictureType;
        this.reportedUserProfilePictureBytes = reportedUserProfilePictureBytes;
    }

    public UserReportsData(Long id, Long reportingUserId, Long reportedUserId, Integer reportedUserNumberOfReports, String description, LocalDateTime sentAt, String reportingUserUsername, String reportingUserProfilePictureType, String reportingUserProfilePictureBytes, String reportedUserUsername, String reportedUserProfilePictureType, String reportedUserProfilePictureBytes) {
        super(id, reportingUserId, reportedUserId, reportedUserNumberOfReports, description, sentAt);
        this.reportingUserUsername = reportingUserUsername;
        this.reportingUserProfilePictureType = reportingUserProfilePictureType;
        this.reportingUserProfilePictureBytes = reportingUserProfilePictureBytes;
        this.reportedUserUsername = reportedUserUsername;
        this.reportedUserProfilePictureType = reportedUserProfilePictureType;
        this.reportedUserProfilePictureBytes = reportedUserProfilePictureBytes;
    }

    public String getReportingUserUsername() {
        return reportingUserUsername;
    }

    public void setReportingUserUsername(String reportingUserUsername) {
        this.reportingUserUsername = reportingUserUsername;
    }

    public String getReportingUserProfilePictureType() {
        return reportingUserProfilePictureType;
    }

    public void setReportingUserProfilePictureType(String reportingUserProfilePictureType) {
        this.reportingUserProfilePictureType = reportingUserProfilePictureType;
    }

    public String getReportingUserProfilePictureBytes() {
        return reportingUserProfilePictureBytes;
    }

    public void setReportingUserProfilePictureBytes(String reportingUserProfilePictureBytes) {
        this.reportingUserProfilePictureBytes = reportingUserProfilePictureBytes;
    }

    public String getReportedUserUsername() {
        return reportedUserUsername;
    }

    public void setReportedUserUsername(String reportedUserUsername) {
        this.reportedUserUsername = reportedUserUsername;
    }

    public String getReportedUserProfilePictureType() {
        return reportedUserProfilePictureType;
    }

    public void setReportedUserProfilePictureType(String reportedUserProfilePictureType) {
        this.reportedUserProfilePictureType = reportedUserProfilePictureType;
    }

    public String getReportedUserProfilePictureBytes() {
        return reportedUserProfilePictureBytes;
    }

    public void setReportedUserProfilePictureBytes(String reportedUserProfilePictureBytes) {
        this.reportedUserProfilePictureBytes = reportedUserProfilePictureBytes;
    }
}
