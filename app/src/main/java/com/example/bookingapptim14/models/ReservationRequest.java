package com.example.bookingapptim14.models;

import com.example.bookingapptim14.enums.RequestStatus;

import java.time.LocalDate;

public class ReservationRequest {
    private Long guestId;
    private Long accommodationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfGuests ;
    private double totalPrice;
    private RequestStatus requestStatus;

    public ReservationRequest(Long guestId,Long accommodationId,double totalPrice, LocalDate startDate, LocalDate endDate, int numberOfGuests, RequestStatus status){
        this.guestId = guestId;
        this.accommodationId = accommodationId;
        this.totalPrice = totalPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.requestStatus = status;
    }

    public ReservationRequest() {

    }

    private void setGuestIdId(Long guestId){
        this.guestId = guestId;
    }
    private Long getGuestId(){
        return this.guestId;
    }
    private void setAccommodationId(Long accommodationId){
        this.accommodationId = accommodationId;
    }
    private Long getAccommodationId(){
        return this.accommodationId;
    }

    private void setTotalPrice(Double totalPrice){
        this.totalPrice = totalPrice;
    }
    private Double getTotalPrice(){
        return this.totalPrice;
    }

    private void setNumberOfGuests(int numberOfGuests){
        this.numberOfGuests = numberOfGuests;
    }
    private int getNumberOfGuests(){
        return this.numberOfGuests;
    }

    private void setStartDate(LocalDate startDate){
        this.startDate = startDate;
    }
    private LocalDate getStartDate(){
        return this.startDate;
    }

    private void setEndDate(LocalDate endDate){
        this.endDate = endDate;
    }
    private LocalDate getEndDate(){
        return this.endDate;
    }


}
