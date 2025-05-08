package com.example.GreenBack.controller;

import com.example.GreenBack.service.impl.dashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final dashboardService dashboard;

    public DashboardController(dashboardService dashboard) {
        this.dashboard = dashboard;
    }


    @GetMapping("/passengers")
    public long getNumberOfPassengers() {
        return dashboard.getNumberOfPassengers();
    }


    @GetMapping("/passengers/last-month")
    public long getNumberOfPassengersLastMonth() {
        return dashboard.getNumberOfPassengersLastMonth();
    }


    @GetMapping("/total-distance")
    public double getTotalDistance() {
        return dashboard.getTotalDistance();
    }


    @GetMapping("/distance/last-month")
    public double getDistanceLastMonth() {
        return dashboard.getDistanceLastMonth();
    }


    @GetMapping("/total-co2-saved")
    public double getTotalCO2Saved() {
        return dashboard.getTotalCO2Saved();
    }


    @GetMapping("/co2-saved/last-month")
    public double getCO2SavedLastMonth() {
        return dashboard.getCO2SavedLastMonth();
    }


    @GetMapping("/total-rides")
    public long getTotalRides() {
        return dashboard.getTotalRides();
    }


    @GetMapping("/rides/last-month")
    public long getRidesLastMonth() {
        return dashboard.getRidesLastMonth();
    }
}
