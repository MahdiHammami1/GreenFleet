package com.example.GreenBack;

import com.example.GreenBack.service.impl.DistanceService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistanceServiceTest {

    public static void main(String[] args) {

        DistanceService distanceService = new DistanceService();

        String destination = "36.831129,10.1466462";

        String origin = "36.826421,10.144960";

        DistanceService.DistanceDurationResponse result = distanceService.getDistanceAndDuration(origin, destination);

        System.out.println("Distance: " + result.distanceText());
        System.out.println("Duration: " + result.durationText());
    }
}
