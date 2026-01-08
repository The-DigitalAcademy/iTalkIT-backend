package com.italkit.italkit.repositories;

import com.italkit.italkit.models.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<PostModel,Integer> {
}
