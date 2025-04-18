package com.example.GreenBack.service.impl;

import com.example.GreenBack.entity.*;
import com.example.GreenBack.enums.Badge;
import com.example.GreenBack.repository.*;
import com.example.GreenBack.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final RideRepository rideRepository;
    private final BookingRepository bookingRepository;
    private final PreferenceRepository preferenceRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long userId, User updatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setFirstname(updatedUser.getFirstname());
        user.setLastname(updatedUser.getLastname());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setGender(updatedUser.getGender());
        user.setDateOfBirth(updatedUser.getDateOfBirth());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }



    @Override
    public void updateProfilePicture(Long userId, String imageUrl) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setProfilePictureUrl(imageUrl);
        userRepository.save(user);
    }

    @Override
    public void updatePhoneNumber(Long userId, String phoneNumber) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow();
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Old password does not match.");
        }
    }

    @Override
    public void updateUserPreferences(Long userId, Preference preference) {
        preferenceRepository.save(preference);
        // Assume user has one preference instance
    }

    @Override
    public void updateUserPoints(Long userId, int points) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setGamificationPoints(points);
        userRepository.save(user);
    }

    @Override
    public void addBadgeToUser(Long userId, Long badgeId) {
        User user = userRepository.findById(userId).orElseThrow();
        Badge badge = Badge.values()[badgeId.intValue()];
        user.getBadges().add(badge);
        userRepository.save(user);
    }

    @Override
    public List<Badge> getUserBadges(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return user.getBadges();
    }

    @Override
    public void addVehicleToUser(Long userId, Vehicle vehicle) {
        User user = userRepository.findById(userId).orElseThrow();
        vehicle.setOwner(user);
        vehicleRepository.save(vehicle);
    }

    @Override
    public void removeVehicleFromUser(Long userId, Long vehicleId) {
        vehicleRepository.deleteById(vehicleId);
    }

    @Override
    public List<Vehicle> getUserVehicles(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return user.getVehicles();
    }



    @Override
    public double getUserTotalCO2Saved(Long userId) {
        // Implement CO2 calculation logic
        return 0.0;
    }

    @Override
    public void toggleUserVerificationStatus(Long userId, boolean verified) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setVerified(verified);
        userRepository.save(user);
    }

    @Override
    public void banUser(Long userId) {
        toggleUserVerificationStatus(userId, false);
    }

    @Override
    public void unbanUser(Long userId) {
        toggleUserVerificationStatus(userId, true);
    }


}
