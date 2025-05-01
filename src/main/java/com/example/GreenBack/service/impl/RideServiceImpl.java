package com.example.GreenBack.service.impl;

import com.example.GreenBack.dto.*;
import com.example.GreenBack.entity.*;
import com.example.GreenBack.enums.BookingStatus;
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
    private final BookingRepository bookingRepository;


    public RideServiceImpl(RideRepository rideRepository, UserRepository userRepository, VehicleRepository vehicleRepository, StopoverRepository stopoverRepository, PreferenceRepository preferenceRepository, DistanceService distanceService,
                           BookingRepository bookingRepository) {
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;

        this.stopoverRepository = stopoverRepository;
        this.preferenceRepository = preferenceRepository;
        this.distanceService = distanceService;
        this.bookingRepository = bookingRepository;
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
        System.out.println("Starting searchRides from stopovers...");

        List<RideResponseDto> matchedRides = new ArrayList<>();
        LocalDate requestedDate = searchCriteria.getDate();
        LocalTime requestedArrivalTime = searchCriteria.getTime();

        List<Stopover> stopovers = stopoverRepository.findAll(); // Ideally filter this with radius directly in DB if possible

        for (Stopover stopover : stopovers) {
            Location stopoverLocation = stopover.getLocation();

            DistanceService.DistanceDurationResponse distanceDuration = distanceService.getDistanceAndDuration(
                    formatLocation(searchCriteria.getFromLocation().getLatitude(), searchCriteria.getFromLocation().getLongitude()),
                    formatLocation(stopoverLocation.getLatitude(), stopoverLocation.getLongitude())
            );

            if (distanceDuration == null) {
                System.out.println("Distance service returned null, skipping stopover...");
                continue;
            }

            double distanceInMeters = parseDistanceToMeters(distanceDuration.distanceText());
            if (distanceInMeters > searchCriteria.getRayonPossible() * 1000) {
                continue;
            }

            Ride ride = stopover.getRide();

            if (!ride.isPublished()) continue;
            if (!ride.getRideDate().isEqual(requestedDate)) continue;
            if (!ride.getRideTime().equals(requestedArrivalTime)) continue;

            RideResponseDto response = new RideResponseDto();
            response.setRideId(ride.getRideId());
            response.setDriverId(ride.getDriver().getUserId());
            response.setDriverName(ride.getDriver().getFirstname() + " " + ride.getDriver().getLastname());
            response.setCarName(ride.getVehicle().getBrand() + " " + ride.getVehicle().getModel());
            response.setRateDriver(ride.getDriver().getRating());
            response.setStopoverName(stopoverLocation.getName());
            response.setDistanceBetween(distanceDuration.distanceText());
            response.setPrefrences(
                    ride.getPreferences().stream()
                            .map(Preference::getDescription)
                            .toList()
            );

            matchedRides.add(response);

        }

        // Sort: by rating desc, distance asc
        matchedRides.sort((r1, r2) -> {
            int rateCompare = Double.compare(r2.getRateDriver(), r1.getRateDriver());
            if (rateCompare != 0) return rateCompare;
            double d1 = parseDistanceToMeters(r1.getDistanceBetween());
            double d2 = parseDistanceToMeters(r2.getDistanceBetween());
            return Double.compare(d1, d2);
        });

        return matchedRides;
    }



    // Helper to format location
    private String formatLocation(double latitude, double longitude) {
        return latitude + "," + longitude;
    }

    // Helper to parse "2.3 km" to meters
    private double parseDistanceToMeters(String distanceText) {
        if (distanceText == null || distanceText.isEmpty()) {
            throw new IllegalArgumentException("Distance text is null or empty");
        }

        distanceText = distanceText.replace(",", "."); // Replace , with .
        try {
            if (distanceText.contains("km")) {
                double kmValue = Double.parseDouble(distanceText.replace("km", "").trim());
                return kmValue * 1000;
            } else if (distanceText.contains("m")) {
                return Double.parseDouble(distanceText.replace("m", "").trim());
            } else {
                throw new IllegalArgumentException("Unknown distance format: " + distanceText);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Failed to parse distance: " + distanceText, e);
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



    public RideExtendedDto convertToRideExtendedDto(Ride ride) {
        RideExtendedDto rideExtendedDto = new RideExtendedDto();

        List<StopoverDTO> stopoverDTOs = ride.getStopovers().stream()
                .map(stopover -> {
                    StopoverDTO dto = new StopoverDTO();
                    dto.setStopoverStatus(String.valueOf(stopover.getStopoverStatus()));
                    dto.setLatitude(stopover.getLocation().getLatitude());
                    dto.setLongitude(stopover.getLocation().getLongitude());
                    dto.setName(stopover.getLocation().getName());
                    return dto;
                })
                .toList();

        List<String> preferenceStrings = ride.getPreferences().stream()
                .map(preference -> preference.getDescription())
                .toList();


        List<Booking> bookings = bookingRepository.findByRide_RideId(ride.getRideId());
        List<RequestBookingDto> bookingDtoList = bookings.stream()
                .map(booking -> {
                    RequestBookingDto dto = new RequestBookingDto();
                    dto.setRideId(ride.getRideId());
                    dto.setPassengerName(booking.getPassenger().getFirstname() + " " + booking.getPassenger().getLastname());
                    dto.setPassengerId(booking.getPassenger().getUserId());
                    dto.setPassengerRate(booking.getPassenger().getRating());
                    dto.setStopoverName(booking.getPickupLocation());
                    dto.setBookingStatus(booking.getBookingStatus());
                    return dto;
                })
                .toList();

        return new RideExtendedDto(
                ride.getRideId(),
                ride.getRideDate(),
                ride.getRideTime(),
                ride.getNumberOfSeat(),
                ride.isPublished(),
                ride.getAvailableSeats(),
                ride.getDriver().getUserId(),
                ride.getVehicle().getVehicleId(),
                preferenceStrings,
                stopoverDTOs,
                bookingDtoList
        );
    }



    @Transactional
    public void updateStart(Long rideId, int stopoverIndex) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        List<Booking> bookings = bookingRepository.findByRide_RideId(rideId);

        if (stopoverIndex == 0) {
            for (Booking booking : bookings) {
                if (booking.getBookingStatus() == BookingStatus.ACCEPTED) {
                    booking.setBookingStatus(BookingStatus.ONGOING);
                }
            }
        } else if (stopoverIndex == 1) {
            for (Booking booking : bookings) {
                if (booking.getBookingStatus() == BookingStatus.ONGOING) {
                    booking.setBookingStatus(BookingStatus.REACHED);
                }
            }
        }

        bookingRepository.saveAll(bookings);
    }




    @Transactional
    public void updateAccept(Long rideId, Long passengerId, boolean accept) {
        List<Booking> bookings = bookingRepository.findByRide_RideId(rideId);
        for (Booking booking : bookings) {
            if (booking.getPassenger().getUserId() == passengerId) {
                booking.setBookingStatus(accept?BookingStatus.ACCEPTED:BookingStatus.REJECTED);
                bookingRepository.save(booking);
            }
        }

    }



}


// Start -> rides/updateStart/rideId  ->  update all stopovers Status to ongoing
// accept -> rides/updateAccept/rideId/passengerId   -> update the booking of the ride Id of passenger with passenger Id to ACCEPTEED
// decline -> rides/updateAccept/rideId/passengerId   -> update the booking of the ride Id of passenger with passenger Id to ACCEPTEED
// reached -> rides.updateReached/rideId


