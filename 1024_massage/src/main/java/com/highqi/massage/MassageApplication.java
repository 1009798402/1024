package com.highqi.massage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author: 陈建春
 * @Date: 2018-12-25 20:38
 * @Description:
 */
@EnableEurekaClient
@SpringBootApplication
public class MassageApplication {
    public static void main(String[] args) {
        SpringApplication.run(MassageApplication.class);
    }
}
