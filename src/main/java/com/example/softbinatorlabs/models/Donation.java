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
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String message;
    @NotNull
    private Double amount;

    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @JsonIgnoreProperties(value = {"events", "categories", "comments", "wallet", "donations"})
    private User user;

    @ManyToOne
    @JoinColumn(name="event_id", nullable=false)
    @JsonIgnoreProperties(value = {"user", "category", "comments", "donations"})
    private Event event;
}