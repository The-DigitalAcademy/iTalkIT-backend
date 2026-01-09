package com.italkit.italkit.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity( name = "followers")
public class FollowerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    @Column( name = "followed_id")
    private Integer followed_id;

    @Column( name = "follower_id")
    private Integer follower_id;
}
