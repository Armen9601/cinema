package com.example.common.service.serviceImpl;

import com.example.common.entity.Movie;
import com.example.common.entity.Rating;
import com.example.common.repository.ActorRepository;
import com.example.common.repository.MovieRepository;
import com.example.common.repository.RatingRepository;
import com.example.common.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;
    private final ActorRepository actorRepository;


    @Override
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(Movie movie) {
        Optional<Movie> byId = movieRepository.findById(movie.getId());
        if (byId.isEmpty()) {
            movieRepository.save(movie);
        }
//        int movieRating = 0;
////        List<Rating> byMovie_id = ratingRepository.findByMovie_Id(movie.getId());
//        for (Rating rating : byMovie_id) {
//            movieRating = rating.getRating() + movieRating;
//        }
//        movie.setRating(movieRating / byMovie_id.size());
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

//    @Override
//    public List<Movie> getByCategoryId(int categoryId) {
//        return movieRepository.findByCategory_Id(categoryId);
//    }

    @Override
    public Set<Movie> getByRating() {
        List<Movie> all = movieRepository.findAll();
        TreeSet<Movie> movies = new TreeSet<>(all);
        return null;
    }

    @Override
    public Set<Movie> getByPopularity(int popularity) {
        return null;
    }

//    @Override
//    public List<Movie> showNow(LocalDateTime localDateTime) {
//        return movieRepository.findBySeanceDateTime(localDateTime);
//    }

    @Override
    public List<Movie> showPreviewsWeek(LocalDate startLocalDate, LocalDate endLocalDate) {
        return null;
    }

//    @Override
//    public List<Movie> getByActorId(int actorId) {
//        return movieRepository.findByActor(actorRepository.findById(actorId).get());
//    }

    @Override
    public void deleteMovie(int id) {
        movieRepository.deleteById(id);
    }
}
