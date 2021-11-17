package com.example.web.rest;

import com.example.common.entity.Comment;
import com.example.common.entity.Dislike;
import com.example.common.service.CommentService;
import com.example.web.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentLikeRestController {

    private final CommentService commentService;

    @GetMapping("/updateLikes")
    public ResponseEntity<Boolean> updateLikeCount(
            @RequestParam int commentId,
            @AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(commentService.updateLike(commentId, currentUser.getUser()));
    }

    @GetMapping("/updateDislikes")
    public ResponseEntity<Boolean> updateDislikeCount(
            @RequestParam int commentId,
            @AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(commentService.updateDislike(commentId, currentUser.getUser()));
    }
}