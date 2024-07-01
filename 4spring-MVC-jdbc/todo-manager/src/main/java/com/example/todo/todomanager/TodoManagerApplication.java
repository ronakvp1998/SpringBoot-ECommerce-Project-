package com.example.todo.todomanager;

import com.example.todo.todomanager.doa.TodoDao;
import com.example.todo.todomanager.models.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class TodoManagerApplication  implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(TodoManagerApplication.class);
	@Autowired
	private TodoDao todoDao;
	public static void main(String[] args) {
		SpringApplication.run(TodoManagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		System.out.println("Application started");
//		JdbcTemplate jdbcTemplate = todoDao.getTemplate();
//		logger.info("Template object : {}",jdbcTemplate);
//		logger.info("Template object : {}",jdbcTemplate.getDataSource().getConnection());
//
//		Todo todo = new Todo();
//		todo.setId(123);;
//		todo.setTitle("This is for testing");
//		todo.setContent("Wow its working");
//		todo.setStatus("Pending");
//		todo.setToDoDate(new Date());
//		todo.setAddedDate(new Date());
//
//		logger.info("run todosave method completed");
//		todoDao.SaveTodo(todo);

//		todoDao.getTodo(123);
//		List<Todo> allTodos = todoDao.getAllTodos();
//		logger.info("All Todos {}",allTodos);


		//update
//		Todo todo = todoDao.getTodo(123);
//		logger.info("Todo ,{} ",todo);
//		todo.setTitle("asdv");
//		todo.setContent("I have");
//		todo.setStatus("Done");
//		todo.setAddedDate(new Date());
//		todo.setToDoDate(new Date());
//		todoDao.updateTodo(123,todo);

		//delete
//		todoDao.deleteTodo(123);

		//delete multiple
//		todoDao.deleteMultiple(new int[]{123,1234});


//		get using row mapper
//		Todo todo = todoDao.getTodo2(123);
//		logger.info("TODO: {}",todo);

		// get all todos using row mapper
		System.out.println(todoDao.getAllTodos2());
	}

}
