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

    @Override
    public void userUpdate(User user, int id) {

        Movie byId = movieRepository.getById(id);
        List<Movie> myLikedMovie = user.getMyLikedMovie();

    }

    @Override
    public boolean addUser(User user) {
        if (user.getPassword().length() > 20 || user.getPassword().length() < 4 ||
                !user.getPassword().equals(user.getRepeatPassword())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }
}
