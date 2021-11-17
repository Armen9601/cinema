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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MovieRepository movieRepository;

    @Override
    public boolean addOrRemove(User user, int id) {
        boolean isRemove = false;
        Movie byId = movieRepository.getById(id);
        if (user.getMyLikedMovie().stream().anyMatch(m -> m.getId() == byId.getId())) {
            user.getMyLikedMovie().removeIf(m -> m.getId() == id);
        } else {
            user.getMyLikedMovie().add(byId);
            isRemove = true;
        }
        userRepository.save(user);
        return isRemove;
    }

    @Override
    public boolean add(User user) {
        if (user.getPassword().length() > 20 || user.getPassword().length() < 4 ||
                !user.getPassword().equals(user.getRepeatPassword())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
