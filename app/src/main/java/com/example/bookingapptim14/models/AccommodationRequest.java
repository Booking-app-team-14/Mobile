package com.example.bookingapptim14.models;

public class AccommodationRequest {

    private Long accommodationId;
    private String name; // accommodation name
    private String type; // accommodation type
    private String ownerImageType; // owner profile picture type (jpg, png, etc.)
    private String ownerProfilePictureBytes;
    private String ownerUsername;
    private String dateRequested; // date requested, (epoch seconds)
    private String requestType; // request type (new, updated)
    private String message;
    private int stars;
    private String imageType; // accommodation main picture type (jpg, png, etc.)
    private String mainPictureBytes;

    public AccommodationRequest(Long accommodationId, String name, String type, String ownerImageType, String ownerProfilePictureBytes, String ownerUsername, String dateRequested, String requestType, String message, int stars, String imageType, String mainPictureBytes) {
        this.accommodationId = accommodationId;
        this.name = name;
        this.type = type;
        this.ownerImageType = ownerImageType;
        this.ownerProfilePictureBytes = ownerProfilePictureBytes;
        this.ownerUsername = ownerUsername;
        this.dateRequested = dateRequested;
        this.requestType = requestType;
        this.message = message;
        this.stars = stars;
        this.imageType = imageType;
        this.mainPictureBytes = mainPictureBytes;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwnerImageType() {
        return ownerImageType;
    }

    public void setOwnerImageType(String ownerImageType) {
        this.ownerImageType = ownerImageType;
    }

    public String getOwnerProfilePictureBytes() {
        return ownerProfilePictureBytes;
    }

    public void setOwnerProfilePictureBytes(String ownerProfilePictureBytes) {
        this.ownerProfilePictureBytes = ownerProfilePictureBytes;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(String dateRequested) {
        this.dateRequested = dateRequested;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getMainPictureBytes() {
        return mainPictureBytes;
    }

    public void setMainPictureBytes(String mainPictureBytes) {
        this.mainPictureBytes = mainPictureBytes;
    }

}
