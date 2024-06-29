package com.example.restsyncvirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RestSyncVirtualApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestSyncVirtualApplication.class, args);
    }
}
