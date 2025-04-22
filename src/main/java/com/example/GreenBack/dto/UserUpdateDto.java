package com.example.GreenBack.dto;

import com.example.GreenBack.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private LocalDate dateOfBirth;
}