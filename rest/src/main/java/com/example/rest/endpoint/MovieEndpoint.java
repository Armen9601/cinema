package com.example.rest.endpoint;

import com.example.common.entity.Movie;

import com.example.common.service.MovieService;
import com.example.common.service.RatingService;
import com.example.rest.dto.UserSaveDto;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieEndpoint {

    private final MovieService movieService;
    private final RatingService ratingService;


    /*----------------- Get Movies---------*/
    @GetMapping("/admin/addMovie")
    public List<Movie> getAllMovie() {
        return movieService.getAllMovie();
    }

/*---upload pic by movie----*/
    @PostMapping ("/admin/uploadPic/{movieId}")
    public Movie updateMovieByPic(@PathVariable ("movieId") int movieId,
                                        @RequestParam("picture") MultipartFile[] multipartFile,
                                        @RequestParam String seanceOne,
                                        @RequestParam String seanceTwo,
                                        @RequestParam String seanceThree) throws IOException {
        return movieService.updateMovieByPic(movieId,multipartFile,seanceOne,seanceTwo,seanceThree);
    }

    @PostMapping ("/admin/addMovie")
    public Movie save(@RequestBody Movie movie){
        return movieService.save(movie);
    }

    /*--------------get movie by id ---------*/
    @GetMapping("/movieDetails/{id}")
    public Movie movieById(@PathVariable("id") int id){
        return movieService.getByIndex(id);
    }

    /*-- update movie rating--*/
    @PostMapping("/updateMovieRating")
    public Movie updateMovieByRating(@RequestParam int rating,@RequestParam int movieId) {
        return movieService.update(movieId,rating);

    }

    /*----------search movie by date----------*/
    @GetMapping("/nextPremiere")
    public List<Movie> searchMovieByDate(@RequestParam String date){
        return movieService.getByToDay(date);
    }

    @GetMapping("/admin/downloadPic")
    public void downloadPic(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException {
         movieService.downloadPicByName(fileName, response);
    }

}
