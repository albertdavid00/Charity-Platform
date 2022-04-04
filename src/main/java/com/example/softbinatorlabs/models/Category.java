package com.example.softbinatorlabs.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @JsonIgnoreProperties(value = {"categories", "events", "wallet", "comments", "donations"})
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    @JsonIgnoreProperties(value = {"category", "user", "comments", "donations"})
    private List<Event> events;
}
