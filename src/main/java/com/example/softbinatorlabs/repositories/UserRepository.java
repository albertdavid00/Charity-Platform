package com.example.softbinatorlabs.repositories;

import com.example.softbinatorlabs.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Query-urile sunt generate automat
    // Putem adauga si noi query-uri cu annotation @Query()
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmailQuery(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u where u.id = :id")
    void deleteById(Long id);

    Boolean existsByEmail(String email);
}
