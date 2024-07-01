package com.example.todo.todomanager.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Todo {
    private int id;
    private String title;
    private String content;
    private String status;
    private Date addedDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
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
