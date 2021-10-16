package com.example.common.service.serviceImpl;

import com.example.common.entity.Movie;
import com.example.common.entity.User;
import com.example.common.repository.MovieRepository;
import com.example.common.repository.UserRepository;
import com.example.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MovieRepository movieRepository;
    private final String PASSWORD_PATTERN = "\\w{6,}";


    @Override
    public void userUpdate(User user, int id) {

        Movie byId = movieRepository.getById(id);
        List<Movie> myLikedMovie = user.getMyLikedMovie();


    }

    String msg = null;
    boolean valid = false;
    @Override
    public void addUser(User user) {


        if (user.getPassword().length() > 20) {
            msg = "* Password is too long";
            valid = false;

        } else if (user.getPassword().length() < 4) {
            msg = "* Password is too short";
            valid = false;

        } else if (!user.getPassword().equals(user.getPassword2())) {

            msg = "* Passwords do not match!";
            valid = false;

        } else if (user.getPassword().equals(user.getPassword2())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            valid = true;
        }


    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }


}
