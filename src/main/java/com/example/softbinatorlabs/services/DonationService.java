package com.example.softbinatorlabs.services;

import com.example.softbinatorlabs.dtos.DonationDto;
import com.example.softbinatorlabs.models.Donation;
import com.example.softbinatorlabs.models.Event;
import com.example.softbinatorlabs.models.User;
import com.example.softbinatorlabs.models.Wallet;
import com.example.softbinatorlabs.repositories.DonationRepository;
import com.example.softbinatorlabs.repositories.EventRepository;
import com.example.softbinatorlabs.repositories.UserRepository;
import com.example.softbinatorlabs.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.time.LocalDateTime;

@Service
public class DonationService {
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final WalletRepository walletRepository;
    @Autowired
    public DonationService(DonationRepository donationRepository, UserRepository userRepository, EventRepository eventRepository, WalletRepository walletRepository) {
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.walletRepository = walletRepository;
    }


    public void makeDonation(Long eventId, DonationDto donationDto, Long userId) {
        User user = userRepository.getById(userId);
        Event event = eventRepository.getById(eventId);
        Wallet wallet = user.getWallet();
        Double balance = wallet.getBalance();
        Double amount = donationDto.getAmount();
        if (balance >= amount){
            Donation donation = Donation.builder()
                    .message(donationDto.getMessage())
                    .amount(donationDto.getAmount())
                    .dateTime(LocalDateTime.now())
                    .event(event)
                    .user(user)
                    .build();
            wallet.setBalance(balance - amount);
            Double eventMoney = event.getCurrentAmount();
            event.setCurrentAmount(eventMoney + amount);
            eventRepository.save(event);
            walletRepository.save(wallet);
            donationRepository.save(donation);
        }
        else throw new BadRequestException("Not enough funds");

    }
}
