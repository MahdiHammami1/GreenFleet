package com.example.GreenBack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RideResponseDto {
    private Long rideId;
    private Long driverId;
    private String driverName;
    private String carName;
    private Double rateDriver;
    private String stopoverName; // li matchet m3ehaaa
    private String distanceBetween;
    private List<String> prefrences;

    private double parseDistanceToMeters(String distanceText) {
        if (distanceText == null || distanceText.isEmpty()) {
            throw new IllegalArgumentException("Distance text is null or empty");
        }

        distanceText = distanceText.replace(",", "."); // Replace , with .
        try {
            if (distanceText.contains("km")) {
                double kmValue = Double.parseDouble(distanceText.replace("km", "").trim());
                return kmValue * 1000;
            } else if (distanceText.contains("m")) {
                return Double.parseDouble(distanceText.replace("m", "").trim());
            } else {
                throw new IllegalArgumentException("Unknown distance format: " + distanceText);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Failed to parse distance: " + distanceText, e);
        }
    }
}
