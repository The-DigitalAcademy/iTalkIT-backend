package com.italkit.italkit.services;

import com.italkit.italkit.models.PostModel;
import com.italkit.italkit.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    public List<PostModel> getAllPost(){
        return  postRepo.findAll();
    }

    public PostModel createPost(PostModel post){
        return  postRepo.save(post);
    }

    public Optional<PostModel> getPostById(Integer id){
        return postRepo.findById(id);
    }

    public void deletePost(Integer id){
        postRepo.deleteById(id);
    }

    //Edit the post function

}
