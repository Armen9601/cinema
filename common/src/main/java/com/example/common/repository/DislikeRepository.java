package com.example.common.repository;

import com.example.common.entity.Dislike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DislikeRepository extends JpaRepository<Dislike, Integer>{

    List<Dislike> findByCommentId(int id);
}
