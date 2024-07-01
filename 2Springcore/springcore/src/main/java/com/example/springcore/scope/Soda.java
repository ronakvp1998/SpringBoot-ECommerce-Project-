package com.example.springcore.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Soda {
    public Soda(){
        System.out.println("creating soda");
    }
}
