package com.example.softbinatorlabs.controllers;

import com.example.softbinatorlabs.dtos.LoginDto;
import com.example.softbinatorlabs.dtos.RefreshTokenDto;
import com.example.softbinatorlabs.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Daca puneam @RequestBody inainte de LoginDto puteam trimite json-uri
    // Fara el, putem trimite request-uri cu x-www-form-urlencoded din Postman/Frontend
    @PostMapping("/login")
    public ResponseEntity<?> login(LoginDto loginDto) {
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(RefreshTokenDto refreshTokenDto) {
        return new ResponseEntity<>(authService.refresh(refreshTokenDto), HttpStatus.OK);
    }

}

