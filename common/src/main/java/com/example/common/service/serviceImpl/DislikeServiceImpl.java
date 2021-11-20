package com.example.common.service.serviceImpl;

import com.example.common.entity.Dislike;
import com.example.common.repository.DislikeRepository;
import com.example.common.service.DislikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DislikeServiceImpl implements DislikeService {

    private final DislikeRepository dislikeRepository;

    @Override
    public void addDislike(Dislike dislike) {
        dislikeRepository.save(dislike);
    }

}