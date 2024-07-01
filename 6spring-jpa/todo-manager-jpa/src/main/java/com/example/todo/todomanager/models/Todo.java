package com.example.todo.todomanager.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="jpa_todos")
public class Todo {

    @Id
    private int id;
    @Column(name="todo_title",length = 100)
    private String title;
    @Column(name="todo_content",length = 100)
    private String content;
    @Column(name="todo_status",length = 10)
    private String status;
    @Column(name = "todo_added_date")
    private Date addedDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name="todo_todo_date")
    private Date toDoDate;

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", addedDate=" + addedDate +
                ", toDoDate=" + toDoDate +
                '}';
    }
}
