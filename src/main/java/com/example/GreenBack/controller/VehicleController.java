package com.example.GreenBack.controller;

import com.example.GreenBack.dto.VehicleDTO;
import com.example.GreenBack.entity.*;
import com.example.GreenBack.service.impl.VehicleServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleServiceImpl vehicleService;

    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("hiiiiiiiiiiiiiiiiii");
        return "Hello!";
    }

    @PostMapping("/add")
    public ResponseEntity<VehicleDTO> createVehicle(@Valid @RequestBody VehicleDTO vehicleRequest) {
        return ResponseEntity.ok(vehicleService.saveVehicle(vehicleRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }
    @GetMapping("/count")
    public ResponseEntity<String> getVehicleCount() {
        long count = vehicleService.countVehicles();
        return ResponseEntity.ok("Total vehicles in database: " + count);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(
            @PathVariable Long id,
            @RequestBody Vehicle updatedVehicle
    ) {
        return ResponseEntity.ok(vehicleService.updateVehicleById(id, updatedVehicle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ride/{rideId}")
    public ResponseEntity<Vehicle> getVehicleByRideId(@PathVariable Long rideId) {
        return ResponseEntity.ok(vehicleService.getVehicleByRideId(rideId));
    }
}
