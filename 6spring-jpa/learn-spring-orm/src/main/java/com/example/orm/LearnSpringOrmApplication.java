package com.example.orm;

import com.example.orm.entities.*;
import com.example.orm.repositories.CategoryRepo;
import com.example.orm.repositories.ProductRepo;
import com.example.orm.repositories.StudentRepository;
import com.example.orm.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class LearnSpringOrmApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private UserService userService;
	private Logger logger = LoggerFactory.getLogger(LearnSpringOrmApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(LearnSpringOrmApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		// Save user
//		User user = new User();
//		user.setName("Ankit");
//		user.setCity("Delhi");
//		user.setAge(34);
//		User savedUser = userService.saveUser(user);
//		System.out.println(savedUser);


		// get user by id
//		User user = userService.getUser(22);
//		logger.info("User : {} ",user);

		// get all users
//		List<User> userList = userService.getAllUser();
//		logger.info("User size is {} ",userList.size());
//		logger.info("Users: {} ",userList);

//		// updte user based on id
//		User user = new User();
//		user.setName("Ronak");
//		user.setCity("Mumbai");
//		user.setAge(40);
//		User updatedUser = userService.updateUser(user , 1);
//		logger.info("updated user details {}",updatedUser);

		//deleted user bases on Id
//		User user = null;
//		try{
//			user = userService.deleteUser(2);
//		}catch(Exception e){
//			e.getMessage();
//		}
//		logger.info("user deleted {}",user);

		//One to One relationship
//		Student student = new Student();
//		student.setStudentName("Ronak Panchal");
//		student.setAbout("I am java dev");
//		student.setStudentId(12);
//
//		Laptop laptop = new Laptop();
//		laptop.setLaptopId(123);
//		laptop.setModelNumber("1234");
//		laptop.setBrand("Dell");
//
//		laptop.setStudent(student);
//		student.setLaptop(laptop);
//
//		Student student1 = studentRepository.save(student);
//		logger.info("saved student : {}",student1.getStudentName());

//		Student student = studentRepository.findById(12).get();
//		logger.info("Name is {} ",student.getStudentName());
//
//		Laptop laptop = student.getLaptop();
//		logger.info("Laptop is {}",laptop.getModelNumber());
//
//		Student student2 = laptop.getStudent();
//		logger.info("Name is {} ",student2.getStudentName());


		// One To Many
//		Student student = new Student();
//		student.setStudentName("Ronak Panchal");
//		student.setAbout("I am java dev");
//		student.setStudentId(12);
//
//
//		Address a1 = new Address();
//		a1.setAddressId(131);
//		a1.setStreet("235/235");
//		a1.setCity("LW0");
//		a1.setCountry("IND");
//		a1.setStudent(student);
//
//		Address a2 = new Address();
//		a2.setAddressId(132);
//		a2.setStreet("235/235");
//		a2.setCity("LW0");
//		a2.setCountry("IND");
//		a2.setStudent(student);
//
//		student.setAddressList(List.of(a1,a2));
//
//		Student save = studentRepository.save(student);
//		logger.info("Student created: with address ");


		// Many To Many
//		Product product1 = new Product();
//		product1.setpId("pid1");
//		product1.setProductName("Iphone");
//
//		Product product2 = new Product();
//		product2.setpId("pid2");
//		product2.setProductName("Samsung");
//
//		Product product3 = new Product();
//		product3.setpId("pid3");
//		product3.setProductName("Iphone 14 max pro");
//
//		Catogory catogory1 =  new Catogory();
//		catogory1.setCid("cid1");
//		catogory1.setTitle("Electronics");
//
//		Catogory catogory2 =  new Catogory();
//		catogory2.setCid("cid2");
//		catogory2.setTitle("Mobile Phones");
//
//		catogory1.setProductList(List.of(product1,product2,product3));
//		catogory2.setProductList(List.of(product1,product2));
//
//		categoryRepo.save(catogory1);
//		categoryRepo.save(catogory2);

//		Catogory catogory1 =  categoryRepo.findById("cid1").get();
//		logger.info("category size {} ",catogory1.getProductList().size());
//
//		Catogory catogory2 =  categoryRepo.findById("cid2").get();
//		logger.info("category size {} ",catogory2.getProductList().size());

//		Product product = productRepo.findById("pid1").get();
//		logger.info("product size {} ",product.getCatogoryList().size());


		// Testing the finder methods
	    Optional<Product> byProductName = productRepo.findByProductName("Samsung");
		logger.info("{}",byProductName.isPresent());
		logger.info("{}",byProductName.get().getProductName());
		logger.info("*******************************************************");
		List<Product> productList = productRepo.findByProductNameEndingWith("g");
		productList.forEach(p -> System.out.println(p.getProductName()));

		logger.info("*******************************************************");
		List<Product> sung = productRepo.findByProductNameContaining("Sam");
		sung.forEach(p -> System.out.println(p.getProductName()));

		logger.info("*******************************************************");
		List<Product> pp = productRepo.findByProductNameIn(List.of("Samsung","Iphone"));
		pp.forEach(p -> System.out.println(p.getProductName()));


	}
}
