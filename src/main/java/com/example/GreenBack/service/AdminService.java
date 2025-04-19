package com.example.GreenBack.service;

import com.example.GreenBack.entity.*;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<User> getAllUsers();
    void deleteUserById(Long id);
    List<Vehicle> getAllVehicles();
    List<Ride> getAllRides();
    List<Stopover> getAllStopovers();

    List<Booking> getAllBookings();
    List<Preference> getAllPreferences();
    long countTotalUsers();
    long countTotalRides();
    long countTotalVehicles();
    long countTotalBookings();
}
