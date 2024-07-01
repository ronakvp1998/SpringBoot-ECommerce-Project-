package com.security.learning.security_project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    // first end point
    @GetMapping("/route1")
    public String route1(){
        return "This is protected route1";
    }

    // Second protected end point
    @GetMapping("/route2")
    public String route2(){
        return "This is protected route2";
    }

}
