package com.example.GreenBack.controller;

import com.example.GreenBack.entity.Stopover;
import com.example.GreenBack.service.StopoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("Stopovers")
public class StopoverController {
    private final StopoverService stopoverService;


    @Autowired
    public StopoverController (StopoverService stopoverService) {
        this.stopoverService = stopoverService ;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Stopover>> getAllStopovers() {
        List<Stopover> stopovers = stopoverService.getAllStopover();
        return ResponseEntity.ok(stopovers) ;
    }

    @GetMapping("/getById")
    public ResponseEntity<Optional<Stopover>> getStopoverById(@RequestParam Long id) {
        Optional<Stopover> stopover  = stopoverService.getStopoverById(id) ;
        return ResponseEntity.ok( stopover) ;

    }

    @GetMapping("/getByLocationName")
    public ResponseEntity<Optional<Stopover>> getStopoverByLocationName(@RequestParam String locationName) {
        Optional<Stopover> stopover = stopoverService.getStopoverByLocationName(locationName) ;
        return  ResponseEntity.ok(stopover);

    }

    @GetMapping("/getByRideId")
    public ResponseEntity<Optional<Stopover>> getStopoverByRideId(@RequestParam Long rideId) {
        Optional<Stopover> stopover = stopoverService.getStopoverByRideId(rideId) ;
        return ResponseEntity.ok(stopover);
    }

    @PostMapping("/postStopover")
    public ResponseEntity<Optional<Stopover>> addStopover(@RequestBody Stopover stopover) {
        Optional<Stopover> newStopover = stopoverService.addStopover(stopover) ;
        return ResponseEntity.ok(newStopover) ;
    }

    @PutMapping("/putStopover")
    public ResponseEntity<String> updateStopover(@RequestBody Stopover stopover , @RequestParam Long id) {
        stopoverService.updateStopoverById(id , stopover) ;
        return ResponseEntity.ok("Ride Successfully updated") ;
    }




}
