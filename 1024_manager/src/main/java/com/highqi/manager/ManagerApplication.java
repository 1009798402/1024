package com.highqi.manager;

import com.highqi.common.util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @Author: 陈建春
 * @Date: 2018-12-30 12:54
 * @Description:
 */

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ManagerApplication {

    public static void main(String[] args) {

        SpringApplication.run(ManagerApplication.class);
    }

    @Bean
    public JwtUtil getJwtUtil(){
        return new JwtUtil();
    }
}
