package com.example.common.service.serviceImpl;

import com.example.common.entity.Rating;
import com.example.common.repository.RatingRepository;
import com.example.common.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository repository;

    @Override
    public List<Rating> getRatingByMovieId(int id) {
        return repository.findByMovie_Id(id);
    }
}
