package com.example.GreenBack.controller;

import com.example.GreenBack.entity.*;
import com.example.GreenBack.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    private final AdminService adminService;


    @Autowired
    public AdminController(AdminService adminService ) {
        this.adminService = adminService;
    }

    @GetMapping("/admins")
    public ResponseEntity<Optional<List<Admin>>> getAllAdmins() {
        Optional<List<Admin>> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        adminService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(adminService.getAllVehicles());
    }

    @GetMapping("/rides")
    public ResponseEntity<List<Ride>> getAllRides() {
        return ResponseEntity.ok(adminService.getAllRides());
    }

    @GetMapping("/stopovers")
    public ResponseEntity<List<Stopover>> getAllStopovers() {
        return ResponseEntity.ok(adminService.getAllStopovers());
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(adminService.getAllBookings());
    }

    @GetMapping("/preferences")
    public ResponseEntity<List<Preference>> getAllPreferences() {
        return ResponseEntity.ok(adminService.getAllPreferences());
    }


    @GetMapping("/count/users")
    public ResponseEntity<Long> countTotalUsers() {
        return ResponseEntity.ok(adminService.countTotalUsers());
    }

    @GetMapping("/count/vehicles")
    public ResponseEntity<Long> countTotalVehicles() {
        return ResponseEntity.ok(adminService.countTotalVehicles());
    }

    @GetMapping("/count/rides")
    public ResponseEntity<Long> countTotalRides() {
        return ResponseEntity.ok(adminService.countTotalRides());
    }

    @GetMapping("/count/bookings")
    public ResponseEntity<Long> countTotalBookings() {
        return ResponseEntity.ok(adminService.countTotalBookings());
    }








}
