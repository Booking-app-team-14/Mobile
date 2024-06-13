package com.example.bookingapptim14.models.dtos.ReportsDTO;

import java.time.LocalDateTime;

public class OwnerReviewReportsDTO {

    private Long id;
    private Long reviewId;
    private String reason;
    private ReportStatus status;
    private LocalDateTime sentAt;

    public OwnerReviewReportsDTO() { }

    public OwnerReviewReportsDTO(Long id, Long reviewId, String reason, ReportStatus status, LocalDateTime sentAt) {
        this.id = id;
        this.reviewId = reviewId;
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

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
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
