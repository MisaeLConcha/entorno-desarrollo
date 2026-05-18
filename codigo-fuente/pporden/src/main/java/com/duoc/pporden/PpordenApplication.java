package com.duoc.pporden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PpordenApplication {

    public static void main(String[] args) {
        SpringApplication.run(PpordenApplication.class, args);
    }

}