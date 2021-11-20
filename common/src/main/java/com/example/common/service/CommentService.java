package com.example.common.service;

import com.example.common.entity.Comment;
import com.example.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommentService {
    void addComment(Comment comment);

    void deleteCommentById(int id);

    Page<Comment> getCommentByMovieId(int id, Pageable pageable);

    boolean updateLike(int commentId, User user);

    boolean updateDislike(int commentId, User user);

}
