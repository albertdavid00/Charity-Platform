package com.example.softbinatorlabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SoftbinatorLabsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftbinatorLabsApplication.class, args);
    }

}
