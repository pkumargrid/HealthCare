package com.healthcare.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class HealthCareSystem {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(HealthCareSystem.class, args);
    }
}
