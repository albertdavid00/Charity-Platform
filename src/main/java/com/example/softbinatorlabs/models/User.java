package com.example.softbinatorlabs.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnoreProperties(value = {"events", "user"})
    private List<Category> categories;

    @JsonIgnoreProperties(value = {"category", "user", "comments"})
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Event> events;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnoreProperties(value = {"event", "user"})
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnoreProperties(value = {"event", "user"})
    private List<Donation> donations;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id")
    @JsonIgnoreProperties(value = {"user"})
    private Wallet wallet;


}
