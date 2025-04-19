package com.example.GreenBack.service.impl;

import com.example.GreenBack.entity.Ride;
import com.example.GreenBack.repository.RideRepository;
import com.example.GreenBack.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;


    public RideServiceImpl(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Override
    public Ride saveRide(Ride ride) {
        return rideRepository.save(ride);
    }

    @Override
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    @Override
    public Optional<Ride> getRideById(Long id) {
        return rideRepository.findById(id) ;
    }

    @Override
    public Optional<Ride> getRideByLocationName(String locationName) {
        return rideRepository.findRideByLocationName(locationName);

    }

    @Override
    public Optional<Ride> getRideByDate(LocalDate date){
        return rideRepository.getRideByRideDate(date) ;
    }

    @Override
    public Optional<Ride> getRideByTime(LocalTime time){
        return rideRepository.getRidesByRideTime(time) ;
    }




    @Override
    public void deleteRide(Long id) {
        rideRepository.deleteById(id);
    }

    @Override
    public Optional<Ride> publishRide(Long id) {
        return rideRepository.findById(id).map(ride -> {
            ride.setPublished(true);
            return rideRepository.save(ride);
        });
    }

    @Override
    public Optional<Ride> updateRide(Ride ride) {
        return rideRepository.UpdateRide(ride);
    }



    @Override
    public Optional<Ride> updateAvailableSeats(Long rideId, int seatsBooked) {
        return rideRepository.findById(rideId).map(ride -> {
            int updatedSeats = ride.getAvailableSeats() - seatsBooked;
            ride.setAvailableSeats(Math.max(updatedSeats, 0));
            return rideRepository.save(ride);
        });
    }

}
