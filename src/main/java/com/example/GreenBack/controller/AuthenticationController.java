package com.example.GreenBack.controller;

import com.example.GreenBack.Responses.LoginResponse;
import com.example.GreenBack.dto.*;
import com.example.GreenBack.entity.User;
import com.example.GreenBack.mapper.UserMapper;
import com.example.GreenBack.service.impl.AuthenticationService;
import com.example.GreenBack.service.impl.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;


    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/admin/signup")
    public ResponseEntity<String> registerAdmin(
            @Valid @RequestBody AdminSignupDto signupDto) {
        String jwt = authenticationService.registerAdmin(signupDto);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<String> loginAdmin(
            @Valid @RequestBody LoginUserDto loginDto) {
        String jwt = authenticationService.loginAdmin(loginDto);
        return ResponseEntity.ok(jwt);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        UserDto userDto = UserMapper.toDto(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getJwtExpiration(), userDto);
        return ResponseEntity.ok(loginResponse);
    }


    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
        try {
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Account verified successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}