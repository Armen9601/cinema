package com.example.common.service.serviceImpl;

import com.example.common.entity.Actor;
import com.example.common.entity.Movie;
import com.example.common.entity.QMovie;
import com.example.common.entity.Rating;
import com.example.common.enums.Category;
import com.example.common.enums.Languages;
import com.example.common.properties.MovieProperties;
import com.example.common.repository.ActorRepository;
import com.example.common.repository.MovieRepository;
import com.example.common.repository.RatingRepository;
import com.example.common.service.MovieService;
import com.example.common.util.CustomMultipartFile;
import com.example.common.util.FileUploadUtil;
import com.example.common.util.MovieRatingComparator;
import com.example.common.util.ResponseDto;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import static com.example.common.util.DateUtil.dateTimeFormatterWithDate;
import static com.example.common.util.DateUtil.dateTimeFormatterWithDateAndTime;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    @PersistenceContext
    private EntityManager em;
    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;
    private final ActorRepository actorRepository;
    private final MovieProperties movieProperties;
    private final FileUploadUtil fileUploadUtil;

    @Override
    public Movie add(
            Movie movie, MultipartFile[] multipartFiles,
            String seanceOne, String seanceTwo, String seanceThree
    ) throws IOException {
        List<String> picUrls = new ArrayList<>();
        movie.setPicUrl(fileUploadUtil.getSmallPicUrl(multipartFiles[0],true));
        for (MultipartFile multipartFile : multipartFiles) {
            picUrls.add(fileUploadUtil.getSmallPicUrl(multipartFile,true));

        }
        movie.setPicUrls(picUrls);
        List<LocalDateTime> localDateTimeList = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = dateTimeFormatterWithDateAndTime();
        localDateTimeList.add(LocalDateTime.parse(seanceOne, dateTimeFormatter));
        localDateTimeList.add(LocalDateTime.parse(seanceTwo, dateTimeFormatter));
        localDateTimeList.add(LocalDateTime.parse(seanceThree, dateTimeFormatter));
        movie.setSeanceDateTime(localDateTimeList);
        movieRepository.save(movie);
        return movie;
    }

    @Override
    public Movie update(int movieId, int newRating) {
        Movie byId = movieRepository.getById(movieId);
        Rating rat = new Rating();
        rat.setRating(newRating);
        rat.setMovie(byId);
        ratingRepository.save(rat);
        double movieRating = 0;
        List<Rating> byMovie_id = ratingRepository.findByMovie_Id(movieId);
        for (Rating rating : byMovie_id) {
            movieRating = rating.getRating() + movieRating;
        }
        byId.setRating(movieRating / byMovie_id.size());
        return movieRepository.save(byId);
    }

    @Override
    public Page<Movie> getAll(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public Page<Movie> getByCategory(String category, Pageable pageable) {
        Category category1 = Category.valueOf(category.toUpperCase(Locale.ROOT));
        return movieRepository.findByCategory(category1, pageable);
    }

    @Override
    public Page<Movie> getByLanguage(String lang, Pageable pageable) {
        Languages languages = Languages.valueOf(lang.toUpperCase(Locale.ROOT));
        return movieRepository.findByLanguage(languages, pageable);
    }

    @Override
    public Set<Movie> getByPopularity() {
        List<Movie> all = movieRepository.findAll();
        Set<Movie> movies = new TreeSet<>(new MovieRatingComparator());
        movies.addAll(all);
        return movies;
    }

    @Override
    public Movie findBySeanceTime(LocalDateTime localDateTime) {
        return movieRepository.findBySeanceDateTime(localDateTime);
    }

    @Override
    public List<Movie> getByDay() {
        LocalDateTime localDateTime1 = LocalDate.now().atTime(LocalTime.MIDNIGHT);
        LocalDateTime localDateTime2 = LocalDate.now().atTime(LocalTime.MAX);
        return movieRepository.findByDay(localDateTime1, localDateTime2);
    }

    @Override
    public List<Movie> getByToDay(String local) {
        final LocalDate localDate = LocalDate.parse(local, dateTimeFormatterWithDate());
        LocalDateTime localDateTime1 = localDate.atTime(LocalTime.MIDNIGHT);
        LocalDateTime localDateTime2 = localDate.atTime(LocalTime.MAX);
        return movieRepository.findByDay(localDateTime1, localDateTime2);
    }

    @Override
    public void delete(int id) {
        movieRepository.deleteById(id);
    }

    @Override
    public List<Movie> getByActorId(Actor actor) {
        if (actor != null) {
            return movieRepository.findByActor_Id(actor.getId());
        }
        return null;
    }

    @Override
    public Movie getById(Movie movie) {
        return movieRepository.getById(movie.getId());
    }

    @Override
    public Page<Movie> getByName(String name, Pageable pageable) {
        return movieRepository.findByName(name, pageable);
    }

    @Override
    public List<Movie> getByAll(ResponseDto responseDto) {
        List<Languages> languagesList = new ArrayList<>();
        List<Category> categoryList = new ArrayList<>();
        for (String s : responseDto.getLang()) {
            languagesList.add(Languages.valueOf(s.toUpperCase()));
        }
        if (languagesList.size() == 0) {
            languagesList = Arrays.asList(Languages.values());

        }
        for (String s : responseDto.getCategories()) {
            categoryList.add(Category.valueOf(s.toUpperCase()));
        }
        if (categoryList.size() == 0) {

            categoryList = Arrays.asList(Category.values());
        }
        return findMovieByParamsQueryDSL(languagesList, categoryList);
    }

    public List<Movie> findMovieByParamsQueryDSL(
            final List<Languages> languages,
            final List<Category> category
    ) {
        final JPAQuery<Movie> query = new JPAQuery<>(em);
        final QMovie movie = QMovie.movie;
        return query.from(movie).where((movie.language.in(languages))
                .and(movie.category.in(category))).fetch();
    }

    @Override
    public List<LocalDate> local(LocalDate localDate) {
        List<LocalDate> localDateList = new ArrayList<>();
        localDateList.add(localDate.plusDays(1));
        localDateList.add(localDate.plusDays(2));
        localDateList.add(localDate.plusDays(3));
        localDateList.add(localDate.plusDays(4));
        return localDateList;
    }
}