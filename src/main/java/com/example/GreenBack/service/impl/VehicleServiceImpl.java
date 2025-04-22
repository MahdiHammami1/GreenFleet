package com.example.GreenBack.service.impl;

import com.example.GreenBack.dto.VehicleDTO;
import com.example.GreenBack.entity.User;
import com.example.GreenBack.entity.Vehicle;
import com.example.GreenBack.repository.UserRepository;
import com.example.GreenBack.repository.VehicleRepository;
import com.example.GreenBack.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    @Transactional
    public VehicleDTO saveVehicle(VehicleDTO vehicleRequest) {
        User owner = userRepository.findById(vehicleRequest.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + vehicleRequest.getOwnerId()));

        Vehicle vehicle = new Vehicle();
        vehicle.setLicenceNumber(vehicleRequest.getLicenceNumber());
        vehicle.setBrand(vehicleRequest.getBrand());
        vehicle.setModel(vehicleRequest.getModel());
        vehicle.setNumberOfSeat(vehicleRequest.getNumberOfSeat());
        vehicle.setRegistrationDate(vehicleRequest.getRegistrationDate());
        vehicle.setPictureUrl(vehicleRequest.getPictureUrl());
        vehicle.setOwner(owner);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return convertToDto(savedVehicle);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<VehicleDTO> getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .map(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    private VehicleDTO convertToDto(Vehicle vehicle) {
        VehicleDTO dto = new VehicleDTO();
        dto.setVehicleId(vehicle.getVehicleId());
        dto.setLicenceNumber(vehicle.getLicenceNumber());
        dto.setBrand(vehicle.getBrand());
        dto.setModel(vehicle.getModel());
        dto.setNumberOfSeat(vehicle.getNumberOfSeat());
        dto.setRegistrationDate(vehicle.getRegistrationDate());
        dto.setPictureUrl(vehicle.getPictureUrl());
        dto.setOwnerId(vehicle.getOwner() != null ? vehicle.getOwner().getUserId() : null);
        return dto;
    }
    @Override
    public long countVehicles(){
        return vehicleRepository.count();
   }
    @Override
    public Vehicle updateVehicleById(Long id, Vehicle updatedVehicle) {
        Vehicle existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));

        existingVehicle.setLicenceNumber(updatedVehicle.getLicenceNumber());
        existingVehicle.setBrand(updatedVehicle.getBrand());
        existingVehicle.setModel(updatedVehicle.getModel());
        existingVehicle.setNumberOfSeat(updatedVehicle.getNumberOfSeat());
        existingVehicle.setRegistrationDate(updatedVehicle.getRegistrationDate());
        existingVehicle.setPictureUrl(updatedVehicle.getPictureUrl());
        existingVehicle.setOwner(updatedVehicle.getOwner());

        return vehicleRepository.save(existingVehicle);
    }


    @Override
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public Vehicle getVehicleByRideId(Long RideID) {
        return null;
    }
}
