package com.sathvik.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class IntegrationServiceDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(IntegrationServiceDemoApplication.class, args);
    }
}