package com.example.GreenBack.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private int totalRides;
    private int lastMonthRides;

    private int totalDrivers;
    private int lastMonthDrivers;

    private double totalCO2Saved;
    private double lastMonthCO2Saved;

    private double totalDistance;
    private double lastMonthDistance;

}
