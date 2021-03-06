package com.example.common.repository;

import com.example.common.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Page<Comment> findByMovieIdOrderByIdDesc(int id, Pageable pageable);

}
