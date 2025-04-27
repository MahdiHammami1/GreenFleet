package com.example.GreenBack.repository;

import com.example.GreenBack.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    Vehicle getVehicleByVehicleId(Long vehicleId);
}
