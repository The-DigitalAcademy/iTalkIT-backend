package com.italkit.italkit.repositories;

import com.italkit.italkit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.followers WHERE u.id = :id")
    Optional<User> findByIdWithFollowers(Long id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.following WHERE u.id = :id")
    Optional<User> findByIdWithFollowing(Long id);

    // Fetch user with both followers and following
    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN FETCH u.followers " +
            "LEFT JOIN FETCH u.following " +
            "WHERE u.id = :id")
    Optional<User> findByIdWithFollowersAndFollowing(Long id);

    // Find users by list of IDs
    @Query("SELECT u FROM User u WHERE u.id IN :ids")
    List<User> findByIdIn(List<Long> ids);
}