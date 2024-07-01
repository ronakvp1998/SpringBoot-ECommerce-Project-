package com.example.mcv.springMVCProject.controllers;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class AdminController {

    @RequestMapping(value="/login",method = RequestMethod.GET)
    @ResponseBody
    public String showLoginPageHandler(){
        return "login";
    }


    @RequestMapping(value="/users",method=RequestMethod.GET)
    @ResponseBody
    public List<String> getUserData(){
        return Arrays.asList("Ram","Shyam","Chinkku");
    }

    @RequestMapping(value = "/create-user",method = RequestMethod.POST)
    public String createUser(){
        System.out.println("Creating User");
        return "user created !!";
    }
}
