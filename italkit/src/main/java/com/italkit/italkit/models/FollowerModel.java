package com.italkit.italkit.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity( name = "followers")
public class FollowerModel {

    @Column( name = "followed_id")
    private Integer followed_id;

    @Column( name = "follower_id")
    private Integer follower_id;
}
