package com.example.todo.todomanager.service.impl;

import com.example.todo.todomanager.doa.TodoDao;
import com.example.todo.todomanager.models.Todo;
import com.example.todo.todomanager.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Primary
public class DaoTodoServiceImpl implements TodoService {

    @Autowired
    private TodoDao todoDao;
    @Override
    public Todo createTodo(Todo todo) {
        return todoDao.saveTodo(todo) ;
    }

    @Override
    public List<Todo> getAllTodos() {
        return todoDao.getAllTodos2();
    }

    @Override
    public Todo getTodo(int todoId) {
        return todoDao.getTodo2(todoId);
    }

    @Override
    public Todo updateTodo(int todoId, Todo newTodo) {
        return todoDao.updateTodo(todoId,newTodo);
    }

    @Override
    public void deleteTodo(int todoId) {
        todoDao.deleteTodo(todoId);
    }
}
