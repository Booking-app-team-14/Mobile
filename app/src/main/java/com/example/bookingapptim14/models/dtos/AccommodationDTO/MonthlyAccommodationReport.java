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

    public Map<String, MonthlyAccommodationReport> getMonthlyReportMap() {
        Map<String, MonthlyAccommodationReport> monthlyReportMap = new LinkedHashMap<>();
        Integer year =2024;
        for (int month = 1; month <= 12; month++) {
            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);

            int numberOfReservations = 10;
            double totalProfit = 233.3;

            MonthlyAccommodationReport monthlyReportDTO = new MonthlyAccommodationReport(startDate.getMonth().toString(), numberOfReservations, totalProfit);
            monthlyReportMap.put(startDate.getMonth().toString(), monthlyReportDTO);
        }
        return monthlyReportMap;
    }



}
