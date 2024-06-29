package com.example.resttasksrecursive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RestTasksRecursive {

    public static void main(String[] args) {
        SpringApplication.run(RestTasksRecursive.class, args);
    }
}
