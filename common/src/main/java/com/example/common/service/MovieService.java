package com.example.common.service;

import com.example.common.entity.Actor;
import com.example.common.entity.Movie;
import com.example.common.util.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface MovieService {

    Movie addMovie(Movie movie, MultipartFile[] multipartFile, String seanceOne, String seanceTwo, String seanceThree) throws IOException;

    Movie updateMovie(int movieId, int rating);

    Page<Movie> getAllMovies(Pageable pageable);

    Page<Movie> getByCategory(String category, Pageable pageable);

    Page<Movie> getByLanguage(String lang, Pageable pageable);

    Set<Movie> getByPopularity();

    Movie findBySeanceTime(LocalDateTime localDateTime);


    List<Movie> getByDay();

    List<Movie> getByToDay(String localDate);

    void deleteMovie(int id);

    List<Movie> getByActorId(Actor actor);

    Movie getById(Movie movie);

    Page<Movie> getByName(String name, Pageable pageable);

    List<Movie> getByAll(ResponseDto responseDto);

    List<LocalDate> local(LocalDate localDate);
}
