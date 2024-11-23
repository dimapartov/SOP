package com.example.sop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.example.sopcontracts", "com.example.sop"})
public class SopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SopApplication.class, args);
    }

}