package com.example.GreenBack.service.impl;

import com.example.GreenBack.enums.BookingStatus;
import com.example.GreenBack.repository.BookingRepository;
import com.example.GreenBack.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class dashboardService {

    private final RideRepository rideRepository;
    private final BookingRepository bookingRepository;
    private static final double CO2_PER_KM = 0.192;


    public dashboardService(RideRepository rideRepository, BookingRepository bookingRepository) {
        this.rideRepository = rideRepository;
        this.bookingRepository = bookingRepository;
    }


    public long getNumberOfPassengers() {
        return bookingRepository.countByStatus(BookingStatus.ACCEPTED);
    }

    public long getNumberOfPassengersLastMonth() {

        LocalDate now = LocalDate.now();
        LocalDate firstDayOfLastMonth = now.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfLastMonth = now.withDayOfMonth(1).minusDays(1);


        return bookingRepository.countAcceptedBookingsForRidesLastMonth(
                BookingStatus.ACCEPTED,
                firstDayOfLastMonth,
                lastDayOfLastMonth
        );
    }



    public double getTotalDistance() {
        return rideRepository.getTotalDistance();
    }

    public double getDistanceLastMonth() {
        LocalDate  lastMonth = LocalDate .now().minusMonths(1);
        return rideRepository.getDistanceLastMonth(lastMonth);
    }


    public double getTotalCO2Saved() {
        double totalDistance = rideRepository.getTotalDistance();
        return totalDistance * CO2_PER_KM;
    }

    public double getCO2SavedLastMonth() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        double distanceLastMonth = rideRepository.getDistanceLastMonth(lastMonth);
        return distanceLastMonth * CO2_PER_KM;
    }


    public long getTotalRides() {
        return rideRepository.countTotalRides();
    }

    public long getRidesLastMonth() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        return rideRepository.countRidesLastMonth(lastMonth);
    }

}
