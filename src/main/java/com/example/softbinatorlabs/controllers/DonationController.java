package com.example.softbinatorlabs.controllers;


import com.example.softbinatorlabs.dtos.DonationDto;
import com.example.softbinatorlabs.services.DonationService;
import com.example.softbinatorlabs.utility.KeycloakHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/donations")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping("/donate/{eventId}")
    public ResponseEntity<?> makeDonation(@PathVariable Long eventId, @RequestBody  DonationDto donationDto, Authentication authentication){
        donationService.makeDonation(eventId, donationDto, Long.parseLong(KeycloakHelper.getUser(authentication)));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
