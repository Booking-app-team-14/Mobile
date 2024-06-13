package com.example.bookingapptim14.models.dtos.AccommodationDTO;

import java.time.LocalDate;

public class UpdateAvailabilityDTO {

    private LocalDate startDate;
    private LocalDate endDate;
    private Double specialPrice;

    public UpdateAvailabilityDTO() { }

    public UpdateAvailabilityDTO(LocalDate startDate, LocalDate endDate, Double specialPrice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.specialPrice = specialPrice;
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

    public Double getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(Double specialPrice) {
        this.specialPrice = specialPrice;
    }
}
