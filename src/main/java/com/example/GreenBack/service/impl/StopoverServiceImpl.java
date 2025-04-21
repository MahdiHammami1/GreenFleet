package com.example.GreenBack.service.impl;

import com.example.GreenBack.entity.Stopover;
import com.example.GreenBack.repository.StopoverRepository;
import com.example.GreenBack.service.StopoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StopoverServiceImpl implements StopoverService {
    private final StopoverRepository stopoverRepository;

    @Autowired
    public StopoverServiceImpl(StopoverRepository stopoverRepository) {

        this.stopoverRepository=stopoverRepository ;
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


            existingStopover.setStopoverStatus(newStopoverData.getStopoverStatus());
            existingStopover.setLocation(newStopoverData.getLocation());

            Stopover updatedStopover = stopoverRepository.save(existingStopover);
            return Optional.of(updatedStopover);
        } else {
            return Optional.empty();
        }
    }
    @Override
    public Optional<Stopover> addStopover(Stopover stopover) {
        stopoverRepository.save(stopover);
        return Optional.of(stopover);

    }





    @Override
    public Optional<Stopover> getStopoverByRideId(Long rideId) {
        return stopoverRepository.getStopoverByRide_RideId(rideId) ;
    }



}

