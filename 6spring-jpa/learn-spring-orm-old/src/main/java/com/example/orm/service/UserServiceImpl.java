package com.example.orm.service;

import com.example.orm.entities.User;
import com.example.orm.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        User savedUser = userRepository.save(user);
        logger.info("User Saved: {}",savedUser.getId());
        return savedUser;
    }

    @Override
    public User updateUser(User user, int userId) {
        // 1: get the data using id
        User user1 = userRepository.findById(userId).
                orElseThrow(() -> new RuntimeException("User with given id not found"));
        user1.setName(user.getName());
        user1.setCity(user.getCity());
        user1.setAge(user.getAge());

        // 2: update the new user details
        userRepository.save(user1);
        return user1
                ;
    }

    @Override
    public void deleteUaer(int userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with given id"));
        userRepository.delete(user);

        logger.info("user deleted ");

    }

    @Override
    public List<User> getAllUser() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public User getUser(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User id not found"));
        return user;
    }
}
