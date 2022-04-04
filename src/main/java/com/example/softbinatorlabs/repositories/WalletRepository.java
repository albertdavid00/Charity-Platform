package com.example.softbinatorlabs.repositories;

import com.example.softbinatorlabs.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query("SELECT w FROM Wallet w where w.user.id = :userId")
    Wallet getByUserId(Long userId);

    @Query("UPDATE Wallet w set w.balance = :balance where w.id = :walletId")
    @Transactional
    @Modifying
    void depositById(Long walletId, Double balance);

}
