package com.example.common.entity;



import com.example.common.enums.Languages;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private String category;
    private String description;
    private int rating;
    private int popularity;
    private String picUrl;
    @ElementCollection
    private List<String> picUrls;
    @ManyToOne
    private Seance seanceDateTime;
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
