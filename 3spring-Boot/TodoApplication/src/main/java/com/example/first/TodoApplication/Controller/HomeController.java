package com.example.first.TodoApplication.Controller;

import com.example.first.TodoApplication.config.LCWDConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class HomeController {

    @Value("${lcwd.profile.image.path}")
    private String profilePath;

    @Autowired
    private LCWDConfig lcwdConfig;
    @RequestMapping("/todos")
    public List<String> justTest(){
        List<String> todos = Arrays.asList("Learn Java","Learn SpringBoot","Develop project");
        return todos;
    }

    @RequestMapping("/profilePath")
    public String getProfilePath(){
        return profilePath;
    }

    @RequestMapping("/lcwdConfig")
    public LCWDConfig getLcwdConfig(){
        System.out.println(this.lcwdConfig);
        return this.lcwdConfig;
    }
}
