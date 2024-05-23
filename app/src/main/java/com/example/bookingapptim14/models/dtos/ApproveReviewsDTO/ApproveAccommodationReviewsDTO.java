package com.example.bookingapptim14.models.dtos.ApproveReviewsDTO;

import java.time.LocalDateTime;

public class ApproveAccommodationReviewsDTO {

    private Long id;
    private Long accommodationId;
    private Long userId;
    private Integer rating;
    private String comment;
    private ReviewStatus status;
    private LocalDateTime sentAt;

    public ApproveAccommodationReviewsDTO() { }

    public ApproveAccommodationReviewsDTO(Long id, Long accommodationId, Long userId, Integer rating, String comment, ReviewStatus status, LocalDateTime sentAt) {
        this.id = id;
        this.accommodationId = accommodationId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.status = status;
        this.sentAt = sentAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}
