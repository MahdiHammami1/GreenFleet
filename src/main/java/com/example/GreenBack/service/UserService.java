package com.example.GreenBack.service;

import com.example.GreenBack.dto.UserDto;
import com.example.GreenBack.dto.UserUpdateDto;
import com.example.GreenBack.dto.VehicleDTO;
import com.example.GreenBack.entity.Preference;
import com.example.GreenBack.entity.Ride;
import com.example.GreenBack.entity.User;
import com.example.GreenBack.entity.Vehicle;
import com.example.GreenBack.enums.Badge;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {


    User createUser(User user);
    Optional<UserDto> getUserById(Long userId);
    Optional<UserDto> getUserByEmail(String email);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long userId, UserUpdateDto updateDto);
    void deleteUser(Long userId);
    void updateProfilePicture(Long userId, String imageUrl);
    void updatePhoneNumber(Long userId, String phoneNumber);
    void changePassword(Long userId, String oldPassword, String newPassword);
    void updateUserPoints(Long userId, int points);
    void addBadgeToUser(Long userId, Badge badge);
    List<Badge> getUserBadges(Long userId);
    void addVehicleToUser(Long userId, Vehicle vehicle);
    void removeVehicleFromUser(Long userId, Long vehicleId);
    List<VehicleDTO> getUserVehicles(Long userId);
    double getUserTotalCO2Saved(Long userId);
    void toggleUserBanned(Long userId, boolean verified);
    void banUser(Long userId);
    void unbanUser(Long userId);


}
