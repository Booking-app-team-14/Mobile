package com.example.bookingapptim14.models.dtos.AccommodationDTO;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class MonthlyAccommodationReport {

    private String month;

    private int numberOfReservations;

    private double totalProfit;

    public MonthlyAccommodationReport(String month, int numberOfReservations, double totalProfit) {
        this.month = month;
        this.numberOfReservations = numberOfReservations;
        this.totalProfit = totalProfit;
    }

public int getNumberOfReservations(){
        return this.numberOfReservations;
}
public double getTotalProfit(){
        return this.totalProfit;
}

public String getMonth(){
        return this.month;
}



}
