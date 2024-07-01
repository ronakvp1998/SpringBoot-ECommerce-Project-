package com.example.springcore.couple;

import org.springframework.stereotype.Component;

@Component("dog")
public class Dog implements Animal{

    @Override
    public void play() {
        System.out.println("Dog is playing");
    }
}
