package com.example.GreenBack.controller;


import com.example.GreenBack.dto.*;
import com.example.GreenBack.entity.Booking;
import com.example.GreenBack.entity.Ride;
import com.example.GreenBack.service.impl.BookingServiceImpl;
import com.example.GreenBack.service.impl.RideServiceImpl;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/rides")
public class RideController {
    private final RideServiceImpl rideService;
    private final BookingServiceImpl bookingService;


    public RideController(RideServiceImpl rideService, BookingServiceImpl bookingService) {
        this.rideService = rideService;
        this.bookingService = bookingService;
    }

    @PostMapping("/searchRides")
    public ResponseEntity<List<RideResponseDto>> searchRides(@RequestBody RideSearchDTO searchCriteria) {
        List<RideResponseDto> matchedRides = rideService.searchRides(searchCriteria);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=utf-8")
                .body(matchedRides);
    }




    @GetMapping("/getRides")
    public ResponseEntity<List<RideDTO>> getRides() {
        List<Ride> rides = rideService.getAllRides();
        List<RideDTO> rideDTOs = rides.stream()
                .map(RideDTO::convertToDTO)
                .toList();
        return ResponseEntity.ok(rideDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideExtendedDto> getRideById(@PathVariable Long id) {
        Optional<Ride> rideOptional = rideService.getRideById(id);

        if (rideOptional.isPresent()) {
            Ride ride = rideOptional.get();
            RideExtendedDto rideDTO = rideService.convertToRideExtendedDto(ride);
            return ResponseEntity.ok().
                    header("Content-Type", "application/json; charset=utf-8")
                    .body(rideDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getByDate")
    public ResponseEntity<Optional<Ride>> getRideByDate(@RequestParam LocalDate date) {
        Optional<Ride> ride = rideService.getRideByDate(date) ;
        return  ResponseEntity.ok(ride);

    }

    @GetMapping("/getByTime")
    public ResponseEntity<Optional<Ride>> getRideByTime(@RequestParam LocalTime time) {
        Optional<Ride> ride = rideService.getRideByTime(time) ;
        return  ResponseEntity.ok(ride);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteRide(@RequestParam Long id) {
        rideService.deleteRide(id) ;
        return ResponseEntity.ok("Ride Successfully deleted") ;
    }

    @PostMapping
    public ResponseEntity<?> createRide(@RequestBody RideDTO request) {
        Ride savedRide = rideService.saveRide(request);
        return ResponseEntity.ok(savedRide.getRideId());
    }


    @PutMapping("/updateRide")
    public ResponseEntity<String> updateRideById(
            @RequestParam Long id ,
            @RequestBody Ride ride
    ) {
        rideService.updateRideById(id , ride) ;
        return ResponseEntity.ok("Ride Successfully updated") ;
    }

    @PutMapping("/updateseats ")
    public ResponseEntity<Ride> updateAvailableSeats(
            @RequestBody Ride ride ,
            @RequestParam Long rideId,
            @RequestParam int seats
    ) {
        rideService.updateAvailableSeats(rideId, seats) ;
        return ResponseEntity.ok(ride) ;
    }

    @PutMapping("/update/{rideId}")
    public ResponseEntity<String> updateStart(
            @PathVariable Long rideId,
            @RequestBody Map<String, Integer> body
    ) {
        try {
            Integer stopoverIndex = body.get("stopoverIndex");
            if (stopoverIndex == null) {
                return ResponseEntity.badRequest().body("Missing stopoverIndex in request body");
            }

            rideService.updateStart(rideId, stopoverIndex);
            return ResponseEntity.ok("Ride and bookings updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update ride: " + e.getMessage());
        }
    }


    @PutMapping("/update/{rideId}/{passengerId}")
    public ResponseEntity<String> updateAccept(
            @PathVariable Long rideId,
            @PathVariable Long passengerId,
            @RequestParam boolean accept
    ) {

        try {
            rideService.updateAccept(rideId, passengerId, accept);
            return ResponseEntity.ok("Booking updated to " + (accept ? "ACCEPTED" : "REJECTED"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update booking: " + e.getMessage());
        }
    }




}

