package com.example.GreenBack.service.impl;

import com.example.GreenBack.entity.*;
import com.example.GreenBack.repository.*;
import com.example.GreenBack.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final StopoverRepository stopoverRepository;
    private final BookingRepository bookingRepository;
    private final AdminRepository adminRepository;
    private final VehicleRepository vehicleRepository;
    private final PreferenceRepository preferenceRepository;
    private final RideRepository rideRepository;


    @Autowired
    public AdminServiceImpl(UserRepository userRepository, StopoverRepository stopoverRepository, VehicleRepository vehicleRepository, PreferenceRepository preferenceRepository, RideRepository rideRepository, BookingRepository bookingRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.stopoverRepository = stopoverRepository ;
        this.vehicleRepository = vehicleRepository ;
        this.preferenceRepository = preferenceRepository ;
        this.rideRepository = rideRepository ;
        this.bookingRepository = bookingRepository ;
        this.adminRepository = adminRepository ;
    }

    @Override
    public Optional<List<Admin>> getAllAdmins(){
        List<Admin> admins = adminRepository.findAll();
        return Optional.of(admins) ;
    }
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
