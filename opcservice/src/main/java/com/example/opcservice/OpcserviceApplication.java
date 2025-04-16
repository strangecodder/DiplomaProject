package com.example.opcservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OpcserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpcserviceApplication.class, args);
    }

}
