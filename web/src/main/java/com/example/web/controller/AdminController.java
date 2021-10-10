package com.example.web.controller;

import com.example.common.entity.Movie;
import com.example.common.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/")
public class AdminController {

    private final MovieService movieService;

    @GetMapping("addMovie")
    public String addMoviePage() {
        return "add-movie-page";
    }

    @PostMapping("addMovie")
    public String addMovie(@ModelAttribute Movie movie) {
        movieService.addMovie(movie);
        return "redirect:/add-movie-page";
    }

}
