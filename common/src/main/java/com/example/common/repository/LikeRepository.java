package com.example.common.repository;

import com.example.common.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Integer> {

    List<Like> findByCommentId(int id);


}
