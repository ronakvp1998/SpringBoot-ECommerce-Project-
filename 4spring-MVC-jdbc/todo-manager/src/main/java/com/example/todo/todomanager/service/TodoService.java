package com.example.todo.todomanager.service;

import com.example.todo.todomanager.exception.ResourceNotFoundException;
import com.example.todo.todomanager.models.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TodoService {

    Logger logger = LoggerFactory.getLogger(TodoService.class);
    List<Todo> todos = new ArrayList<>();
    public Todo createTodo(Todo todo){
        todos.add(todo);
        logger.info("Todos {} ",this.todos);
        return todo;
    }

    public List<Todo> getAllTodos(){
        return todos;
    }

    public Todo getTodo(int todoId) {

//        return todos.get(todoId);
//        Todo todo = todos.stream().filter(t -> todoId == t.getId()).findAny().get();

        Todo todo = todos.stream().filter(t -> todoId == t.getId()).findAny().orElseThrow(() -> new ResourceNotFoundException("todo not found", HttpStatus.NOT_FOUND));

        logger.info("TODO : {} ",todo);
        return todo;
    }

    public Todo updateTodo(int todoId, Todo newTodo) {
        List<Todo> newUpdateList =
        todos.stream().map(t -> {
            if(t.getId() == todoId){
                t.setTitle(newTodo.getTitle());
                t.setStatus(newTodo.getStatus());
                t.setContent(newTodo.getContent());
                return t;
            }else{
                return t;
            }
        }).collect(Collectors.toList());
        todos = newUpdateList;
        newTodo.setId(todoId);
        return  newTodo;
    }

    public void deleteTodo(int todoId) {
        logger.info("dssad");
        List<Todo> newList =  todos.stream().filter(t-> t.getId() != todoId).collect(Collectors.toList());
        todos = newList;

    }
}
