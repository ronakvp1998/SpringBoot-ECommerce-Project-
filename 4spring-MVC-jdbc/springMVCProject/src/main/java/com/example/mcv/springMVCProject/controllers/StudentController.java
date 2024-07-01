package com.example.mcv.springMVCProject.controllers;

import com.example.mcv.springMVCProject.models.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    @PostMapping("/create")
    public String createStudent(@RequestBody Map<String,Object> data) {
        System.out.println(data);
        Object name = data.get("name");
        Object empId = data.get("empId");
        System.out.println("Name " + name);
        System.out.println("EmpId " + empId);
        return "Created";
    }

    @RequestMapping("/creates")
    public String createStudents(@RequestBody Student data) {
        System.out.println(data);
        return "Created";
    }

    @PostMapping("/createsList")
    public List<Student> createStudentsList(@RequestBody List<Student> data) {
        System.out.println(data);
        return data;
    }

    @PostMapping("/createsList2")
    public Map<String,Object> createStudentsList2(@RequestBody List<Student> data) {
        System.out.println(data);

        Map<String,Object> data1 = new HashMap<>();
        data1.put("content",data);
        data1.put("error","Not Error found");
        data1.put("currentDate",new Date());
//        data1.put("SsystemInfo",System.getProperties());
        System.out.println(data1);
        return data1;
    }
    @PostMapping("/createsList3")
    public ResponseEntity<List<Student>> createStudentsList3(@RequestBody List<Student> data) {

//        String s = null;
//        System.out.println(s.length());

        ResponseEntity<List<Student>> responseEntity = new ResponseEntity<>(data, HttpStatus.CREATED );
        return responseEntity;
    }
}
