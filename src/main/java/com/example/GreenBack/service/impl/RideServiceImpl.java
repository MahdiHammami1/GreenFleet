package com.example.GreenBack.service.impl;

import com.example.GreenBack.dto.RideDTO;
import com.example.GreenBack.entity.Booking;
import com.example.GreenBack.entity.Preference;
import com.example.GreenBack.entity.Ride;
import com.example.GreenBack.entity.Stopover;
import com.example.GreenBack.repository.RideRepository;
import com.example.GreenBack.service.RideService;
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
    public Optional<Ride> updateRideById(Long id , Ride ride) {
        Optional<Ride> optionalRide = rideRepository.findById(id);
        if (optionalRide.isPresent()) {
            rideRepository.save(ride);
            return Optional.of(ride);
        }else {
            return Optional.empty();
        }
    }



    @Override
    public Optional<Ride> updateAvailableSeats(Long rideId, int seatsBooked) {
        return rideRepository.findById(rideId).map(ride -> {
            int updatedSeats = ride.getAvailableSeats() - seatsBooked;
            ride.setAvailableSeats(Math.max(updatedSeats, 0));
            return rideRepository.save(ride);
        });
    }

    private RideDTO convertToDto(Ride ride) {
        RideDTO dto = new RideDTO();
        dto.setRideId(ride.getRideId());
        dto.setRideDate(ride.getRideDate());
        dto.setRideTime(ride.getRideTime());
        dto.setNumberOfSeat(ride.getNumberOfSeat());
        dto.setAvailableSeats(ride.getAvailableSeats());
        dto.setPublished(ride.isPublished());
        dto.setTotalDistance(ride.getTotalDistance());
        dto.setDriverId(ride.getDriver() != null ? ride.getDriver().getUserId() : null);
        dto.setPreferenceIds(ride.getPreferences() != null
                ? ride.getPreferences().stream().map(Preference::getPreferenceId).toList()
                : List.of());
        dto.setBookingIds(ride.getBookings() != null
                ? ride.getBookings().stream().map(Booking::getBookingId).toList()
                : List.of());
        dto.setStopoverIds(ride.getStopovers() != null
                ? ride.getStopovers().stream().map(Stopover::getIdStopover).toList()
                : List.of());
        return dto;
    }
}


