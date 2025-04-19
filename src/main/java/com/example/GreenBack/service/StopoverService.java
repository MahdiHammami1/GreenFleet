package com.example.GreenBack.service;

import com.example.GreenBack.entity.Stopover;

import java.util.List;
import java.util.Optional;

public interface StopoverService {
    List<Stopover> getAllStopover();
    Optional<Stopover> getStopoverById(Long id);
    Optional<Stopover> getStopoverByLocationName(String locationName);
    Optional<Stopover> addStopover(Stopover s);
    Optional<Stopover> updateStopoverById(Long idStopover, Stopover s);
    Optional<Stopover> getStopoverByRideId(Long rideId);






}
