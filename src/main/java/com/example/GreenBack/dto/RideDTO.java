package com.example.GreenBack.dto;

import com.example.GreenBack.entity.Ride;
import com.example.GreenBack.entity.Stopover;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideDTO {
    private Long rideId;
    private LocalDate rideDate;
    private LocalTime rideTime;
    private int numberOfSeat;
    private boolean published;
    private int availableSeats;

    private Long driverId;
    private Long carId;

    private List<String> preferences;
    private List<StopoverDTO> stopovers;

    public static RideDTO convertToDTO(Ride ride) {
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

        // Convert Preference entities to strings
        List<String> preferenceStrings = ride.getPreferences().stream()
                .map(preference -> preference.getDescription())
                .toList();

        return new RideDTO(
                ride.getRideId(),
                ride.getRideDate(),
                ride.getRideTime(),
                ride.getNumberOfSeat(),
                ride.isPublished(),
                ride.getAvailableSeats(),
                ride.getDriver().getUserId(),
                ride.getVehicle().getVehicleId(),
                preferenceStrings,
                stopoverDTOs
        );
    }
}