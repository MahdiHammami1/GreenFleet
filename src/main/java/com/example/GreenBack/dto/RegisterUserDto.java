package com.example.GreenBack.dto;

import com.example.GreenBack.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterUserDto {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String phoneNumber ;
    private LocalDate dateOfBirth;


    @Enumerated(EnumType.STRING)
    private Gender gender;
}