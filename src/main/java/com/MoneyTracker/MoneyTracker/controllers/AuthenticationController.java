package com.MoneyTracker.MoneyTracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MoneyTracker.MoneyTracker.models.DTOs.UserLoginDTO;
import com.MoneyTracker.MoneyTracker.models.DTOs.UserSignupDTO;
import com.MoneyTracker.MoneyTracker.services.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody UserSignupDTO signupDTO) {
        String response = authenticationService.signup(signupDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        String token = authenticationService.login(loginDTO);
        return ResponseEntity.ok(token);
    }
}