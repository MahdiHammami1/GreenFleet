package com.example.GreenBack.service.impl;

import com.example.GreenBack.entity.Stopover;
import com.example.GreenBack.repository.StopoverRepository;
import com.example.GreenBack.service.StopoverService;

import java.util.List;
import java.util.Optional;

public class StopoverServiceImpl implements StopoverService {
    private StopoverRepository stopoverRepository;
    public StopoverServiceImpl() {
        super();
    }

    @Override
    public List<Stopover> getAllStopover() {
        return stopoverRepository.findAll();
    }

    @Override
    public Optional<Stopover> getStopoverById(Long id) {
        return stopoverRepository.getStopoverByIdStopover( id);
    }

    @Override
    public Optional<Stopover> getStopoverByLocationName(String locationName) {
        return stopoverRepository.getStopOverByLocation_NameLocation(locationName) ;
    }

    @Override
    public Optional<Stopover> updateStopoverById(Long idStopover, Stopover newStopoverData) {
        Optional<Stopover> existingStopoverOpt = stopoverRepository.findById(idStopover);
        if (existingStopoverOpt.isPresent()) {
            Stopover existingStopover = existingStopoverOpt.get();

            // Update fields
            existingStopover.setStopoverStatus(newStopoverData.getStopoverStatus());
            existingStopover.setLocation(newStopoverData.getLocation());

            Stopover updatedStopover = stopoverRepository.save(existingStopover);
            return Optional.of(updatedStopover);
        } else {
            return Optional.empty();
        }
    }
    @Override
    public Optional addStopover(Stopover stopover) {
        stopoverRepository.save(stopover);
        return Optional.of(stopover);

    }





    @Override
    public Optional<Stopover> getStopoverByRideId(Long rideId) {
        return stopoverRepository.getStopoverByRide_RideId(rideId) ;
    }



}

