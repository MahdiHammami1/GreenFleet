package com.example.GreenBack.service;

import com.example.GreenBack.dto.VehicleDTO;
import com.example.GreenBack.entity.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    VehicleDTO saveVehicle(VehicleDTO vehicleDTO);
    Optional<VehicleDTO> getVehicleById(Long id);
    List<VehicleDTO> getAllVehicles();
    Vehicle updateVehicleById(Long id, Vehicle updatedVehicle);
    void deleteVehicle(Long id);
    Vehicle getVehicleByRideId(Long RideID);
    long countVehicles();


}
