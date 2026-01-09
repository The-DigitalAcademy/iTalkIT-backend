package com.italkit.italkit.services;

import com.italkit.italkit.models.Post;
import com.italkit.italkit.models.User;
import com.italkit.italkit.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }

    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Post> getFeedPosts(Long userId) {
        User user = userService.getUserById(userId);

        // Get posts from users that this user is following, plus their own posts
        List<Long> followingIds = user.getFollowing().stream()
                .map(User::getId)
                .collect(Collectors.toList());

        followingIds.add(userId); // Include user's own posts

        return postRepository.findByUserIdInOrderByCreatedAtDesc(followingIds);
    }

    public Post createPost(Long userId, Post post) {
        User user = userService.getUserById(userId);
        post.setUser(user);
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post postDetails) {
        Post post = getPostById(id);

        if (postDetails.getContent() != null) {
            post.setContent(postDetails.getContent());
        }
        if (postDetails.getImageUrl() != null) {
            post.setImageUrl(postDetails.getImageUrl());
        }

        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        Post post = getPostById(id);
        postRepository.delete(post);
    }

    public Post likePost(Long id) {
        Post post = getPostById(id);
        post.setLikesCount(post.getLikesCount() + 1);
        return postRepository.save(post);
    }

    public Post unlikePost(Long id) {
        Post post = getPostById(id);
        if (post.getLikesCount() > 0) {
            post.setLikesCount(post.getLikesCount() - 1);
        }
        return postRepository.save(post);
    }
}