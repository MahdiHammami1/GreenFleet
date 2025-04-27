package com.example.GreenBack.mapper;

import com.example.GreenBack.dto.RideDTO;
import com.example.GreenBack.entity.Ride;
import com.example.GreenBack.entity.User;
import com.example.GreenBack.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class RideMapper {
    private final UserRepository userRepository;

    public RideMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Ride toEntity(RideDTO dto) {
        Ride ride = new Ride();
        ride.setRideId(dto.getRideId());
        ride.setRideDate(dto.getRideDate());
        ride.setRideTime(dto.getRideTime());
        ride.setNumberOfSeat(dto.getNumberOfSeat());
        ride.setAvailableSeats(dto.getAvailableSeats());
        ride.setPublished(dto.isPublished());


        User driver = userRepository.findById(dto.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        ride.setDriver(driver);

        return ride;
    }
}
