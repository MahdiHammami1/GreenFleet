package com.example.GreenBack.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideDTO {
    private Long rideId;
    private LocalDate rideDate;
    private LocalTime rideTime;
    private int numberOfSeat;
    private int availableSeats;
    private boolean published;
    private double totalDistance;

    private Long driverId;

    private List<Long> preferenceIds;
    private List<Long> bookingIds;
    private List<Long> stopoverIds;
}
