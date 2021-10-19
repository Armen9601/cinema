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
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
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
                            @RequestParam(value = "lang", required = false) String lang, @RequestParam(value = "category", required = false) String category) {
        Page<Movie> allMovies = movieService.getByAll(pageable, name, lang, category);
        int totalPages = allMovies.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNum = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNum);
        }
        modelMap.addAttribute("date", movieService.local(LocalDate.now()));
        modelMap.addAttribute("movies", allMovies);
        return "movie-grid";
    }


    @PostMapping("/updateMovieRating")
    public String updateMovieRating(@RequestParam int rating, @RequestParam("id") int movieId) {

        movieService.updateMovie(movieId, rating);
        return "redirect:/movieDetails?id=" + movieId;
    }

    @GetMapping("/nextPremiere")
    public String searchMovieByName(@RequestParam(value = "date", required = false) String date, ModelMap modelMap) {
        List<Movie> byDate = movieService.getByToDay(date);
        modelMap.addAttribute("date", movieService.local(LocalDate.now()));
        modelMap.addAttribute("movies", byDate);
        return "next-premiere";
    }

}
