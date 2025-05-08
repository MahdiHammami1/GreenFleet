package com.example.GreenBack.service.impl;

import com.example.GreenBack.dto.*;
import com.example.GreenBack.entity.*;
import com.example.GreenBack.enums.Badge;
import com.example.GreenBack.enums.Status;
import com.example.GreenBack.repository.*;
import com.example.GreenBack.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;




import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final ChatMessageRepository chatMessageRepository;



    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void saveUser(User user){
        user.setStatus(Status.ONLINE);
        userRepository.save(user);

    };
    public void disconnect(User user){
        var storedUser=userRepository.findById(user.getUserId()).orElse(null);
        if(storedUser!=null){
            storedUser.setStatus(Status.OFFLINE);
        }

    }

    public List<User> findConnectedUser(){
        return userRepository.findAllByStatus(Status.ONLINE);

    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserDto> getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(this::convertToDto);
    }

    private UserDto convertToDto(User user) {
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
                .publishedRides(user.getPublishedRides().stream()
                        .map(Ride::getRideId)
                        .collect(Collectors.toList()))
                .vehicles(user.getVehicles().stream()
                        .map(Vehicle::getVehicleId)
                        .collect(Collectors.toList()))
                .bookings(user.getBookings().stream()
                        .map(Booking::getBookingId)
                        .collect(Collectors.toList()))
                .badges(user.getBadges())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        return userRepository.findByEmail(email)
                .map(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public UserDto updateUser(Long userId, UserUpdateDto updateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        user.setFirstname(updateDto.getFirstname());
        user.setLastname(updateDto.getLastname());
        user.setPhoneNumber(updateDto.getPhoneNumber());
        user.setDateOfBirth(updateDto.getDateOfBirth());
        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


    public List<topUserDto> getTopTenUsersByGamificationPoints() {
        List<User> topUsers = userRepository.findTop10ByOrderByGamificationPointsDesc();
        return topUsers.stream()
                .map(this::convertToTopUserDto)
                .collect(Collectors.toList());
    }
    private topUserDto convertToTopUserDto(User user) {
        topUserDto dto = new topUserDto();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        if (user.getBadges() != null && !user.getBadges().isEmpty()) {
            dto.setBadge(user.getBadges().get(0).toString());
        } else {
            dto.setBadge(null);
        }
        dto.setRating(user.getRating());
        dto.setGamificationPoints(user.getGamificationPoints());
        return dto;
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

    @Transactional
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BadCredentialsException("Invalid current password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

    }



    @Transactional
    @Override
    public void updateUserPoints(Long userId, int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        user.setGamificationPoints(user.getGamificationPoints() + points);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void addBadgeToUser(Long userId, Badge badge) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

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
    private VehicleDTO convertToVehicleDto(Vehicle vehicle) {
        return VehicleDTO.builder()
                .vehicleId(vehicle.getVehicleId())
                .licenceNumber(vehicle.getLicenceNumber())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .numberOfSeat(vehicle.getNumberOfSeat())
                .registrationDate(vehicle.getRegistrationDate())
                .pictureUrl(vehicle.getPictureUrl())
                .ownerId(vehicle.getOwner() != null ? vehicle.getOwner().getUserId() : null)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDTO> getUserVehicles(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        return user.getVehicles().stream()
                .map(this::convertToVehicleDto)
                .collect(Collectors.toList());
    }




    @Override
    public double getUserTotalCO2Saved(Long userId) {
        // chnowee l logic !! ?
        return 0.0;
    }

    @Override
    public void toggleUserBanned(Long userId, boolean banned) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setEnabled(banned);
        userRepository.save(user);
    }

    @Override
    public void banUser(Long userId) {
        toggleUserBanned(userId, true);
    }

    @Override
    public void unbanUser(Long userId) {
        toggleUserBanned(userId, false);
    }

    public String getProfileImageFilename(Long userId){
            User user=userRepository.findById(userId).orElseThrow();
            return user.getProfilePictureUrl();
    }


    public List<FriendChatDTO> getChatFriends(String userId) {
        List<ChatMessage> messages = chatMessageRepository.findMessagesInvolvingUser(userId);

        Map<String, ChatMessage> lastMessages = new HashMap<>();
        for (ChatMessage msg : messages) {
            String friendId = msg.getSenderId().equals(userId) ? msg.getRecipientId() : msg.getSenderId();
            if (!lastMessages.containsKey(friendId) || msg.getTimestamp().after(lastMessages.get(friendId).getTimestamp())) {
                lastMessages.put(friendId, msg);
            }
        }

        List<Long> friendIds = lastMessages.keySet().stream().map(Long::valueOf).toList();
        List<User> users = userRepository.findAllByIdIn(friendIds);

        return users.stream().map(user -> {
            ChatMessage lastMsg = lastMessages.get(String.valueOf(user.getUserId()));
            return FriendChatDTO.builder()
                    .id(user.getUserId())
                    .firstName(user.getFirstname())
                    .lastName(user.getLastname())
                    .profilePicture(user.getProfilePictureUrl())
                    .lastMessage(lastMsg.getContent())
                    .lastMessageTime(lastMsg.getTimestamp())
                    .build();
        }).toList();
    }





}
