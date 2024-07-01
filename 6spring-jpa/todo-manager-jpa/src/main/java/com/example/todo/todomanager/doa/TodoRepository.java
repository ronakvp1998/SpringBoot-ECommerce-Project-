package com.example.todo.todomanager.doa;

import com.example.todo.todomanager.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Integer> {
}
