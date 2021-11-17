package com.example.common.service;

import com.example.common.entity.Actor;
import com.example.common.entity.Movie;
import com.example.common.dto.ResponseDto;
import com.example.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;

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

    Page<Movie> getAll(Pageable pageable);

    Page<Movie> getByCategory(String category, Pageable pageable);

    Page<Movie> getByLanguage(String lang, Pageable pageable);

    Set<Movie> getByPopularity();

    Movie findBySeanceTime(LocalDateTime localDateTime);

    List<Movie> getByDay();

    List<Movie> getByToDay(String localDate);

    void delete(int id);

    Movie getById(int movieId);

    Page<Movie> getByName(String name, Pageable pageable);

    List<Movie> getByAll(ResponseDto responseDto);

    Slice<Movie> findFirst3(Pageable pageable);

    List<LocalDate> local(LocalDate localDate);

    boolean updateRating(int movieId, User user, int rating);

}
