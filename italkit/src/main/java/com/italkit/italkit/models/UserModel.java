package com.italkit.italkit.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    //Confirm  if this works store the picture as byte
    //Inform frontend to convert this back to image
    @Column(name = "profile_picture")
    @Lob
    private byte[] profile_image_url;

    @Column(name = "bio")
    private String bio;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "updated_at")
    private Date updated_at;


}
