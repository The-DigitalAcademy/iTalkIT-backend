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

    // Frontend expects: GET /api/users?email={email} for login
    @GetMapping(params = "email")
    public ResponseEntity<List<Map<String, Object>>> getUserByEmail(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        List<Map<String, Object>> response = List.of(mapUserToResponse(user));
        return ResponseEntity.ok(response);
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

    // Update user's following list (for follow/unfollow functionality)
    @PutMapping("/{id}/following")
    public ResponseEntity<Map<String, Object>> updateUserFollowing(
            @PathVariable Long id,
            @RequestBody Map<String, List<Long>> requestBody) {
        List<Long> followingIds = requestBody.get("following");
        User updatedUser = userService.updateUserFollowing(id, followingIds);
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

    // Helper method to avoid circular references and match frontend expectations
    private Map<String, Object> mapUserToResponse(User user) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("password", user.getPassword()); // Include for login verification
        response.put("firstName", user.getUsername()); // Using username as firstName for now
        response.put("lastName", ""); // Empty for now
        response.put("bio", user.getBio() != null ? user.getBio() : "");
        response.put("profilePicture", user.getProfilePicture() != null ? user.getProfilePicture() : "https://plus.unsplash.com/premium_photo-1673344310388-3510b264bfc4?q=80&w=927&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        response.put("createdAt", user.getCreatedAt());
        response.put("followersCount", user.getFollowersCount());
        response.put("followingCount", user.getFollowingCount());
        response.put("postsCount", user.getPostsCount());

        // Add arrays of IDs for frontend
        List<Long> followingIds = user.getFollowing().stream()
                .map(User::getId)
                .collect(Collectors.toList());
        List<Long> followersIds = user.getFollowers().stream()
                .map(User::getId)
                .collect(Collectors.toList());

        response.put("following", followingIds);
        response.put("followers", followersIds);

        return response;
    }
}