package com.example.springcore;

import com.example.springcore.couple.*;
import com.example.springcore.scope.Pepsi;
import com.example.springcore.scope.Soda;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import test.Test;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.springcore","test"})
public class SpringcoreApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpringcoreApplication.class, args);

	}


}
