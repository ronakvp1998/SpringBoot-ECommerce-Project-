package com.example.springcore.couple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("person")
public class Person {

    // 3 field injection
    @Autowired
    @Qualifier("dog")
    Animal animal ;
    @Autowired
    Student student;
    @Autowired
    @Qualifier("samosa2")
    Samosa samosa;

    //1 Constructor injection
//    @Autowired
//    public Person(@Qualifier("dog") Animal animal,Student student) {
//        System.out.println("Calling constructor");
//        this.animal = animal;
//        this.student = student;
//    }

    public void playWithAnimal(){
        //using animal
        animal.play();
        student.detail();
        samosa.eat();
    }

    public Animal getAnimal() {
        return animal;
    }

    // 2 Setter injection
//    @Autowired
//    @Qualifier("dog")
    public void setAnimal(Animal animal) {
        System.out.println("Setting animal");
        this.animal = animal;
    }

    public Student getStudent() {
        return student;
    }

    // 2 Setter injection
//    @Autowired
    public void setStudent(Student student) {
        System.out.println("Setting Student");
        this.student = student;
    }
}
