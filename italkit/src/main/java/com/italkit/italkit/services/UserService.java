package com.italkit.italkit.services;

import com.italkit.italkit.models.User;
import com.italkit.italkit.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {

            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);

        if (userDetails.getUsername() != null && !userDetails.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(userDetails.getUsername())) {
                throw new RuntimeException("Username already exists");
            }
            user.setUsername(userDetails.getUsername());
        }

        if (userDetails.getEmail() != null) {
            user.setEmail(userDetails.getEmail());
        }
        if (userDetails.getBio() != null) {
            user.setBio(userDetails.getBio());
        }
        if (userDetails.getProfilePicture() != null) {
            user.setProfilePicture(userDetails.getProfilePicture());
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    @Transactional
    public User followUser(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw new RuntimeException("Cannot follow yourself");
        }

        User follower = getUserById(followerId);
        User following = getUserById(followingId);

        follower.getFollowing().add(following);

        return userRepository.save(follower);
    }

    @Transactional
    public User unfollowUser(Long followerId, Long followingId) {
        User follower = getUserById(followerId);
        User following = getUserById(followingId);

        follower.getFollowing().remove(following);

        return userRepository.save(follower);
    }

    public Set<User> getFollowers(Long userId) {
        User user = userRepository.findByIdWithFollowers(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return user.getFollowers();
    }

    public Set<User> getFollowing(Long userId) {
        User user = userRepository.findByIdWithFollowing(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return user.getFollowing();
    }
}