package com.example.common.service;

import com.example.common.entity.Rating;

import java.util.List;

public interface RatingService {

    List<Rating> getRatingByMovieId(int id);

}
