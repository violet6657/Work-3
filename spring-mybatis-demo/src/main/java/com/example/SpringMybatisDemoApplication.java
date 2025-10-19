package com.example;

import com.example.service.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringMybatisDemoApplication implements CommandLineRunner {
    @Autowired
    private Tools tool;

    public static void main(String[] args) {
        SpringApplication.run(SpringMybatisDemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        tool.startSystem();
    }
}
