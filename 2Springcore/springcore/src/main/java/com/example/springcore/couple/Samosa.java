package com.example.springcore.couple;

public class Samosa {

    private String name;

    public Samosa(String name){
        System.out.println(" this is  " + name);
        this.name = name;
    }
    public void eat(){
        System.out.println("wow samosa is very spicy");
        System.out.println(name);
    }
}
