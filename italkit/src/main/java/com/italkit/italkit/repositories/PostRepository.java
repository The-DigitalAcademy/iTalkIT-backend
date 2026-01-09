package com.italkit.italkit.repositories;

import com.italkit.italkit.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("SELECT p FROM Post p WHERE p.user.id IN :userIds ORDER BY p.createdAt DESC")
    List<Post> findByUserIdInOrderByCreatedAtDesc(List<Long> userIds);

    List<Post> findAllByOrderByCreatedAtDesc();
}