package com.example.common.service.serviceImpl;

import com.example.common.dto.MovieDto;
import com.example.common.dto.ResponseDto;
import com.example.common.entity.Movie;
import com.example.common.entity.QMovie;
import com.example.common.entity.Rating;
import com.example.common.entity.User;
import com.example.common.enums.Category;
import com.example.common.enums.Languages;
import com.example.common.properties.MovieProperties;
import com.example.common.repository.ActorRepository;
import com.example.common.repository.MovieRepository;
import com.example.common.repository.RatingRepository;
import com.example.common.service.MovieService;
import com.example.common.service.RatingService;
import com.example.common.util.FileUploadUtil;
import com.example.common.util.MovieRatingComparator;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private final RatingService ratingService;


    @Override
    public Movie add(
            Movie movie, MultipartFile[] multipartFiles,
            String seanceOne, String seanceTwo, String seanceThree
    ) throws IOException {
        List<String> picUrls = new ArrayList<>();
        movie.setPicUrl(fileUploadUtil.getSmallPicUrl(multipartFiles[0], true));
        for (MultipartFile multipartFile : multipartFiles) {
            picUrls.add(fileUploadUtil.getSmallPicUrl(multipartFile, true));

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

    private double update(List<Rating> byMovie_id) {
        double result = 0;
        for (Rating rating : byMovie_id) {
            result = rating.getRating() + result;
        }
       result /= byMovie_id.size();
        return result;
    }

    @Override
    public Page<MovieDto> getAll(Pageable pageable, User user) {
        Page<Movie> allMovies = movieRepository.findAll(pageable);
        return movieDtos(allMovies,user,pageable) ;
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
    public Movie getById(int movieId) {
        Movie byId = movieRepository.getById(movieId);
        List<LocalDateTime> seanceDateTime = byId.getSeanceDateTime();
        seanceDateTime.removeIf(localDateTime -> localDateTime.isBefore(LocalDateTime.now()));
        return byId;
    }

    @Override
    public Page<MovieDto> getByName(String name, Pageable pageable,User user) {
        Page<Movie> byName = movieRepository.findByName(name, pageable);
        return movieDtos(byName,user,pageable);

    }

    @Override
    public List<Movie> getByAll(ResponseDto responseDto) {
        List<Languages> languagesList = new ArrayList<>();
        List<Category> categoryList = new ArrayList<>();
        for (String lang : responseDto.getLang()) {
            languagesList.add(Languages.valueOf(lang.toUpperCase()));
        }
        if (languagesList.size() == 0) {
            languagesList = Arrays.asList(Languages.values());

        }
        for (String category : responseDto.getCategories()) {
            categoryList.add(Category.valueOf(category.toUpperCase()));
        }
        if (categoryList.size() == 0) {

            categoryList = Arrays.asList(Category.values());
        }
        return findMovieByParamsQueryDSL(languagesList, categoryList);
    }

    public List<Movie> findTop3OByOrderByRatingDesc() {
        return movieRepository.findTop3OByOrderByRatingDesc();
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
        localDateList.add(LocalDate.now());
        localDateList.add(localDate.plusDays(1));
        localDateList.add(localDate.plusDays(2));
        localDateList.add(localDate.plusDays(3));
        localDateList.add(localDate.plusDays(4));
        return localDateList;
    }

    @Override
    public List<Movie> getAllMovie() {
        return movieRepository.findAll();
    }

    @Override
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie getByIndex(int index) {
        return movieRepository.getById(index);
    }

    @Override
    public Movie updateMovieByPic(int movieId, MultipartFile[] multipartFiles, String seanceOne, String seanceTwo, String seanceThree) throws IOException {
        List<String> picUrls = new ArrayList<>();
        Movie movie = movieRepository.getById(movieId);
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                movie.setPicUrl(fileUploadUtil.getSmallPicUrl(multipartFiles[0], true));
                picUrls.add(fileUploadUtil.getSmallPicUrl(multipartFile, true));
            }

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
    public void downloadPicByName(String fileName, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        InputStream in = new FileInputStream(movieProperties.getMovieImg() + File.separator + fileName);
        IOUtils.copy(in, response.getOutputStream());
    }

    private Page<MovieDto> movieDtos(Page<Movie> allMovies,User user,Pageable pageable){
        List<MovieDto> movieDtos=new ArrayList<>();
        allMovies.stream().forEach(item->{
            boolean b = user.getMyLikedMovie().stream().anyMatch(like -> item.getId() == like.getId());
            MovieDto movieDto=new MovieDto();
            movieDto.setLiked(b);
            movieDto.setCategory(item.getCategory());
            movieDto.setDuration(item.getDuration());
            movieDto.setId(item.getId());
            movieDto.setName(item.getName());
            movieDto.setPicUrl(item.getPicUrl());
            movieDtos.add(movieDto);
        });
        Page<MovieDto> movieDtoPage=new PageImpl<>(movieDtos,pageable,allMovies.getTotalPages());
        return movieDtoPage;
    }

    @Override
    public boolean updateRating(int movieId, User user, int rat) {
        boolean isRating = false;
        Movie movie = movieRepository.getById(movieId);
        List<Rating> byId = movie.getRatings();

        if (byId.stream().anyMatch(m -> m.getUser().getId() == user.getId())) {
            return isRating;
        } else {
            Rating rating = Rating.builder()
                    .user(user)
                    .movie(movie)
                    .rating(rat)
                    .build();
            ratingRepository.save(rating);
            byId.add(rating);
            isRating = true;
        }
        movie.setRating(update(byId));
        movieRepository.save(movie);

        return isRating;
    }
}