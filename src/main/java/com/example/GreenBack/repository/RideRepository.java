package com.example.GreenBack.repository;

import com.example.GreenBack.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride,Long> {
    Optional<Ride> getRideByRideDate(LocalDate rideDate);

    Optional<Ride> getRidesByRideTime(LocalTime rideTime);

    Optional<Ride> findRideByLocationName(String locationName);

    Optional<Ride> UpdateRide( Ride ride);


}
