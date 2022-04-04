package com.example.softbinatorlabs.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @Column(columnDefinition = "double default 0")
    private Double currentAmount;
    @Column(columnDefinition = "double default 0")
    private Double targetAmount;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @JsonIgnoreProperties(value = {"categories", "events", "comments", "wallet", "donations"})
    private User user;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    @JsonIgnoreProperties(value = {"events", "user"})
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @JsonIgnoreProperties(value = {"event", "user"})
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @JsonIgnoreProperties(value = {"event", "user"})
    private List<Donation> donations;

}
