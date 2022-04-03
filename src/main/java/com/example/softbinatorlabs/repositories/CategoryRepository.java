package com.example.softbinatorlabs.repositories;

import com.example.softbinatorlabs.models.Category;
import com.example.softbinatorlabs.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("UPDATE Category c set c.name = :name where c.id =:id")
    @Transactional
    @Modifying
    void updateCategory(String name, Long id);
}
