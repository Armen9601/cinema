package com.example.common.service;

import com.example.common.dto.MovieDto;
import com.example.common.dto.ResponseDto;
import com.example.common.entity.Movie;
import com.example.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface MovieService {

    Movie add(
            Movie movie, MultipartFile[] multipartFile,
            String seanceOne, String seanceTwo,
            String seanceThree
    ) throws IOException;

    Page<MovieDto> getAll(Pageable pageable, User user);

    Page<Movie> getByCategory(String category, Pageable pageable);

    Page<Movie> getByLanguage(String lang, Pageable pageable);

    Set<Movie> getByPopularity();

    Movie findBySeanceTime(LocalDateTime localDateTime);

    List<Movie> getByDay();

    List<Movie> getByToDay(String localDate);

    void delete(int id);

    Movie getById(int movieId);

    Page<MovieDto> getByName(String name, Pageable pageable,User user);

    List<Movie> getByAll(ResponseDto responseDto);

    List<Movie> findTop3OByOrderByRatingDesc();

    List<LocalDate> local(LocalDate localDate);

    boolean updateRating(int movieId, User user, int rating);


    List<Movie> getAllMovie();

    Movie updateMovieByPic(
            int movieId, MultipartFile[] multipartFile,
            String seanceOne, String seanceTwo,
            String seanceThree
    ) throws IOException;

    Movie save(Movie movie);

    Movie getByIndex(int index);

    void downloadPicByName(String fileName, HttpServletResponse response) throws IOException;
}
