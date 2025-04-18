package com.example.GreenBack.service;

import com.example.GreenBack.entity.Preference;
import com.example.GreenBack.entity.Ride;
import com.example.GreenBack.entity.User;
import com.example.GreenBack.entity.Vehicle;
import com.example.GreenBack.enums.Badge;

import java.util.List;
import java.util.Optional;

public interface UserService {


    User createUser(User user);
    Optional<User> getUserById(Long userId);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    User updateUser(Long userId, User updatedUser);
    void deleteUser(Long userId);



    void updateProfilePicture(Long userId, String imageUrl);
    void updatePhoneNumber(Long userId, String phoneNumber);
    void changePassword(Long userId, String oldPassword, String newPassword);
    void updateUserPreferences(Long userId, Preference preference);


    void updateUserPoints(Long userId, int points);
    void addBadgeToUser(Long userId, Long badgeId);
    List<Badge> getUserBadges(Long userId);

    void addVehicleToUser(Long userId, Vehicle vehicle);
    void removeVehicleFromUser(Long userId, Long vehicleId);
    List<Vehicle> getUserVehicles(Long userId);


    double getUserTotalCO2Saved(Long userId);


    void toggleUserVerificationStatus(Long userId, boolean verified);
    void banUser(Long userId);
    void unbanUser(Long userId);


}
