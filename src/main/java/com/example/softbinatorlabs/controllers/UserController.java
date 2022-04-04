package com.example.softbinatorlabs.controllers;

import com.example.softbinatorlabs.dtos.RegisterUserDto;
import com.example.softbinatorlabs.services.UserService;
import com.example.softbinatorlabs.utility.KeycloakHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public ResponseEntity<?> getCurrentUser(Authentication authentication){
        return new ResponseEntity<>(userService.getUser(Long.parseLong(KeycloakHelper.getUser(authentication))),
                HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDto registerUserDto) {
        return new ResponseEntity<>(userService.registerUser(registerUserDto, "ROLE_USER"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, Authentication authentication){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterUserDto registerUserDto) {
        return new ResponseEntity<>(userService.registerUser(registerUserDto, "ROLE_ADMIN"), HttpStatus.OK);
    }
}
