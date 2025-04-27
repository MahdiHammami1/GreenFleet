package com.example.GreenBack.service.impl;

import com.example.GreenBack.dto.RideDTO;
import com.example.GreenBack.dto.RideResponseDto;
import com.example.GreenBack.dto.RideSearchDTO;
import com.example.GreenBack.entity.*;
import com.example.GreenBack.enums.StopoverStatus;
import com.example.GreenBack.mapper.RideMapper;
import com.example.GreenBack.repository.*;
import com.example.GreenBack.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final StopoverRepository stopoverRepository;
    private final PreferenceRepository preferenceRepository;
    private final DistanceService distanceService;





    public RideServiceImpl(RideRepository rideRepository, UserRepository userRepository, VehicleRepository vehicleRepository, StopoverRepository stopoverRepository, PreferenceRepository preferenceRepository, DistanceService distanceService) {
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;

        this.stopoverRepository = stopoverRepository;
        this.preferenceRepository = preferenceRepository;
        this.distanceService = distanceService;
    }

    @Override
    public Ride saveRide(RideDTO dto) {
        Ride ride = new Ride();

        ride.setRideDate(dto.getRideDate());
        ride.setRideTime(dto.getRideTime());
        ride.setNumberOfSeat(dto.getNumberOfSeat());
        ride.setAvailableSeats(dto.getNumberOfSeat()); // initially all available
        ride.setPublished(dto.isPublished());

        User driver = userRepository.findById(dto.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        ride.setDriver(driver);

        Vehicle vehicle = vehicleRepository.findById(dto.getCarId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        ride.setVehicle(vehicle);

        // Save ride first (so it has ID)
        ride = rideRepository.save(ride);

        // Create and assign stopovers
        final Ride finalRide = ride;
        List<Stopover> stopovers = dto.getStopovers().stream().map(s -> {
            Location location = new Location(s.getLatitude(), s.getLongitude(), s.getName());
            return Stopover.builder()
                    .stopoverStatus(StopoverStatus.valueOf(s.getStopoverStatus().toUpperCase()))
                    .location(location)
                    .ride(finalRide)
                    .build();
        }).collect(Collectors.toList());
        stopoverRepository.saveAll(stopovers);
        ride.setStopovers(stopovers);

        // Create and assign preferences

        List<Preference> preferences = dto.getPreferences().stream().map(desc -> {
            return Preference.builder()
                    .description(desc)
                    .ride(finalRide)
                    .build();
        }).collect(Collectors.toList());
        preferenceRepository.saveAll(preferences);
        ride.setPreferences(preferences);

        // Final save
        return rideRepository.save(ride);
    }




    @Override
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    @Override
    public Optional<Ride> getRideById(Long id) {
        return rideRepository.findById(id) ;
    }


    @Transactional(readOnly = true)
    public List<RideResponseDto> searchRides(RideSearchDTO searchCriteria) {
        System.out.println("Starting searchRides...");
        List<Ride> rides = rideRepository.findAll();
        System.out.println("Total rides fetched: " + rides.size());

        List<RideResponseDto> matchedRides = new ArrayList<>();

        LocalDate requestedDate = searchCriteria.getDate();
        LocalTime requestedArrivalTime = searchCriteria.getTime();
        System.out.println("Search criteria date: " + requestedDate + ", time: " + requestedArrivalTime);

        for (Ride ride : rides) {
            System.out.println("Checking ride: " + ride.getRideId());

            if (!ride.isPublished()) {
                System.out.println("Ride " + ride.getRideId() + " is not published, skipping...");
                continue;
            }

            if (!ride.getRideDate().isEqual(requestedDate)) {
                System.out.println("Ride " + ride.getRideId() + " date " + ride.getRideDate() + " does not match requested date " + requestedDate + ", skipping...");
                continue;
            }

            for (Stopover stopover : ride.getStopovers()) {
                System.out.println("Checking stopover at location: " + stopover.getLocation().getLatitude() + "," + stopover.getLocation().getLongitude());

                DistanceService.DistanceDurationResponse distanceDuration = distanceService.getDistanceAndDuration(
                        formatLocation(searchCriteria.getFromLocation().getLatitude(), searchCriteria.getFromLocation().getLongitude()),
                        formatLocation(stopover.getLocation().getLatitude(), stopover.getLocation().getLongitude())
                );

                if (distanceDuration == null) {
                    System.out.println("Distance service returned null, skipping stopover...");
                    continue;
                }

                double distanceInMeters = parseDistanceToMeters(distanceDuration.distanceText());
                System.out.println("Distance to stopover: " + distanceInMeters + " meters");

                if (distanceInMeters > searchCriteria.getRayonPossible()) {
                    System.out.println("Distance " + distanceInMeters + " is greater than allowed " + searchCriteria.getRayonPossible() + ", skipping stopover...");
                    continue;
                }

                LocalTime stopoverArrivalTime = ride.getRideTime();
                System.out.println("Ride time: " + stopoverArrivalTime + ", requested arrival time: " + requestedArrivalTime);

                if (stopoverArrivalTime.equals(requestedArrivalTime)) {
                    System.out.println("Matched ride " + ride.getRideId() + "! Creating RideResponseDto...");

                    RideResponseDto response = new RideResponseDto();
                    response.setDriverId(ride.getDriver().getUserId());
                    response.setDriverName(ride.getDriver().getFirstname() + " " + ride.getDriver().getLastname());
                    response.setCarName(ride.getVehicle().getBrand() + " " + ride.getVehicle().getModel());
                    response.setRateDriver(ride.getDriver().getRating()); // Assuming driver has a rate field
                    response.setStopoverName(stopover.getLocation().getName()); // Assuming Location has a name
                    response.setDistanceBetween(distanceDuration.distanceText());
                    response.setPrefrences(
                            ride.getPreferences()
                                    .stream()
                                    .map(Preference::getDescription)
                                    .toList()
                    );


                    matchedRides.add(response);
                    break; // Stop checking other stopovers once matched
                } else {
                    System.out.println("Ride time does not match requested time.");
                }
            }
        }

        System.out.println("Total matched rides: " + matchedRides.size());
        return matchedRides;
    }



    // Helper to format location
    private String formatLocation(double latitude, double longitude) {
        return latitude + "," + longitude;
    }

    // Helper to parse "2.3 km" to meters
    private double parseDistanceToMeters(String distanceText) {
        distanceText = distanceText.replace(",", "."); // Replace , with . if necessary
        if (distanceText.contains("km")) {
            double kmValue = Double.parseDouble(distanceText.replace("km", "").trim());
            return kmValue * 1000;
        } else if (distanceText.contains("m")) {
            return Double.parseDouble(distanceText.replace("m", "").trim());
        } else {
            throw new IllegalArgumentException("Unknown distance format: " + distanceText);
        }
    }






    @Override
    public Optional<Ride> getRideByDate(LocalDate date){
        return rideRepository.getRideByRideDate(date) ;
    }

    @Override
    public Optional<Ride> getRideByTime(LocalTime time){
        return rideRepository.getRidesByRideTime(time) ;
    }




    @Override
    public void deleteRide(Long id) {
        rideRepository.deleteById(id);
    }



    @Override
    public Optional<Ride> updateRideById(Long id , Ride ride) {
        Optional<Ride> optionalRide = rideRepository.findById(id);
        if (optionalRide.isPresent()) {
            rideRepository.save(ride);
            return Optional.of(ride);
        }else {
            return Optional.empty();
        }
    }



    @Override
    public Optional<Ride> updateAvailableSeats(Long rideId, int seatsBooked) {
        return rideRepository.findById(rideId).map(ride -> {
            int updatedSeats = ride.getAvailableSeats() - seatsBooked;
            ride.setAvailableSeats(Math.max(updatedSeats, 0));
            return rideRepository.save(ride);
        });
    }


}

