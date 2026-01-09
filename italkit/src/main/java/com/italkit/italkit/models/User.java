package com.italkit.italkit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"posts", "following", "followers"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String bio;

    private String profilePicture;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    // Users that this user is following
    @ManyToMany
    @JoinTable(
            name = "followers",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    @JsonIgnore
    private Set<User> following = new HashSet<>();

    // Users that are following this user
    @ManyToMany(mappedBy = "following")
    @JsonIgnore
    private Set<User> followers = new HashSet<>();

    @Transient
    public int getFollowersCount() {
        return followers.size();
    }

    @Transient
    public int getFollowingCount() {
        return following.size();
    }

    @Transient
    public int getPostsCount() {
        return posts.size();
    }
}