package com.example.web.controller;

import com.example.common.entity.Movie;
import com.example.common.service.MovieService;
import com.example.common.service.UserService;
import com.example.web.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MovieService movieService;
    private final UserService userService;

    @GetMapping("/")
    public String home(
            ModelMap modelMap,
            @AuthenticationPrincipal CurrentUser currentUser) {
        List<Movie> allMovies = movieService.getByDay();
        LocalDate now = LocalDate.now();
        List<Movie> all = movieService.findTop3OByOrderByRatingDesc();
        modelMap.addAttribute("allMovies", allMovies);
        modelMap.addAttribute("all", all);
        modelMap.addAttribute("day", now);
        modelMap.addAttribute("user", currentUser);
        return "index";
    }

    @GetMapping("/sign-in")
    public String login() {
        return "sign-in";
    }

    @GetMapping("/accessDenied")
    public String acsses() {
        return "404";
    }

    @GetMapping("/contact")
    public String contact(@AuthenticationPrincipal CurrentUser currentUser,
                          ModelMap modelMap) {
        modelMap.addAttribute("user", currentUser);
        return "contact";
    }

    @GetMapping("/about")
    public String about(@AuthenticationPrincipal CurrentUser currentUser,
                        ModelMap modelMap) {
        modelMap.addAttribute("user", currentUser);
        return "about";
    }
}
