package com.example.common.service;

import com.example.common.entity.User;

public interface UserService {

    void userUpdate(User user, int like);

    boolean addUser(User user);

    void deleteUserById(int id);

}
