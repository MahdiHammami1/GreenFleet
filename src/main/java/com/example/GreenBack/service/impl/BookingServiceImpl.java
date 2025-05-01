package com.example.GreenBack.service.impl;

import com.example.GreenBack.dto.BookingDto;
import com.example.GreenBack.dto.BookingResponseDto;
import com.example.GreenBack.entity.Booking;
import com.example.GreenBack.entity.Ride;
import com.example.GreenBack.entity.User;
import com.example.GreenBack.enums.BookingStatus;
import com.example.GreenBack.repository.BookingRepository;
import com.example.GreenBack.repository.RideRepository;
import com.example.GreenBack.repository.UserRepository;
import com.example.GreenBack.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RideRepository rideRepository;

    @Override
    public Booking createBooking(Long rideId, Long userId, BookingDto bookingRequest) {
        User passenger = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new IllegalArgumentException("Ride not found"));

        if (ride.getAvailableSeats() <= 0) {
            throw new IllegalStateException("No seats available for this ride");
        }
        Booking booking = new Booking();
        booking.setPassenger(passenger);
        booking.setRide(ride);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setPickupLocation(bookingRequest.getPickupLocation());

        rideRepository.save(ride);
        return bookingRepository.save(booking);
    }
    public BookingDto mapToBookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setPassengerId(booking.getPassenger().getUserId());
        bookingDto.setRideId(booking.getRide().getRideId());
        bookingDto.setFeedback(booking.getFeedback());
        bookingDto.setRating(booking.getRating());
        bookingDto.setBookingStatus(booking.getBookingStatus());
        bookingDto.setPickupLocation(booking.getPickupLocation());
        return bookingDto;
    }
    public BookingDto acceptBooking(Long bookingId, Long driverId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (!booking.getRide().getDriver().getUserId().equals(driverId)) {
            throw new IllegalStateException("You are not the driver of this ride");
        }


        booking.setBookingStatus(BookingStatus.ACCEPTED);

        Ride ride = booking.getRide();
        if (ride.getAvailableSeats() <= 0) {
            throw new IllegalStateException("No available seats left to accept the booking");
        }
        ride.setAvailableSeats(ride.getAvailableSeats() - 1);

        bookingRepository.save(booking);
        rideRepository.save(ride);

        return mapToBookingDto(booking);
    }
    @Override
    public void cancelBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (!booking.getPassenger().getUserId().equals(userId)) {
            throw new IllegalStateException("You can only cancel your own bookings");
        }

        booking.setBookingStatus(BookingStatus.ACCEPTED);
        bookingRepository.save(booking);

        Ride ride = booking.getRide();
        ride.setAvailableSeats(ride.getAvailableSeats() + 1);
        rideRepository.save(ride);
    }

    @Override
    public List<BookingDto> getBookingsForRide(Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new IllegalArgumentException("Ride not found"));

        return bookingRepository.findByRide(ride).stream()
                .map(this::mapToBookingDto)
                .toList();
    }


    public BookingDto declineBooking(Long bookingId, Long driverId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (!booking.getRide().getDriver().getUserId().equals(driverId)) {
            throw new IllegalStateException("You are not the driver of this ride");
        }

        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException("Only pending bookings can be declined");
        }

        booking.setBookingStatus(BookingStatus.REJECTED);
        return mapToBookingDto(bookingRepository.save(booking));
    }

    public BookingResponseDto mapToBookingResponseDto(Booking booking) {
        BookingResponseDto bookingResponseDto = new BookingResponseDto();
        bookingResponseDto.setBookingStatus(booking.getBookingStatus());
        bookingResponseDto.setDate(booking.getRide().getRideDate());
        bookingResponseDto.setTime(booking.getRide().getRideTime());
        bookingResponseDto.setDriverName(booking.getRide().getDriver().getLastname()+" "+booking.getRide().getDriver().getFirstname());
        bookingResponseDto.setRating(booking.getRide().getDriver().getRating());
        bookingResponseDto.setPickupLocation(booking.getPickupLocation());
        bookingResponseDto.setDriverId(booking.getRide().getDriver().getUserId());
        return bookingResponseDto;
    }

    @Override
    public List<BookingResponseDto> getBookingsForUser(Long userId) {
        User passenger = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Booking> bookings = bookingRepository.findByPassenger(passenger);

        return bookings.stream()
                .map(this::mapToBookingResponseDto)
                .toList();
    }

}
