package com.train.eureke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekeServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekeServerApplication.class, args);
    }

}
