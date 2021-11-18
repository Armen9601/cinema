package com.example.common.service;

import com.example.common.entity.Actor;
import org.springframework.web.multipart.MultipartFile;

public interface ActorService {

    void save(Actor actor, int movieId, MultipartFile multipartFile);

    Actor getById(int actorId);

    void delete(int id);
}
