package com.example.bookingapptim14.models.dtos.ReportsDTO;

import java.time.LocalDateTime;

public class UserReportsDTO {

    private Long id;
    private Long reportingUserId;
    private Long reportedUserId;
    private Integer reportedUserNumberOfReports;
    private String description;
    private LocalDateTime sentAt;

    public UserReportsDTO() { }

    public UserReportsDTO(Long id, Long reportingUserId, Long reportedUserId, Integer reportedUserNumberOfReports, String description, LocalDateTime sentAt) {
        this.id = id;
        this.reportingUserId = reportingUserId;
        this.reportedUserId = reportedUserId;
        this.reportedUserNumberOfReports = reportedUserNumberOfReports;
        this.description = description;
        this.sentAt = sentAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReportingUserId() {
        return reportingUserId;
    }

    public void setReportingUserId(Long reportingUserId) {
        this.reportingUserId = reportingUserId;
    }

    public Long getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(Long reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public Integer getReportedUserNumberOfReports() {
        return reportedUserNumberOfReports;
    }

    public void setReportedUserNumberOfReports(Integer reportedUserNumberOfReports) {
        this.reportedUserNumberOfReports = reportedUserNumberOfReports;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}
