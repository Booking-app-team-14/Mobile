package com.example.bookingapptim14.models.dtos.NotificationDTO.Guest;

import com.example.bookingapptim14.enums.RequestStatus;

import java.time.LocalDate;

public class ReservationRequestDTO {

    private Long id;
    private Long guestId;
    private Long accommodationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfGuests;
    private double totalPrice;
    private RequestStatus requestStatus;
    private String name;
    private String type;
    private String userImageType;
    private String userProfilePictureBytes;
    private String userUsername;
    private String dateRequested; // date requested, (epoch seconds)
    private int stars;
    private String imageType; // accommodation main picture type (jpg, png, etc.)
    private String mainPictureBytes;

    public ReservationRequestDTO() { }

    public ReservationRequestDTO(Long id, Long guestId, Long accommodationId, LocalDate startDate, LocalDate endDate, int numberOfGuests, double totalPrice, RequestStatus requestStatus, String name, String type, String userImageType, String userProfilePictureBytes, String userUsername, String dateRequested, int stars, String imageType, String mainPictureBytes) {
        this.id = id;
        this.guestId = guestId;
        this.accommodationId = accommodationId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = totalPrice;
        this.requestStatus = requestStatus;
        this.name = name;
        this.type = type;
        this.userImageType = userImageType;
        this.userProfilePictureBytes = userProfilePictureBytes;
        this.userUsername = userUsername;
        this.dateRequested = dateRequested;
        this.stars = stars;
        this.imageType = imageType;
        this.mainPictureBytes = mainPictureBytes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
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

    public String getUserImageType() {
        return userImageType;
    }

    public void setUserImageType(String userImageType) {
        this.userImageType = userImageType;
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

    public String getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(String dateRequested) {
        this.dateRequested = dateRequested;
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
