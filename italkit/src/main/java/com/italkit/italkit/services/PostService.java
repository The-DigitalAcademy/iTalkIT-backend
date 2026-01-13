package com.italkit.italkit.services;

import com.italkit.italkit.models.Post;
import com.italkit.italkit.models.User;
import com.italkit.italkit.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByTimestampDesc();  // Updated method name
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }

    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByUserIdOrderByTimestampDesc(userId);  // Updated method name
    }

    public List<Post> getFeedPosts(Long userId) {
        User user = userService.getUserById(userId);

        List<Long> followingIds = user.getFollowing().stream()
                .map(User::getId)
                .collect(Collectors.toList());

        followingIds.add(userId);

        return postRepository.findByUserIdInOrderByTimestampDesc(followingIds);  // Updated method name
    }

    public Post createPost(Long userId, Post post) {
        User user = userService.getUserById(userId);
        post.setUser(user);
        post.setTimestamp(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post postDetails) {
        Post post = getPostById(id);

        if (postDetails.getCaption() != null) {  // Changed from getContent
            post.setCaption(postDetails.getCaption());
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
        post.setLikes(post.getLikes() + 1);  // Changed from getLikesCount
        return postRepository.save(post);
    }

    public Post unlikePost(Long id) {
        Post post = getPostById(id);
        if (post.getLikes() > 0) {
            post.setLikes(post.getLikes() - 1);
        }
        return postRepository.save(post);
    }
}