package com.italkit.italkit.services;

import com.italkit.italkit.models.FollowerModel;
import com.italkit.italkit.repositories.FollowerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowerService {

    @Autowired
    private FollowerRepo followerRepo;

    public List<FollowerModel> getAllFollowers(){
        return  followerRepo.findAll();
    }
    //How to Unfollow /follow logic
    //Create and Display the custom counts for follower and following
    //*******


}
