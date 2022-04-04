package com.example.softbinatorlabs.repositories;

import com.example.softbinatorlabs.models.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {

}
