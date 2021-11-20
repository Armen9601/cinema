package com.example.web.controller;

import com.example.common.entity.Comment;
import com.example.common.service.CommentService;
import com.example.common.service.MovieService;
import com.example.web.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final MovieService movieService;

    @PostMapping("/user/addComment")
    public String addComment(
            @ModelAttribute Comment comment,
            @RequestParam(name = "movieId") int movieId,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        comment.setUser(currentUser.getUser());
        comment.setMovie(movieService.getById(movieId));
        commentService.addComment(comment);
        return "redirect:/movieDetails?id=" + movieId;
    }

    @GetMapping("/user/deleteComment")
    public String deleteComment(@RequestParam(name = "movieId") int movieId,
                                @RequestParam(name = "id") int id) {
        commentService.deleteCommentById(id);
        return "redirect:/movieDetails?id=" + movieId;
    }
}
