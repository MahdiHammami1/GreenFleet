package com.example.GreenBack.controller;

import com.example.GreenBack.dto.BookingDto;
import com.example.GreenBack.dto.BookingResponseDto;
import com.example.GreenBack.entity.Booking;
import com.example.GreenBack.service.BookingService;
import com.example.GreenBack.service.impl.BookingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingServiceImpl bookingService;

    @PostMapping("/create/{rideId}/{userId}")
    public ResponseEntity<BookingDto> createBooking(
            @PathVariable Long rideId,
            @PathVariable Long userId,
            @RequestBody BookingDto bookingRequest) {


        Booking booking = bookingService.createBooking(rideId, userId, bookingRequest);


        BookingDto bookingDto = bookingService.mapToBookingDto(booking);


        return new ResponseEntity<>(bookingDto, HttpStatus.CREATED);
    }




    @PutMapping("/accept/{bookingId}/{driverId}")
    public ResponseEntity<BookingDto> acceptBooking(
            @PathVariable Long bookingId,
            @PathVariable Long driverId) {

        BookingDto booking = bookingService.acceptBooking(bookingId, driverId);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsForUser(@PathVariable Long userId) {
        List<BookingResponseDto> bookings = bookingService.getBookingsForUser(userId);
        return ResponseEntity.ok()
                .header(
                        "Content-Type",
                        "application/json; charset=utf-8"
                )
                .body(bookings);
    }







}
