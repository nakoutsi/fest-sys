package com.second.festivalmanagementsystem.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.second.festivalmanagementsystem")
public class festivalmanagementsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(festivalmanagementsystemApplication.class, args);
    }

}
