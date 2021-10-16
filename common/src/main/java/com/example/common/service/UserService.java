package com.example.common.service;

import com.example.common.entity.User;

public interface UserService {
    void userUpdate(User user, int like);

    void addUser(User user);

    void deleteUserById(int id);




}
