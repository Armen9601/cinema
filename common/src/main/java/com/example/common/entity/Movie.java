package com.example.common.entity;


import com.example.common.enums.Category;
import com.example.common.enums.Languages;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Category category;
    private String description;
    private double rating;
    private int popularity;
    private String picUrl;
    @ElementCollection
    private List<String> picUrls;
    @ElementCollection
    private List<LocalDateTime> seanceDateTime;
    private String duration;
    @ManyToMany
    @JoinTable(
            name = "movie_actors",
            joinColumns = @JoinColumn(name = "actor_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Actor> actor;
    @Enumerated(value = EnumType.STRING)
    private Languages language;




}
