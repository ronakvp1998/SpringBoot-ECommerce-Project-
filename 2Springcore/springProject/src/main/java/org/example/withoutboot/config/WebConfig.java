package org.example.withoutboot.config;

import org.example.withoutboot.web.HomeController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    // declare home controller bean
    @Bean
    public HomeController homeController(){
        return new HomeController();
    }

}
