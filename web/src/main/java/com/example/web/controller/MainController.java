package com.example.web.controller;

import com.example.common.entity.Movie;
import com.example.common.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MovieService movieService;

    @GetMapping("/")
    public String home(ModelMap modelMap){
        List<Movie> allMovies = movieService.getByDay();
        modelMap.addAttribute("allMovies",allMovies);
        return "index";
    }


    @GetMapping("/login")
    public String login(){
        return "sign-up";
    }

}
