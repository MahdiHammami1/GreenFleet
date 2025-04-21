package com.example.GreenBack.controller;

import com.example.GreenBack.entity.Ride;
import com.example.GreenBack.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("")
public class RideController {
    private final RideService rideService;

    @Autowired
    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @GetMapping
    public ResponseEntity<List<Ride>> getRides() {
        List<Ride> rides = rideService.getAllRides();
        return ResponseEntity.ok(rides);
    }

    @GetMapping
    public ResponseEntity<Optional<Ride>> getRideById(@RequestParam Long id) {
        Optional<Ride> ride = rideService.getRideById(id) ;
        return  ResponseEntity.ok(ride);

    }


    @GetMapping
    public ResponseEntity<Optional<Ride>> getRideByLocationName(@RequestParam String locationName) {
        Optional<Ride> ride = rideService.getRideByLocationName(locationName) ;
        return  ResponseEntity.ok(ride);

    }

    @GetMapping
    public ResponseEntity<Optional<Ride>> getRideByDate(@RequestParam LocalDate date) {
         Optional<Ride> ride = rideService.getRideByDate(date) ;
         return  ResponseEntity.ok(ride);

    }

    @GetMapping
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
    public ResponseEntity<String> addRide(@RequestBody Ride ride) {
        rideService.saveRide(ride) ;
        return ResponseEntity.ok("Ride Successfully added") ;
    }


    @PutMapping
    public ResponseEntity<String> updateRide(@RequestBody Ride ride) {
        rideService.updateRide(ride) ;
        return ResponseEntity.ok("Ride Successfully updated") ;
    }

    @PutMapping
    public ResponseEntity<Ride> updateAvailableSeats(@RequestBody Ride ride , @RequestParam Long rideId, @RequestParam int seats) {
        rideService.updateAvailableSeats(rideId, seats) ;
        return ResponseEntity.ok(ride) ;
    }







}
