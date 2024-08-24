package com.example.bookingapptim14.models;

public class OwnerReview {

    private Long id;
    private User sender;
    private int rating;

    private String comment;

    private String timestamp;

    private boolean reported;


    public Long getId() {return id;}

    public User getUser() {
        return sender;
    }

    public void setUser(User sender) {
        this.sender = sender;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public boolean isReported() {return reported;}

    public void setReported(boolean isReported) {this.reported=isReported;}

    public void setTimestamp(String sentAt) {
        this.timestamp = sentAt;
    }}



