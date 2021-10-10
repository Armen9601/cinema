package com.example.common.service;

import com.example.common.entity.Movie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface MovieService {

    Movie addMovie(Movie movie);

    Movie updateMovie(Movie movie);

    List<Movie> getAllMovies();

//    List<Movie> getByCategoryId(int categoryId);

    Set<Movie> getByRating();

    Set<Movie> getByPopularity(int popularity);

//    List<Movie> showNow(LocalDateTime localDateTime);

    List<Movie> showPreviewsWeek(LocalDate startLocalDate, LocalDate endLocalDate);

//    List<Movie> getByActorId(int actorId);

    void deleteMovie(int id);



}
