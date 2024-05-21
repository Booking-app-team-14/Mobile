package com.example.bookingapptim14.models.dtos.ReservationRequestDTO;

import java.time.LocalDate;
import java.util.Date;

public class ApprovedReservationData extends ReservationRequestDTO {

    private String date;
    private String accommodationName;
    private String accommodationType;
    private String guestUsername;
    private String numberOfCancellations;

    public ApprovedReservationData() { }

    public ApprovedReservationData(Long id, Long guestId, Long accommodationId, LocalDate startDate, LocalDate endDate, int numberOfGuests, double totalPrice, String requestStatus, String name, String type, String userUsername, String dateRequested, int stars, String mainPictureBytes, String imageType, String userProfilePictureBytes, String userImageType, String date, String accommodationName, String accommodationType, String guestUsername, String numberOfCancellations) {
        super(id, guestId, accommodationId, startDate, endDate, numberOfGuests, totalPrice, requestStatus, name, type, userUsername, dateRequested, stars, mainPictureBytes, imageType, userProfilePictureBytes, userImageType);
        this.date = date;
        this.accommodationName = accommodationName;
        this.accommodationType = accommodationType;
        this.guestUsername = guestUsername;
        this.numberOfCancellations = numberOfCancellations;
    }

    public ApprovedReservationData(ReservationRequestDTO dto, String date, String accommodationName, String accommodationType, String guestUsername, String numberOfCancellations) {
        super(dto.getId(), dto.getGuestId(), dto.getAccommodationId(), dto.getStartDate(), dto.getEndDate(), dto.getNumberOfGuests(), dto.getTotalPrice(), dto.getRequestStatus(), dto.getName(), dto.getType(), dto.getUserUsername(), dto.getDateRequested(), dto.getStars(), dto.getMainPictureBytes(), dto.getImageType(), dto.getUserProfilePictureBytes(), dto.getUserImageType());
        this.date = date;
        this.accommodationName = accommodationName;
        this.accommodationType = accommodationType;
        this.guestUsername = guestUsername;
        this.numberOfCancellations = numberOfCancellations;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getGuestUsername() {
        return guestUsername;
    }

    public void setGuestUsername(String guestUsername) {
        this.guestUsername = guestUsername;
    }

    public String getNumberOfCancellations() {
        return numberOfCancellations;
    }

    public void setNumberOfCancellations(String numberOfCancellations) {
        this.numberOfCancellations = numberOfCancellations;
    }

}
