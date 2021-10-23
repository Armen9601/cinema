package com.example.common.service;

import com.example.common.entity.User;

public interface UserService {

    void update(User user, int like);

    boolean add(User user);

    void deleteById(int id);

}
