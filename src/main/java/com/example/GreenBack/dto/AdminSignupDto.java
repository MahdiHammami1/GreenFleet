package com.example.GreenBack.dto;

import lombok.Data;

@Data
public class AdminSignupDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;
}