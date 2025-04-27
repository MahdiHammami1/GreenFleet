package com.example.GreenBack.controller;


import com.example.GreenBack.dto.RideDTO;
import com.example.GreenBack.dto.RideResponseDto;
import com.example.GreenBack.dto.RideSearchDTO;
import com.example.GreenBack.entity.Ride;
import com.example.GreenBack.service.impl.RideServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/rides")
public class RideController {
    private final RideServiceImpl rideService;


    public RideController(RideServiceImpl rideService) {
        this.rideService = rideService;
    }

    @PostMapping("/searchRides")
    public ResponseEntity<List<RideResponseDto>> searchRides(@RequestBody RideSearchDTO searchCriteria) {
        List<RideResponseDto> matchedRides = rideService.searchRides(searchCriteria);
        return ResponseEntity.ok(matchedRides);
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
    public ResponseEntity<RideDTO> getRideById(@PathVariable Long id) {
        Optional<Ride> rideOptional = rideService.getRideById(id);

        if (rideOptional.isPresent()) {
            Ride ride = rideOptional.get();
            RideDTO rideDTO = RideDTO.convertToDTO(ride);
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
}

