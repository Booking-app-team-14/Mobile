package com.example.bookingapptim14.models;

public class ReservationRequest {
    private int userProfileImage; // Resource ID for user profile image
    private String userName;
    private int apartmentImage; // Resource ID for apartment image
    private String apartmentName;
    private String apartmentType;
    private String startDate;
    private String endDate;
    private int numGuests;

    // Constructor
    public ReservationRequest(int userProfileImage, String userName, int apartmentImage,
                              String apartmentName, String apartmentType,
                              String startDate, String endDate, int numGuests) {
        this.userProfileImage = userProfileImage;
        this.userName = userName;
        this.apartmentImage = apartmentImage;
        this.apartmentName = apartmentName;
        this.apartmentType = apartmentType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numGuests = numGuests;
    }

    // Getters and Setters
    public int getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(int userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getApartmentImage() {
        return apartmentImage;
    }

    public void setApartmentImage(int apartmentImage) {
        this.apartmentImage = apartmentImage;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(String apartmentType) {
        this.apartmentType = apartmentType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getNumGuests() {
        return numGuests;
    }

    public void setNumGuests(int numGuests) {
        this.numGuests = numGuests;
    }
}
