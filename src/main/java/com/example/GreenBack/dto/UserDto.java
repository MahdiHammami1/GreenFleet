package com.example.GreenBack.dto;


import com.example.GreenBack.enums.Badge;
import com.example.GreenBack.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Double rating;
    private Integer gamificationPoints;
    private boolean verified;
    private String profilePictureUrl;
    private List<Long> publishedRides;
    private List<Long> vehicles;
    private List<Long> bookings;
    private List<Badge> badges;
}
