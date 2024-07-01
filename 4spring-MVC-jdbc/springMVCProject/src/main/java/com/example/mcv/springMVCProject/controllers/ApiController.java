package com.example.mcv.springMVCProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class ApiController {

    @RequestMapping("/hello")
    @ResponseBody
    public String helloApi(){
        return "Hello, how are you";
    }
}
