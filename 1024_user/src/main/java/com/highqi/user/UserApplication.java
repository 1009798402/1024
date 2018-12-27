package com.highqi.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.highqi.common.util.IdWorker;
import com.highqi.common.util.JwtUtil;

@EnableEurekaClient
@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    public IdWorker getIdWork() {
        return new IdWorker();
    }

    @Bean
    public BCryptPasswordEncoder getBcrypt() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil getJwtUtil() {
        return new JwtUtil();
    }

}
