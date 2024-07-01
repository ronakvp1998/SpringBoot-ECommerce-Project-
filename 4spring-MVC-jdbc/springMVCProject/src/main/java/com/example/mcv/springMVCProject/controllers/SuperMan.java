package com.example.mcv.springMVCProject.controllers;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@RequestMapping
@Controller
public class SuperMan {

    @RequestMapping("/about")
    public String aboutRequestHandler(){
        System.out.println("processing about request");
        return "about";
    }

    @RequestMapping("/service")
    public String serviceRequestHandler(){
        System.out.println("processing service request");
        return "services";
    }
}
