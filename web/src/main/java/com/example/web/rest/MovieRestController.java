package com.example.web.rest;

import com.example.common.entity.Movie;
import com.example.common.service.MovieService;
import com.example.common.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieRestController {

    private final MovieService movieService;

    @PostMapping("/user/viewAll")
    public List<Movie> filterMovies(@RequestBody ResponseDto responseDto) {
        List<Movie> movies = movieService.getByAll(responseDto);
        return movies;
    }

}
