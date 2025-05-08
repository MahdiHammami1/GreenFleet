package com.example.GreenBack.repository;

import com.example.GreenBack.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride,Long> {
    Optional<Ride> getRideByRideDate(LocalDate rideDate);
    Optional<Ride> getRidesByRideTime(LocalTime rideTime);

    List<Ride> findRideByDriver_UserId(Long driverId);

    @Query("SELECT COUNT(DISTINCT r.driver.userId) FROM Ride r")
    int countTotalDrivers();

    @Query("SELECT COUNT(DISTINCT r.driver.userId) FROM Ride r WHERE r.rideDate >= :lastMonth")
    int countDriversLastMonth(@Param("lastMonth") LocalDateTime lastMonth);


    @Query("SELECT COALESCE(SUM(r.totalDistance), 0) FROM Ride r")
    double getTotalDistance();

    @Query("SELECT SUM(r.totalDistance) FROM Ride r WHERE r.rideDate >= :startDate")
    double getDistanceLastMonth(@Param("startDate") LocalDate startDate);



    @Query("SELECT COUNT(r) FROM Ride r")
    long countTotalRides();

    @Query("SELECT COUNT(r) FROM Ride r WHERE r.rideDate >= :lastMonth")
    long countRidesLastMonth(@Param("lastMonth") LocalDate lastMonth);







}
