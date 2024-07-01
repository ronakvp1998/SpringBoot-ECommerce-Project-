package com.example.todo.todomanager.doa;

import com.example.todo.todomanager.helper.Helper;
import com.example.todo.todomanager.models.Todo;
import com.sun.source.tree.BreakTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TodoDao {

    Logger logger = LoggerFactory.getLogger(TodoDao.class);
//    @Autowired
    private JdbcTemplate template;

    public TodoDao(@Autowired JdbcTemplate template) {
        this.template = template;

        String createTable = "create table if not exists todos(" +
                "id int primary key, " +
                "title varchar(100) not null, " +
                "content varchar(500), " +
                "status varchar(10) not null, " +
                "addedDate datetime, "+
                "todoDate datetime);";

        int update = template.update(createTable);
        logger.info("Todo table created {} ", update);
    }

    public JdbcTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    // save

    public Todo SaveTodo(Todo todo){
        String insert = "insert into todos(id,title,content,status,addedDate,todoDate) values(?,?,?,?,?,?)";
        int rows = template.update(insert,todo.getId(),todo.getTitle(),todo.getContent(),
                todo.getStatus(),todo.getAddedDate(),todo.getToDoDate());

        logger.info("JDBC Operation {} inserted",rows );
        return todo;
    }

    public Todo getTodo2(int id) throws ParseException {
        String query = "select * from todos where id = ?";
        Todo todo = template.queryForObject(query,new TodoRowMapper(),id);
        return todo;
    }

    public Todo getTodo(int id) throws ParseException {
        String query = "select * from todos where id = ?";
        Map<String, Object> todoData = template.queryForMap(query,id);
        logger.info("TODO: {}",todoData);

        Todo todo = new Todo();
        todo.setId((Integer)todoData.get("id"));
        todo.setTitle((String)todoData.get("title"));
        todo.setStatus((String) todoData.get("status"));
        todo.setAddedDate(Helper.parseDate((LocalDateTime) todoData.get("addedDate")));
        todo.setToDoDate(Helper.parseDate((LocalDateTime )todoData.get("todoDate")));

        return todo;
    }

    public List<Todo> getAllTodos2(){
        String query = "select * from todos";
        List<Todo> todos = template.query(query,new TodoRowMapper());
        return todos;
    }

    public List<Todo> getAllTodos(){
        String query = "select * from todos";
        List<Map<String,Object>> maps = template.queryForList(query);

        List<Todo> todos=  maps.stream().map((map) -> {
            Todo todo = new Todo();
            todo.setId((Integer)map.get("id"));
            todo.setTitle((String)map.get("title"));
            todo.setStatus((String) map.get("status"));
            try {
                todo.setAddedDate(Helper.parseDate((LocalDateTime) map.get("addedDate")));
                todo.setToDoDate(Helper.parseDate((LocalDateTime )map.get("todoDate")));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            return todo;
        }).collect(Collectors.toList());
        return todos;
    }

    public Todo updateTodo(int id, Todo newTodo){
        String query = "update todos set title=?,content=?,status=?,addedDate=?,todoDate=?" +
                " where id=?;";
        int update = template.update(query,newTodo.getTitle(),newTodo.getContent(),
                newTodo.getStatus(),newTodo.getAddedDate(),newTodo.getToDoDate(),id);

        logger.info("Update {} ", update);

        newTodo.setId(id);
        return newTodo;
    }

    public void deleteTodo(int id){
        String query = "delete from todos where id=?";
        int deleted =  template.update(query,id);

        logger.info("deleted rows {}",deleted);
    }

    public void deleteMultiple(int ids[]){
        String query = "delete  from todos where id = ?";

        int ints[] = template.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                int id = ids[i];
                ps.setInt(1,id);
            }

            @Override
            public int getBatchSize() {
                return ids.length;
            }
        });

        for(int i: ints){
            logger.info("Deleted {} ",i);
        }
    }

}
