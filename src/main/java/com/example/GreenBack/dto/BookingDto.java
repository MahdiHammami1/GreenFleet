package com.example.GreenBack.dto;

import com.example.GreenBack.entity.Location;
import com.example.GreenBack.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long passengerId;
    private Long rideId;
    private String feedback;
    private Double rating;
    private BookingStatus bookingStatus;
    private String  pickupLocation;
}
