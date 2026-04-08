package com.split;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.split", "com.adplatform"})
public class SplitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SplitApplication.class, args);
    }

}
