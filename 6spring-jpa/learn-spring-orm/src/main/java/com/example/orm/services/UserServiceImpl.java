package com.example.orm.services;

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
        logger.info("User saved {}",savedUser.getId());
        return savedUser;
    }

    @Override
    public User updateUser(User user,int userId) {
        // 1.get user from db
        User oldUser = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        oldUser.setAge(user.getAge());
        oldUser.setCity(user.getCity());
        oldUser.setName(user.getName());
        userRepository.save(oldUser);
        logger.info("user updated {}",user);

        // 2.update user
        return null;
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = userRepository.findAll();
        return users;
    }

    // get single user with given id
    @Override
    public User getUser(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    @Override
    public User deleteUser(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
        return user;
    }
}
