package com.example.common.service.serviceImpl;

import com.example.common.entity.Actor;
import com.example.common.entity.Movie;
import com.example.common.entity.Rating;
import com.example.common.repository.ActorRepository;
import com.example.common.repository.MovieRepository;
import com.example.common.repository.RatingRepository;
import com.example.common.service.MovieService;
import com.example.common.util.CustomMultipartFile;
import com.example.common.util.MovieRatingComparator;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    @Value("C:\\Java2021\\cinemas\\web\\src\\main\\resources\\static\\assets\\images\\")
    private String uploadDir;

    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;
    private final ActorRepository actorRepository;


    @Override
    public Movie addMovie(Movie movie, MultipartFile[] multipartFiles, String seanceOne, String seanceTwo, String seanceThree) throws IOException {
        List<String> picUrls = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {

            if (!multipartFile.isEmpty()) {
                String picUrl = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();

                String png = "intermediate.png";

                CustomMultipartFile customMultipartFile = new CustomMultipartFile(multipartFile.getBytes(), png);
                String smallPicUrl = compressImage(customMultipartFile, uploadDir, png);
                multipartFile.transferTo(new File(uploadDir + File.separator + picUrl));
                movie.setPicUrl(picUrl);

                picUrls.add(smallPicUrl);

            }
        }
        movie.setPicUrls(picUrls);
        List<LocalDateTime> localDateTimeList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        localDateTimeList.add(LocalDateTime.parse(seanceOne, formatter));
        localDateTimeList.add(LocalDateTime.parse(seanceTwo, formatter));
        localDateTimeList.add(LocalDateTime.parse(seanceThree, formatter));
        movie.setSeanceDateTime(localDateTimeList);
        movieRepository.save(movie);
        return movie;
    }


    @Override
    public Movie updateMovie(int movieId, int newRating) {
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
    public Page<Movie> getAllMovies(Pageable pageable) {
        Page<Movie> all = movieRepository.findAll(pageable);
        return all;
    }

    @Override
    public List<Movie> getByCategory(String category) {
        return movieRepository.findByCategory(category);
    }

    @Override
    public List<Movie> getByLanguage(String lang) {
        return movieRepository.findByLanguage(lang);
    }

    @Override
    public Set<Movie> getByRating() {
        List<Movie> all = movieRepository.findAll();
        Set<Movie> movies = new TreeSet<>(new MovieRatingComparator());
        for (Movie movie : all) {
            movies.add(movie);
        }

        return movies;
    }

    @Override
    public Set<Movie> getByPopularity() {
        List<Movie> all = movieRepository.findAll();
        Set<Movie> movies = new TreeSet<>(new MovieRatingComparator());
        for (Movie movie : all) {
            movies.add(movie);
        }
        return movies;
    }

    @Override
    public Movie findBySeanceTime(LocalDateTime localDateTime) {

        return movieRepository.findBySeanceDateTime(localDateTime);
    }

    @Override
    public List<Movie> showPreviewsWeek(LocalDate startLocalDate, LocalDate endLocalDate) {
        return null;
    }

    @Override
    public List<Movie> getByDay() {
        LocalDateTime localDateTime1 = LocalDate.now().atTime(LocalTime.MIDNIGHT);
        LocalDateTime localDateTime2 = LocalDate.now().atTime(LocalTime.MAX);

        return movieRepository.findByDay(localDateTime1, localDateTime2);
    }

    @Override
    public List<Movie> getByToDay(LocalDate localDate) {

        LocalDateTime localDateTime1 = localDate.atTime(LocalTime.MIDNIGHT);
        LocalDateTime localDateTime2 = localDate.atTime(LocalTime.MAX);
        return movieRepository.findByDay(localDateTime1, localDateTime2);
    }

    @Override
    public void deleteMovie(int id) {
        movieRepository.deleteById(id);
    }


    public static void compressProductImage(BufferedImage image, String uploadPath, String extension) {
        try {
            File compressedImageFile = new File(uploadPath);
            OutputStream outputStream = new FileOutputStream(compressedImageFile);

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(extension);
            ImageWriter writer = writers.next();

            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
            writer.setOutput(imageOutputStream);

            ImageWriteParam param = writer.getDefaultWriteParam();

            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.3f);
            }
            writer.write(null, new IIOImage(image, null, null), param);

            outputStream.close();
            imageOutputStream.close();
            writer.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String compressImage(MultipartFile file, String uploadDir, String fileName) throws IOException {
        String originalFilename = System.currentTimeMillis() + "_" + fileName;
        String uploadPath = uploadDir + originalFilename;
        File image = new File(uploadPath);
        file.transferTo(image);

        BufferedImage bi = ImageIO.read(file.getInputStream());
        BufferedImage resize = Scalr.resize(bi, 200, 150);

        compressProductImage(resize, uploadPath, "png");
        return originalFilename;
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
}