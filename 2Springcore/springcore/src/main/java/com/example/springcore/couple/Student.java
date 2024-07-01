package com.example.springcore.couple;

import org.springframework.stereotype.Component;

@Component
public class Student {
    public Student(){
        System.out.println("Creating stundet");
    }

    public void detail(){
        System.out.println("I am student detail");
    }
}
