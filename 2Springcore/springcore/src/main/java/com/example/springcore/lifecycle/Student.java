package com.example.springcore.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component("student1")
public class Student {
    public Student(){
        System.out.println("Creating Student");
    }

    @PostConstruct
    public void created(){
        System.out.println("Student bean is created: created()");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("# bye bye student bean: destroy()");
    }
}
