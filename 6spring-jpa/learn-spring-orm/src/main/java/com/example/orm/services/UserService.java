package com.example.orm.services;

import com.example.orm.entities.User;
import java.util.List;

public interface UserService {

    public User saveUser(User user);
    public User updateUser(User user,int userId);
    public List<User> getAllUser();
    public User getUser(int userId);
    public User deleteUser(int userId);
}
