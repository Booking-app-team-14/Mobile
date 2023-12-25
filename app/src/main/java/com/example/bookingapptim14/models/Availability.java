package com.example.bookingapptim14.models;

import java.time.LocalDate;

public class Availability {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double specialPrice;
    private Long accommodation_Id;

    public Availability(Long id, LocalDate start, LocalDate end, Double specialPrice, Long accommodation_Id) {
        this.id = id;
        this.startDate = start;
        this.endDate = end;
        this.specialPrice = specialPrice;
        this.accommodation_Id=accommodation_Id;
    }
    private void setId(Long id){
        this.id = id;
    }
    private Long getId(){
        return this.id;
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

    private void setSpecialPrice(Double specialPrice){
        this.specialPrice = specialPrice;
    }
    private Double getSpecialPrice(){
        return this.specialPrice;
    }
    private void setAccommodation_Id(Long id){
        this.id = id;
    }
    private Long getAccommodation_Id(){
        return this.id;
    }
}