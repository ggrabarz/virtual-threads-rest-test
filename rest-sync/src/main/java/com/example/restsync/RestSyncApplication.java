package com.example.restsync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RestSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestSyncApplication.class, args);
    }
}
