package com.example.common.service;

import com.example.common.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void update(User user, int like);

    boolean add(User user);

    void deleteById(int id);

    List<User> getAllUsers();

    Optional<User> findByEmail(String email);

}
