package com.example.todo.todomanager.service.impl;

import com.example.todo.todomanager.doa.TodoRepository;
import com.example.todo.todomanager.exception.ResourceNotFoundException;
import com.example.todo.todomanager.models.Todo;
import com.example.todo.todomanager.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class TodoJpaServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;
    Logger logger = LoggerFactory.getLogger(TodoJpaServiceImpl.class);
    @Override
    public Todo createTodo(Todo todo) {
        logger.info("jpa create");
        return todoRepository.save(todo);
    }

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public Todo getTodo(int todoId) {
        return todoRepository.findById(todoId).orElseThrow(()-> new ResourceNotFoundException("todo not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public Todo updateTodo(int todoId, Todo newTodo) {
        Todo todo1 = getTodo(todoId);
        todo1.setTitle(newTodo.getTitle());
        todo1.setAddedDate(newTodo.getAddedDate());
        todo1.setContent(newTodo.getContent());
        todo1.setToDoDate(newTodo.getToDoDate());
        todo1.setStatus(newTodo.getStatus());
        return todoRepository.save(todo1);
    }

    @Override
    public void deleteTodo(int todoId) {
        Todo todo1 = getTodo(todoId);
        todoRepository.delete(todo1);

    }
}
