package com.example.GreenBack.service;


import com.example.GreenBack.entity.Ride;

import javax.swing.text.html.Option;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface  RideService {

    Ride saveRide(Ride ride);
    List<Ride> getAllRides();
    Optional<Ride> getRideById(Long id);

    Optional<Ride> getRideByDate(LocalDate date) ;
    Optional<Ride> getRideByTime(LocalTime time);
    Optional<Ride> getRideByLocationName(String location);

    void deleteRide(Long id);

    Optional<Ride> publishRide(Long id);

    Optional<Ride> updateAvailableSeats(Long rideId, int seatsBooked);




}
