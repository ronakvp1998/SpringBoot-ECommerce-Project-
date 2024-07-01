package com.example.todo.todomanager.doa;

import com.example.todo.todomanager.helper.Helper;
import com.example.todo.todomanager.models.Todo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;

public class TodoRowMapper implements RowMapper<Todo> {

    @Override
    public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {

        Todo todo = new Todo();
        todo.setId((Integer)rs.getInt("id"));
        todo.setTitle((String)rs.getString("title"));
        todo.setStatus((String) rs.getString("status"));
        try {
            todo.setAddedDate(Helper.parseDate((LocalDateTime) rs.getObject("addedDate")));
            todo.setToDoDate(Helper.parseDate((LocalDateTime )rs.getObject("todoDate")));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return todo;
    }
}
