package com.italkit.italkit.controllers;

import com.italkit.italkit.models.User;
import com.italkit.italkit.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<Map<String, Object>> response = users.stream()
                .map(this::mapUserToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(mapUserToResponse(user));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Map<String, Object>> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(mapUserToResponse(user));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapUserToResponse(createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(mapUserToResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{followerId}/follow/{followingId}")
    public ResponseEntity<Map<String, Object>> followUser(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {
        User user = userService.followUser(followerId, followingId);
        return ResponseEntity.ok(mapUserToResponse(user));
    }

    @DeleteMapping("/{followerId}/unfollow/{followingId}")
    public ResponseEntity<Map<String, Object>> unfollowUser(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {
        User user = userService.unfollowUser(followerId, followingId);
        return ResponseEntity.ok(mapUserToResponse(user));
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<List<Map<String, Object>>> getFollowers(@PathVariable Long id) {
        Set<User> followers = userService.getFollowers(id);
        List<Map<String, Object>> response = followers.stream()
                .map(this::mapUserToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<List<Map<String, Object>>> getFollowing(@PathVariable Long id) {
        Set<User> following = userService.getFollowing(id);
        List<Map<String, Object>> response = following.stream()
                .map(this::mapUserToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // Helper method to avoid circular references
    private Map<String, Object> mapUserToResponse(User user) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("bio", user.getBio());
        response.put("profilePicture", user.getProfilePicture());
        response.put("createdAt", user.getCreatedAt());
        response.put("followersCount", user.getFollowersCount());
        response.put("followingCount", user.getFollowingCount());
        response.put("postsCount", user.getPostsCount());
        return response;
    }
}