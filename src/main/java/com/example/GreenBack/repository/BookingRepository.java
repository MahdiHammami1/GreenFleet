package com.example.GreenBack.repository;

import com.example.GreenBack.entity.Booking;
import com.example.GreenBack.entity.Ride;
import com.example.GreenBack.entity.User;
import com.example.GreenBack.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRide(Ride ride);

    List<Booking> findByPassenger(User passenger);

    List<Booking> findByRide_RideId(Long rideId);

    Booking findByRide_RideIdAndPassenger_UserId(Long rideId,Long userId);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.bookingStatus = :status")
    long countByStatus(@Param("status") BookingStatus status);


    @Query("SELECT COUNT(b) FROM Booking b JOIN b.ride r WHERE b.bookingStatus = :status AND r.rideDate BETWEEN :startDate AND :endDate")

    long countAcceptedBookingsForRidesLastMonth(
            @Param("status") BookingStatus status,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}
