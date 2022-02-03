package com.mrppak.spring_project01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// @EnableJpaAuditing
@SpringBootApplication
public class SpringProject01Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringProject01Application.class, args);
    }

}
