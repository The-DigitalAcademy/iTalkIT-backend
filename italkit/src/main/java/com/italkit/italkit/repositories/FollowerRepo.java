package com.italkit.italkit.repositories;

import com.italkit.italkit.models.FollowerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepo extends JpaRepository<FollowerModel,Integer> {

}
