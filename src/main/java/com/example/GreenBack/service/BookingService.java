package com.example.GreenBack.service;

import com.example.GreenBack.dto.BookingDto;
import com.example.GreenBack.dto.BookingResponseDto;
import com.example.GreenBack.entity.Booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(Long rideId, Long userId, BookingDto bookingRequest);
    void cancelBooking(Long bookingId, Long userId);
    List<BookingDto> getBookingsForRide(Long rideId);
    List<BookingResponseDto> getBookingsForUser(Long userId);
}
