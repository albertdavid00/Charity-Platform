package com.example.softbinatorlabs.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String message;

    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @JsonIgnoreProperties(value = {"events", "categories", "comments"})
    private User user;

    @ManyToOne
    @JoinColumn(name="event_id", nullable=false)
    @JsonIgnoreProperties(value = {"user", "category", "comments"})
    private Event event;
}
