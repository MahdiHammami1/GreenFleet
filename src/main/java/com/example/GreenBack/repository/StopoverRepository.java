package com.example.GreenBack.repository;

import com.example.GreenBack.entity.Location;
import com.example.GreenBack.entity.Stopover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StopoverRepository extends JpaRepository<Stopover,Long> {

    Optional<Stopover> getStopoversByIdStopoverExists(Long idStopover);

    Optional<Stopover> getStopoverByIdStopover(Long idStopover);

    Optional<Stopover> getStopOverByLocation(Location location);

    Optional<Stopover> getStopOverByLocation_NameLocation(String locationNameLocation);


    Optional<Stopover> getStopoverByRide_RideId(Long rideRideId);
}
