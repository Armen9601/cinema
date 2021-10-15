package com.example.common.repository;

import com.example.common.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findByCategory(String category);

    List<Movie> findByActor_Id(int id);

    Movie findBySeanceDateTime(LocalDateTime localDateTime);

    @Query(value = "select distinct m.* from movies m left join  movie_seance_date_time ms on m.id=ms.movie_id where  ms.seance_date_time>=:start and ms.seance_date_time<=:end", nativeQuery = true)
    List<Movie> findByDay(@Param("start") LocalDateTime localDateTime, @Param("end") LocalDateTime localDateTim);

    List<Movie> findByLanguage(String lang);

    @Query(value = "select * from movies m where name like %:name%", nativeQuery = true)
    Page<Movie> findByName(@Param("name") String name, Pageable pageable);

}
