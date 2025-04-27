package com.example.GreenBack.service;

import com.example.GreenBack.dto.RideDTO;
import com.example.GreenBack.entity.Ride;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface  RideService {

    Ride saveRide(RideDTO ride);
    List<Ride> getAllRides();
    Optional<Ride> getRideById(Long id);

    Optional<Ride> getRideByDate(LocalDate date) ;
    Optional<Ride> getRideByTime(LocalTime time);

    void deleteRide(Long id);


    Optional<Ride> updateRideById(Long  id ,  Ride ride);

    Optional<Ride> updateAvailableSeats(Long rideId, int seatsBooked);





}