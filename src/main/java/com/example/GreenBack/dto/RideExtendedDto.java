package com.example.GreenBack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.ExtensionMethod;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RideExtendedDto {
    private Long rideId;
    private LocalDate rideDate;
    private LocalTime rideTime;
    private int numberOfSeat;
    private boolean published;
    private int availableSeats;

    private Long driverId;
    private Long carId;

    private List<String> preferences;
    private List<StopoverDTO> stopovers;
    private List<RequestBookingDto> booking;
}
