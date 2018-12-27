package com.highqi.qa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.highqi.common.util.IdWorker;
import com.highqi.common.util.JwtUtil;


//Feign调用服务的2个注解
@EnableFeignClients
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class QaApplication {

    public static void main(String[] args) {
        SpringApplication.run(QaApplication.class, args);
    }

    @Bean
    public IdWorker getIdWork() {
        return new IdWorker();
    }

    @Bean
    public JwtUtil getJwtUtil() {
        return new JwtUtil();
    }
}
