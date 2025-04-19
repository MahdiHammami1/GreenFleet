package com.example.GreenBack.service.impl;

import com.example.GreenBack.entity.*;
import com.example.GreenBack.repository.*;
import com.example.GreenBack.service.AdminService;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    private UserRepository userRepository;
    private StopoverRepository stopoverRepository;
    private BookingRepository bookingRepository;
    private AdminRepository adminRepository;
    private VehicleRepository vehicleRepository;
    private PreferenceRepository preferenceRepository;
    private RideRepository rideRepository;

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public  List<Vehicle> getAllVehicles(){

        return vehicleRepository.findAll();
    }

    @Override
    public List<Ride> getAllRides(){
        return rideRepository.findAll() ;
    }

    @Override
    public List<Stopover> getAllStopovers(){
        return stopoverRepository.findAll();
    }

    @Override
    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    @Override
    public List<Preference> getAllPreferences(){
        return preferenceRepository.findAll();
    }

    @Override
    public long countTotalUsers(){
        return userRepository.count();
    }

    @Override
    public long countTotalVehicles(){
        return vehicleRepository.count();
    }

    @Override
    public long countTotalRides(){
        return rideRepository.count();
    }

    @Override
    public long countTotalBookings(){

        return bookingRepository.count();
    }


}
