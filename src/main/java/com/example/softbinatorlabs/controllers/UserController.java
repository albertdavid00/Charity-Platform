package com.example.softbinatorlabs.controllers;

import com.example.softbinatorlabs.dtos.RegisterUserDto;
import com.example.softbinatorlabs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @GetMapping("/info")
//    public ResponseEntity<?> getDetails(Authentication authentication) {
//        return new ResponseEntity<>(KeycloakHelper.getUser(authentication), HttpStatus.OK);
//    }
//
    @GetMapping("")
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDto registerUserDto) {
        return new ResponseEntity<>(userService.registerUser(registerUserDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, Authentication authentication){
        //TODO
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
