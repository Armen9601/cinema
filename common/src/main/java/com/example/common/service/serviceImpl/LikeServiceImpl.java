package com.example.common.service.serviceImpl;

import com.example.common.entity.Like;
import com.example.common.repository.LikeRepository;
import com.example.common.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Override
    public void addLike(Like like) {
        likeRepository.save(like);
    }

}