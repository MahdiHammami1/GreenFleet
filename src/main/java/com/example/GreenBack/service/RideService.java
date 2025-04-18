package com.example.GreenBack.service;


import com.example.GreenBack.entity.Ride;

import java.util.List;
import java.util.Optional;

public interface  RideService {

    Ride saveRide(Ride ride);
    List<Ride> getAllRides();
    Optional<Ride> getRideById(Long id);

    void deleteRide(Long id);

    Optional<Ride> publishRide(Long id);

    Optional<Ride> updateAvailableSeats(Long rideId, int seatsBooked);




}
