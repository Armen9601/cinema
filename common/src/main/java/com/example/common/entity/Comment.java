package com.example.common.entity;

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
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String text;
    @ManyToOne
    private User user;
    private int likeCount;
    private int disLikeCount;
    @ManyToOne
    private Movie movie;
    @OneToMany
    private List<Like> likes;
    @OneToMany
    private List<Dislike> dislikes;

}
