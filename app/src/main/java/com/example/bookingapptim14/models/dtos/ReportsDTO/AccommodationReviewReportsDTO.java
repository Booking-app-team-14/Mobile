package com.example.bookingapptim14.models.dtos.ReportsDTO;

import java.time.LocalDateTime;

public class AccommodationReviewReportsDTO {

    private Long id;
    private Long accommodationReviewId;
    private String reason;
    private ReportStatus status;
    private LocalDateTime sentAt;

    public AccommodationReviewReportsDTO() { }

    public AccommodationReviewReportsDTO(Long id, Long accommodationReviewId, String reason, ReportStatus status, LocalDateTime sentAt) {
        this.id = id;
        this.accommodationReviewId = accommodationReviewId;
        this.reason = reason;
        this.status = status;
        this.sentAt = sentAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccommodationReviewId() {
        return accommodationReviewId;
    }

    public void setAccommodationReviewId(Long accommodationReviewId) {
        this.accommodationReviewId = accommodationReviewId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}
