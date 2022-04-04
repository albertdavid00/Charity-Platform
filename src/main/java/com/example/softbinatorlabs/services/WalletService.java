package com.example.softbinatorlabs.services;

import com.example.softbinatorlabs.dtos.WalletDto;
import com.example.softbinatorlabs.models.User;
import com.example.softbinatorlabs.models.Wallet;
import com.example.softbinatorlabs.repositories.UserRepository;
import com.example.softbinatorlabs.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;

@Service
public class WalletService {
    
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    
    @Autowired
    public WalletService(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    public Wallet getWallet(Long userId, Long currentUserId) {
        if (userId.equals(currentUserId)){
            return walletRepository.getByUserId(userId);
        }
        else throw new BadRequestException("You can't access the wallet of another user");
    }

    public void addFunds(Long walletId, WalletDto walletDto, Long userId) {
        Wallet wallet = walletRepository.getByUserId(userId);
        if (wallet.getId().equals(walletId)){
            Double balance = walletDto.getBalance();
            walletRepository.depositById(walletId, balance);
        }
        else throw new BadRequestException("You can't add funds to another wallet, but yours.");
    }

    public Wallet createWallet(User user) {
        Wallet wallet = Wallet.builder()
                .balance(0.0)
                .user(user)
                .build();
        walletRepository.save(wallet);
        return wallet;
    }
}
