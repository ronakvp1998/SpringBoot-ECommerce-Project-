package com.example.orm.service;

import com.example.orm.entities.User;

import java.util.List;

public interface UserService {

    public User saveUser(User user);
    public User updateUser(User user, int userId);
    void deleteUaer(int userId);
    List<User> getAllUser();
    User getUser(int userId);
}
