package com.example.GreenBack.dto;


import com.example.GreenBack.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RequestBookingDto {
    private Long rideId;
    private String passengerName;
    private Long passengerId;
    private Double passengerRate;
    private String stopoverName;
    private BookingStatus bookingStatus;

}
