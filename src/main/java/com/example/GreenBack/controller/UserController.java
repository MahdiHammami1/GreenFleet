package com.example.GreenBack.controller;

import com.example.GreenBack.dto.ChangePasswordRequestDto;
import com.example.GreenBack.dto.UserDto;
import com.example.GreenBack.dto.UserUpdateDto;
import com.example.GreenBack.dto.VehicleDTO;
import com.example.GreenBack.entity.*;
import com.example.GreenBack.enums.Badge;
import com.example.GreenBack.service.impl.ImageStorageService;
import com.example.GreenBack.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final ImageStorageService imageStorageService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/upload/{userId}")
    public ResponseEntity<String> uploadProfilePicture(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) {

        try {
            String fileUrl = imageStorageService.uploadImage(file);
            userService.updateProfilePicture(userId, fileUrl); // save only the filename if possible
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @GetMapping("/image/{userId}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable Long userId) {
        String filename = userService.getProfileImageFilename(userId); // should return just the filename (not full path)
        Resource image = imageStorageService.loadAsResource(filename);

        String contentType = "image/png"; // or detect dynamically (see below)

        return ResponseEntity.ok()
                .header("Content-Type", contentType)
                .body(image);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateDto updateDto) {
        return ResponseEntity.ok(userService.updateUser(userId, updateDto));
    }


    @PatchMapping("/{userId}/profile-picture")
    public ResponseEntity<Void> updateProfilePicture(@PathVariable Long userId, @RequestParam String imageUrl) {
        userService.updateProfilePicture(userId, imageUrl);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/{userId}/password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long userId,
            @RequestBody ChangePasswordRequestDto request

    ) {
        userService.changePassword(userId, request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}/points")
    public ResponseEntity<Void> updateUserPoints(
            @PathVariable Long userId,
            @RequestParam @Min(0) int points) {
        userService.updateUserPoints(userId, points);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/badges/{badgeName}")
    public ResponseEntity<Void> addBadgeToUser(
            @PathVariable Long userId,
            @PathVariable String badgeName) {
        Badge badge = Badge.valueOf(badgeName.toUpperCase());
        userService.addBadgeToUser(userId, badge);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/badges")
    public ResponseEntity<List<Badge>> getUserBadges(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserBadges(userId));
    }



    @GetMapping("/{userId}/vehicles")
    public ResponseEntity<List<VehicleDTO>> getUserVehicles(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserVehicles(userId));
    }

    @GetMapping("/{userId}/co2")
    public ResponseEntity<Double> getUserTotalCO2Saved(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserTotalCO2Saved(userId));
    }


    @PatchMapping("/{userId}/ban")
    public ResponseEntity<Void> banUser(@PathVariable Long userId) {
        userService.banUser(userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}/unban")
    public ResponseEntity<Void> unbanUser(@PathVariable Long userId) {
        userService.unbanUser(userId);
        return ResponseEntity.ok().build();
    }





    @MessageMapping("/user.addUser")
    public User addUser(User user) {
        userService.saveUser(user);
        // Send message to the specific user after they are added
        messagingTemplate.convertAndSend(
                "/user/" + user.getUserId() + "/queue/messages", user);
        return user;
    }

    @MessageMapping("/user.disconnectUser")
    public User disconnectUser(User user) {
        userService.disconnect(user);
        // Notify other users
        messagingTemplate.convertAndSend("/user/public", user);
        return user;
    }

    @GetMapping("/users/online")
    public List<User> getOnlineUsers() {
        return userService.findConnectedUser();
    }


}
