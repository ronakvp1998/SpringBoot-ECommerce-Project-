package org.example.withoutboot.config;

import org.example.withoutboot.beans.CartService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan(basePackages = {"org.example.withoutboot", "another.world"})
@ComponentScan(basePackages = {"org.example.withoutboot"})
        public class AppConfig {

    @Bean("cartService1")
    public CartService cartService(){
        return new CartService();
    }
}
