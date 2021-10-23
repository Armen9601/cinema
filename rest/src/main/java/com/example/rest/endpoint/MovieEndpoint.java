package com.example.rest.endpoint;

import com.example.common.entity.Movie;

import com.example.common.service.MovieService;
import com.example.common.service.RatingService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieEndpoint {

    private final MovieService movieService;
    private final RatingService ratingService;

    /*-------------Add Movie  ------------*/
    @PostMapping("/admin/addMovie")
    public Movie addMovie(@RequestBody Movie movie) throws IOException {
        return movieService.saveMovie(movie);
    }

    /*------------Get All Movie------------*/
    @GetMapping("/addMovie")
    public List<Movie> getMovies() {
        return movieService.moviesAll();
    }

    /*-------------Get Date your MOvie-----------*/



    /*--------------get movie by id ---------*/
    @GetMapping("/movieDetails/{id}")
    public Movie movieById(@PathVariable("id") int id) {
        return movieService.getByIndex(id);
    }

    /*-- update movie rating--*/
    @PostMapping("/updateMovieRating")
    public Movie updateMovieByRating(@RequestParam int rating, @RequestParam int movieId) {
        return movieService.updateMovie(movieId, rating);

    }


}
