package com.example.common.service.serviceImpl;

import com.example.common.entity.Actor;
import com.example.common.entity.Movie;
import com.example.common.repository.ActorRepository;
import com.example.common.service.ActorService;
import com.example.common.service.MovieService;
import com.example.common.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final MovieService movieService;
    private final FileUploadUtil fileUploadUtil;


    @Override
    public Actor getById(int actorId) {
        return actorRepository.findById(actorId).get();
    }

    @Override
    public void delete(int id) {
        actorRepository.deleteById(id);
    }

    @Override
    public void save(Actor actor, int movieId, MultipartFile multipartFile) {
        List<Movie> movie = actor.getMovie();
        List<Movie> movieList = new ArrayList<>();
        Movie byId = movieService.getById(movieId);
        if (movie == null) {
            movie = movieList;
        }
        movie.add(byId);
        try {
            actor.setPicUrl(fileUploadUtil.getSmallPicUrl(multipartFile, false));
        } catch (IOException e) {
            e.printStackTrace();
        }
        actor.setMovie(movie);
        actorRepository.save(actor);
    }
}
