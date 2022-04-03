package com.example.softbinatorlabs.services;

import com.example.softbinatorlabs.dtos.CommentDto;
import com.example.softbinatorlabs.models.Comment;
import com.example.softbinatorlabs.repositories.CommentRepository;
import com.example.softbinatorlabs.repositories.EventRepository;
import com.example.softbinatorlabs.repositories.UserRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;

    @Autowired
    public CommentService(UserRepository userRepository, CommentRepository commentRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.eventRepository = eventRepository;
    }


    public List<Comment> getEventComments(Long eventId) {
        if(eventRepository.existsById(eventId)){
            return commentRepository.findAllByEventId(eventId);
        }
        else throw new BadRequestException("Event with id " + eventId + " doesn't exist");
    }

    public void addComment(CommentDto commentDto, Long eventId, Long userId) {
        if(eventRepository.existsById(eventId)){
            Comment comment = Comment.builder()
                    .message(commentDto.getMessage())
                    .dateTime(LocalDateTime.now())
                    .user(userRepository.getById(userId))
                    .event(eventRepository.getById(eventId))
                    .build();
            commentRepository.save(comment);
        }
        else throw new BadRequestException("Event with id " + eventId + " doesn't exist");
    }


    public void deleteComment(Long commentId, Long userId, Boolean userIsAdmin) {
        if (commentRepository.existsById(commentId)){
            Comment comment = commentRepository.getById(commentId);
            if (comment.getUser().getId().equals(userId) || userIsAdmin){
                commentRepository.deleteById(commentId);
            }
        }
        else throw new BadRequestException("Comment with id " + commentId + " doesn't exist");
    }

    public void updateComment(Long commentId, CommentDto commentDto, Long userId, Boolean userIsAdmin) {
        if (commentRepository.existsById(commentId)){
            Comment comment = commentRepository.getById(commentId);
            if (comment.getUser().getId().equals(userId) || userIsAdmin){
                commentRepository.updateById(commentId, commentDto.getMessage());
            }
        }
        else throw new BadRequestException("Comment with id " + commentId + " doesn't exist");
    }

    public List<Comment> getUserComments(Long userId) {
        if(userRepository.existsById(userId)){
            return commentRepository.findAllByUserId(userId);
        }
        else throw new BadRequestException("User with id " + userId + " doesn't exist");
    }
}
