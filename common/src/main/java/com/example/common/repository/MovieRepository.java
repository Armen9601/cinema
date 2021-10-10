package com.example.common.repository;

import com.example.common.entity.Actor;
import com.example.common.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Integer> {

//List<Movie> findByCategory_Id(int id);
//List<Movie> findByActor(Actor actor);
//
//List<Movie> findBySeanceDateTime(LocalDateTime localDateTime);


}
