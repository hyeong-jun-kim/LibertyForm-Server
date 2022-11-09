package com.example.libertyformapiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class LibertyFormApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibertyFormApiServerApplication.class, args);
    }

}
