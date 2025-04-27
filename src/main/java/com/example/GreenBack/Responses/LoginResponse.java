package com.example.GreenBack.Responses;

import com.example.GreenBack.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private long expiresIn;
    private UserDto user;

    public LoginResponse(String token, long expiresIn,UserDto user) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.user=user;
    }
}