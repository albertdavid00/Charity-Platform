package com.example.softbinatorlabs.controllers;

import com.example.softbinatorlabs.dtos.WalletDto;
import com.example.softbinatorlabs.services.WalletService;
import com.example.softbinatorlabs.utility.KeycloakHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @RequestMapping("/user/{userId}")
    public ResponseEntity<?> getWallet(@PathVariable Long userId, Authentication authentication){
        return new ResponseEntity<>(walletService.getWallet(
                                    userId,
                                    Long.parseLong(KeycloakHelper.getUser(authentication))),
                                    HttpStatus.OK);
    }
    @RequestMapping("/add-funds/{walletId}")
    public ResponseEntity<?> addFunds(@PathVariable Long walletId, @RequestBody WalletDto walletDto, Authentication authentication){
        walletService.addFunds(walletId, walletDto, Long.parseLong(KeycloakHelper.getUser(authentication)));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
