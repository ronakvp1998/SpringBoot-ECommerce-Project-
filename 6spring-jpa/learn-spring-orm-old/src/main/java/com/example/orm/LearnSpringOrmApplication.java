package com.example.orm;

import com.example.orm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import  com.example.orm.entities.User;

import java.util.List;

@SpringBootApplication
public class LearnSpringOrmApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(LearnSpringOrmApplication.class, args);
	}

	Logger logger = LoggerFactory.getLogger(LearnSpringOrmApplication.class);
	@Override
	public void run(String... args){
//		User user = new User();
//		user.setCity("Delhi");
//		user.setName("Ankit");
//		user.setAge(34);
//		User savedUser = userService.saveUser(user);
//		System.out.println(savedUser);

//		List<User> allUser = userService.getAllUser();
//		logger.info("user size is {}",allUser.size());
//		logger.info("User : {} ",allUser)

//		User user = userService.getUser(20);
//		logger.info("user is {} ",user);

		//update method
//		User user = new User();
//		user.setAge(121);
//		user.setName("ronak11");
//		user.setCity("Mumbai11");
//		User updatedUser = userService.updateUser(user,1);
//		logger.info("updated user details: {}",updatedUser);

		//delete
		userService.deleteUaer(2);
	}
}
