package com.example.web.controller;

import com.example.common.entity.Movie;
import com.example.common.entity.Rating;
import com.example.common.service.MovieService;
import com.example.common.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class MovieController {

    @Value("C:\\Java2021\\cinemas\\web\\src\\main\\resources\\static\\assets\\images\\")
    private String movieImg;
    private final MovieService movieService;
    private final RatingService ratingService;

    @GetMapping("/admin/addMovie")
    public String addMoviePage() {

        return "add-movie-page";
    }

    @PostMapping("/admin/addMovie")
    public String addMovie(@ModelAttribute Movie movie, @RequestParam("picture") MultipartFile[] multipartFile,
                           @ModelAttribute("seanceOne") String seanceOne,
                           @ModelAttribute("seanceTwo") String seanceTwo,
                           @ModelAttribute("seanceThree") String seanceThree) throws IOException {

        movieService.addMovie(movie, multipartFile, seanceOne, seanceTwo, seanceThree);
        return "redirect:/admin/addMovie";
    }

    @GetMapping("/movieImage")
    void productImage(@RequestParam("movieUrl") String productUrl, HttpServletResponse response) throws IOException {
        InputStream in = new FileInputStream(movieImg + File.separator + productUrl);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @GetMapping("/movieDetails")
    public String moviePage(@RequestParam("id") Movie movie, ModelMap modelMap) {
        Movie byId = movieService.getById(movie);
        List<Rating> ratingByMovieId = ratingService.getRatingByMovieId(movie.getId());
        modelMap.addAttribute("ratings", ratingByMovieId);
        modelMap.addAttribute("movie", byId);
        return "movie-details";
    }

    @GetMapping("/viewAll")

    public String allMovies(ModelMap modelMap, @PageableDefault(size = 9) Pageable pageable, @RequestParam(value = "search", required = false) String name,
                            @RequestParam(value = "lang",required = false)String lang) {
        Page<Movie> allMovies;
        if (name != null) {
            allMovies = movieService.getByName(name,pageable);
        }
        else if (lang!=null){

            allMovies=  movieService.getByLanguage(lang,pageable);
        }
        else {

            allMovies = movieService.getAllMovies(pageable);
        }
        int totalPages = allMovies.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNum = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNum);
        }
        modelMap.addAttribute("movies", allMovies);
        return "movie-grid";
    }

//    @GetMapping("/movieList")
//    public String allMoviesLists(ModelMap modelMap) {
//        List<Movie> allMovies = movieService.getAllMovies(P);
//        Set<Movie> byPopularity = movieService.getByPopularity();
//        Set<Movie> byRating = movieService.getByRating();
//        List<Movie> byDay = movieService.getByDay();
//        modelMap.addAttribute("movies", allMovies);
//        modelMap.addAttribute("byPopularity", byPopularity);
//        modelMap.addAttribute("byRating", byRating);
//        modelMap.addAttribute("byDay", byDay);
//        return "movie-list";
//    }

    @PostMapping("/updateMovieRating")
    public String updateMovieRating(@RequestParam int rating, @RequestParam("id") int movieId) {

        movieService.updateMovie(movieId, rating);
        return "redirect:/movieDetails?id=" + movieId;
    }

//    @GetMapping("/search")
//    public String searchMovieByName(@RequestParam String name, ModelMap modelMap) {
//        List<Movie> byName = movieService.getByName(name);
//        modelMap.addAttribute("movies", byName);
//        return "movie-grid";
//    }

}
