package com.example.mcv.springMVCProject.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class FeedbackController {

//    @RequestMapping(value = "/feedbacks",method = RequestMethod.GET)
    @GetMapping("/feedbacks")
    public List<String> getFeedBacks(){
        List<String> feedbacks = Arrays.asList("Good Cource","Nice one","Valuable things");
        return feedbacks;
    }

    @PostMapping("/create-feedback")
    public String createFeedback(){
        System.out.println("Feedback created");
        return "Created feedback";
    }
}
