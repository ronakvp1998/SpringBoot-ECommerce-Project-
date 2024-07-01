package com.example.todo.todomanager.doa;

import com.example.todo.todomanager.models.Todo;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.ParseException;
import java.util.List;

public interface TodoDao {

    public void setTemplate(JdbcTemplate template);
    public Todo saveTodo(Todo todo);
    public Todo getTodo2(int id);
    public Todo getTodo(int id) throws ParseException;
    public List<Todo> getAllTodos2();
    public List<Todo> getAllTodos();

    public Todo updateTodo(int id, Todo newTodo);
    public void deleteTodo(int id);
    public void deleteMultiple(int ids[]);


}
