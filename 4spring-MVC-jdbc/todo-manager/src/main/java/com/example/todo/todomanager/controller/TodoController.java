package com.example.todo.todomanager.controller;

import com.example.todo.todomanager.models.Todo;
import com.example.todo.todomanager.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;
    Logger logger = LoggerFactory.getLogger(TodoController.class);
    Random random = new Random();
    @PostMapping
    public ResponseEntity<Todo> createTodoHandler(@RequestBody Todo todo){
        logger.info("Create Todo");
        int id = random.nextInt(8888888);
        Date currentDate = new Date();
        logger.info("currentdate {}",currentDate);
        todo.setAddedDate(currentDate);
        todo.setId(id);
        Todo todo1 = todoService.createTodo(todo);
        return new ResponseEntity<>(todo1, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodo(){
        logger.info("Get Todo");
        return new ResponseEntity<>(todoService.getAllTodos(), HttpStatus.CREATED);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getSingleTodoHandler(@PathVariable int todoId){
        return new ResponseEntity<Todo>(todoService.getTodo(todoId),HttpStatus.OK);
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<Todo> updateTodoHandler(@RequestBody Todo newTodo,
                                                  @PathVariable int todoId){

        Todo todo = todoService.updateTodo(todoId, newTodo );
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<String> deleteTodo(@PathVariable int todoId){
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok("Todo successfully deleted");
    }

    @GetMapping("/exception")
    public ResponseEntity<String> checkExceptionTodo(){
        String s = null;
        s.length();

//        String s = "ronak";
//        Integer.parseInt(s);


        return ResponseEntity.ok("Todo exceptiion");
    }

//    @ExceptionHandler(value = {NullPointerException.class, NumberFormatException.class})
//    public ResponseEntity<String> nullPointerExceptionHanlder(Exception ex){
//        System.out.println(ex.getMessage());
//        return new ResponseEntity<>( ex.getClass().getName() +"  " + ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
