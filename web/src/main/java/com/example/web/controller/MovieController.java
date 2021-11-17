package com.example.web.controller;

import com.example.common.entity.Comment;
import com.example.common.entity.Like;
import com.example.common.entity.Movie;
import com.example.common.properties.MovieProperties;
import com.example.common.repository.LikeRepository;
import com.example.common.service.CommentService;
import com.example.common.service.LikeService;
import com.example.common.service.MovieService;
import com.example.web.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieProperties properties;
    private final MovieService movieService;
    private final CommentService commentService;
    private final LikeService likeService;

    @GetMapping("/admin/addMovie")
    public String addMoviePage() {
        return "add-movie-page";
    }

    @GetMapping("/movieImage")
    void productImage(@RequestParam("movieUrl") String productUrl, HttpServletResponse response) throws IOException {
        InputStream in = new FileInputStream(properties.getMovieImg() + File.separator + productUrl);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @GetMapping("/user/movieDetails")
    public String moviePage(@PageableDefault(size = 3) Pageable pageable,
                            @RequestParam("id") Movie movie,
                            ModelMap modelMap,
                            @AuthenticationPrincipal CurrentUser currentUser) {
        Movie byId = movieService.getById(movie.getId());
        Page<Comment> commentByMovieId = commentService.getCommentByMovieId(movie.getId(), pageable);
        if (commentByMovieId.getTotalPages() > 0) {
            List<Integer> pageNum = IntStream.rangeClosed(1, commentByMovieId.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNum);
        }
        modelMap.addAttribute("movie", byId);
        modelMap.addAttribute("comments", commentByMovieId);
        modelMap.addAttribute("user", currentUser.getUser());

        return "movie-details";
    }

    @GetMapping("/user/viewAll")
    public String allMovies(ModelMap modelMap,
                            @PageableDefault(size = 9) Pageable pageable,
                            @RequestParam(value = "search", required = false) String name) {
        Page<Movie> allMovies = name == null ? movieService.getAll(pageable) :
                movieService.getByName(name, pageable);
        if (allMovies.getTotalPages() > 0) {
            List<Integer> pageNum = IntStream.rangeClosed(1, allMovies.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNum);
        }
        modelMap.addAttribute("date", movieService.local(LocalDate.now()));
        modelMap.addAttribute("movies", allMovies);
        return "movie-grid";
    }

    @GetMapping("/user/nextPremiere")
    public String searchMovieByName(
            @RequestParam(value = "date", required = false) String date,
            ModelMap modelMap
    ) {
        List<Movie> byDate = movieService.getByToDay(date);
        modelMap.addAttribute("date", movieService.local(LocalDate.now()));
        modelMap.addAttribute("movies", byDate);
        return "next-premiere";
    }

    @PostMapping("/updateMovieRating")
    public String updateMovieRating(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestParam int rating,
            @RequestParam("id") int movieId
    ) {
        movieService.updateRating(movieId, currentUser.getUser(), rating);
        return "redirect:/user/movieDetails?id=" + movieId;
    }

    @PostMapping("/admin/addMovie")
    public String addMovie(@ModelAttribute Movie movie,
                           @RequestParam("picture") MultipartFile[] multipartFile,
                           @ModelAttribute("seanceOne") String seanceOne,
                           @ModelAttribute("seanceTwo") String seanceTwo,
                           @ModelAttribute("seanceThree") String seanceThree) throws IOException {
        movieService.add(movie, multipartFile, seanceOne, seanceTwo, seanceThree);
        return "redirect:/admin/addMovie";
    }
}
