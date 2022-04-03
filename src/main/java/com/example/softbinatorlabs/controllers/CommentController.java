package com.example.softbinatorlabs.controllers;

import com.example.softbinatorlabs.dtos.CommentDto;
import com.example.softbinatorlabs.services.CommentService;
import com.example.softbinatorlabs.utility.KeycloakHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<?> getEventComments(@PathVariable Long eventId) {
        return new ResponseEntity<>(commentService.getEventComments(eventId), HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserComments(@PathVariable Long userId) {
        return new ResponseEntity<>(commentService.getUserComments(userId), HttpStatus.OK);
    }

    @PostMapping("/add/{eventId}")
    public ResponseEntity<?> addComment(@PathVariable Long eventId, @RequestBody CommentDto commentDto, Authentication authentication){
        commentService.addComment(commentDto, eventId, Long.parseLong(KeycloakHelper.getUser(authentication)));
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, Authentication authentication){
        commentService.deleteComment(commentId, Long.parseLong(KeycloakHelper.getUser(authentication)),
                KeycloakHelper.userIsAdmin(authentication));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto, Authentication authentication){
        commentService.updateComment(commentId, commentDto, Long.parseLong(KeycloakHelper.getUser(authentication)),
                KeycloakHelper.userIsAdmin(authentication));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
