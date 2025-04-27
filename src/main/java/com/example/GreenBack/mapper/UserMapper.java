package com.example.GreenBack.mapper;

import com.example.GreenBack.dto.UserDto;
import com.example.GreenBack.entity.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        if (user == null) return null;

        return UserDto.builder()
                .userId(user.getUserId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .rating(user.getRating())
                .gamificationPoints(user.getGamificationPoints())
                .verified(user.isVerified())
                .profilePictureUrl(user.getProfilePictureUrl())
                .publishedRides(
                        user.getPublishedRides() != null
                                ? user.getPublishedRides().stream().map(ride -> ride.getRideId()).toList()
                                : null
                )
                .vehicles(
                        user.getVehicles() != null
                                ? user.getVehicles().stream().map(vehicle -> vehicle.getVehicleId()).toList()
                                : null
                )
                .bookings(
                        user.getBookings() != null
                                ? user.getBookings().stream().map(booking -> booking.getBookingId()).toList()
                                : null
                )
                .badges(user.getBadges())
                .build();
    }
}



