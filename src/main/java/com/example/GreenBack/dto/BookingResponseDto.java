package com.example.GreenBack.dto;

import com.example.GreenBack.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingResponseDto {
    private String pickupLocation;
    private BookingStatus bookingStatus;
    private LocalDate date;
    private LocalTime time;
    private String driverName;
    private Double rating;
    private Long driverId;
}
