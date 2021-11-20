package com.example.common.service.serviceImpl;

import com.example.common.entity.*;
import com.example.common.repository.CommentRepository;
import com.example.common.service.CommentService;
import com.example.common.service.DislikeService;
import com.example.common.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.common.entity.QComment.comment;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    public final CommentRepository commentRepository;
    private final LikeService likeService;
    private final DislikeService dislikeService;

    @Override
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(int id) {
        commentRepository.deleteById(id);
    }

    @Override
    public boolean updateLike(int commentId, User user) {
        boolean isRemove = false;
        Comment byId = commentRepository.getById(commentId);
        if (byId.getLikes().stream().anyMatch(m -> m.getUser().getId() == user.getId())) {
            byId.getLikes().removeIf(m -> m.getUser().getId() == user.getId());
        } else {
            Like like = Like.builder()
                    .user(user)
                    .comment(byId)
                    .build();
            likeService.addLike(like);
            byId.getLikes().add(like);
            isRemove = true;
        }
        addComment(byId);
        return isRemove;
    }

    public boolean updateDislike(int commentId, User user) {
        boolean isDislike = false;
        Comment byId = commentRepository.getById(commentId);
        if (byId.getDislikes().stream().anyMatch(m -> m.getUser().getId() == user.getId())) {
            byId.getDislikes().removeIf(m -> m.getUser().getId() == user.getId());
        } else {
            Dislike dislike = Dislike.builder()
                    .user(user)
                    .comment(byId)
                    .build();
            dislikeService.addDislike(dislike);
            byId.getDislikes().add(dislike);
            isDislike = true;
        }
        addComment(byId);
        return isDislike;
    }

    @Override
    public Page<Comment> getCommentByMovieId(int id, Pageable pageable) {
        Page<Comment> byMovieId = commentRepository.findByMovieIdOrderByIdDesc(id, pageable);
        return byMovieId;
    }
}
