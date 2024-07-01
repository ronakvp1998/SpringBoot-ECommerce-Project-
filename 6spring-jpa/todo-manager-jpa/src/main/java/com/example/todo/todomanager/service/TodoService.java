package com.example.todo.todomanager.service;

import com.example.todo.todomanager.models.Todo;

import java.util.List;

public interface TodoService {

    public Todo createTodo(Todo todo);
    public List<Todo> getAllTodos();
    public Todo getTodo(int todoId);
    public Todo updateTodo(int todoId, Todo newTodo);
    public void deleteTodo(int todoId);
}
