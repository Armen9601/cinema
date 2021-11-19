package com.example.web.rest;

import com.example.common.service.UserService;
import com.example.web.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeMovieRestController {

    private final UserService userService;

    @GetMapping("/user/likeMovie")
    public ResponseEntity<Boolean> filterMovies(@RequestParam int movieId, @AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(userService.addOrRemove(currentUser.getUser(), movieId));
    }

}
