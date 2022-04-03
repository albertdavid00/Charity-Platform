package com.example.softbinatorlabs.repositories;

import com.example.softbinatorlabs.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c where c.event.id =:eventId")
    List<Comment> findAllByEventId(Long eventId);

    @Query("UPDATE Comment c set c.message = :message where c.id =:commentId")
    @Transactional
    @Modifying
    void updateById(Long commentId, String message);

    @Query("SELECT c FROM Comment c where c.user.id =:userId")
    List<Comment> findAllByUserId(Long userId);
}
