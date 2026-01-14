package com.italkit.italkit.controllers;

import com.italkit.italkit.models.Post;
import com.italkit.italkit.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<Map<String, Object>> response = posts.stream()
                .map(this::mapPostToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(mapPostToResponse(post));
    }

    // Frontend expects: GET /api/posts?userId={userId}
    @GetMapping(params = "userId")
    public ResponseEntity<List<Map<String, Object>>> getPostsByUserId(@RequestParam Long userId) {
        List<Post> posts = postService.getPostsByUserId(userId);
        List<Map<String, Object>> response = posts.stream()
                .map(this::mapPostToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/feed/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getFeedPosts(@PathVariable Long userId) {
        List<Post> posts = postService.getFeedPosts(userId);
        List<Map<String, Object>> response = posts.stream()
                .map(this::mapPostToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // Frontend expects: POST /api/posts with userId in the body
    @PostMapping
    public ResponseEntity<Map<String, Object>> createPost(@RequestBody Map<String, Object> requestBody) {
        Long userId = Long.valueOf(requestBody.get("userId").toString());
        String caption = (String) requestBody.get("caption");
        String imageUrl = (String) requestBody.get("imageUrl");

        Post post = new Post();
        post.setCaption(caption);
        post.setImageUrl(imageUrl);

        Post createdPost = postService.createPost(userId, post);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapPostToResponse(createdPost));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updatePost(@PathVariable Long id, @RequestBody Post post) {
        Post updatedPost = postService.updatePost(id, post);
        return ResponseEntity.ok(mapPostToResponse(updatedPost));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Map<String, Object>> likePost(@PathVariable Long id) {
        Post post = postService.likePost(id);
        return ResponseEntity.ok(mapPostToResponse(post));
    }

    @PostMapping("/{id}/unlike")
    public ResponseEntity<Map<String, Object>> unlikePost(@PathVariable Long id) {
        Post post = postService.unlikePost(id);
        return ResponseEntity.ok(mapPostToResponse(post));
    }

    // Updated to match frontend expectations
    private Map<String, Object> mapPostToResponse(Post post) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", post.getId());
        response.put("userId", post.getUser().getId());  // Add userId at top level
        response.put("caption", post.getCaption());  // Changed from 'content'
        response.put("imageUrl", post.getImageUrl());
        response.put("timestamp", post.getTimestamp().toString());  // Changed from 'createdAt'
        response.put("likes", post.getLikes());  // Changed from 'likesCount'
        response.put("comments", List.of());  // Empty array for now

        return response;
    }
}