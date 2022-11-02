package com.hackathon.software;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SoftwareApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftwareApplication.class, args);
    }

}
