package com.italkit.italkit.repositories;

import com.italkit.italkit.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserIdOrderByTimestampDesc(Long id);  // Updated method name

    @Query("SELECT p FROM Post p WHERE p.user.id IN :id ORDER BY p.timestamp DESC")  // Updated field name
    List<Post> findByUserIdInOrderByTimestampDesc(List<Long> id);

    List<Post> findAllByOrderByTimestampDesc();  // Updated method name
}