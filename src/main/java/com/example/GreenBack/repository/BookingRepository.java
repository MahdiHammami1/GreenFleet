package com.example.GreenBack.repository;

import com.example.GreenBack.entity.Booking;
import com.example.GreenBack.entity.Ride;
import com.example.GreenBack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRide(Ride ride);

    List<Booking> findByPassenger(User passenger);

    List<Booking> findByRide_RideId(Long rideId);

    Booking findByRide_RideIdAndPassenger_UserId(Long rideId,Long userId);
}
