package com.example.common.entity;

import com.example.common.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Surname is required")
    private String surname;
    @Email
    private String email;
    private String phoneNumber;
    private String password;
    @Transient
    private String repeatPassword;
    @Enumerated(value = EnumType.STRING)
    private UserType userType;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "liked_movie",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Movie> myLikedMovie;

}
