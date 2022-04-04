package com.example.softbinatorlabs.controllers;

import com.example.softbinatorlabs.dtos.WalletDto;
import com.example.softbinatorlabs.services.WalletService;
import com.example.softbinatorlabs.utility.KeycloakHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("")
    public ResponseEntity<?> getWallet(Authentication authentication){
        return new ResponseEntity<>(walletService.getWallet(
                                    Long.parseLong(KeycloakHelper.getUser(authentication))),
                                    HttpStatus.OK);
    }
    @PutMapping("/add-funds")
    public ResponseEntity<?> addFunds(@RequestBody WalletDto walletDto, Authentication authentication){
        walletService.addFunds(walletDto, Long.parseLong(KeycloakHelper.getUser(authentication)));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
