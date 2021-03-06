package com.example.rest.endpoint;

import com.example.common.entity.Movie;
import com.example.common.service.MovieService;
import com.example.rest.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieEndpoint {

    private final MovieService movieService;

    @GetMapping("/admin/addMovie")
    public List<Movie> getAllMovie() {
        return movieService.getAllMovie();
    }

    @GetMapping("/movieDetails/{id}")
    public Movie movieById(@PathVariable("id") int id) {
        return movieService.getByIndex(id);
    }

    @GetMapping("/nextPremiere")
    public List<Movie> searchMovieByDate(@RequestParam String date) {
        return movieService.getByToDay(date);
    }

    @GetMapping("/admin/downloadPic")
    public void downloadPic(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException {
        movieService.downloadPicByName(fileName, response);
    }

    @PostMapping("/admin/uploadPic/{movieId}")
    public Movie updateMovieByPic(@PathVariable("movieId") int movieId,
                                  @RequestParam("picture") MultipartFile[] multipartFile,
                                  @RequestParam String seanceOne,
                                  @RequestParam String seanceTwo,
                                  @RequestParam String seanceThree) throws IOException {
        return movieService.updateMovieByPic(movieId, multipartFile, seanceOne, seanceTwo, seanceThree);
    }

    @PostMapping("/admin/addMovie")
    public Movie save(@RequestBody Movie movie) {
        return movieService.save(movie);
    }

    @PostMapping("/updateMovieRating")
    public boolean updateMovieByRating(@RequestParam int rating,
                                     @RequestParam int movieId,
                                     @AuthenticationPrincipal CurrentUser currentUser) {
        return movieService.updateRating(movieId,currentUser.getUser(), rating);
    }
}
