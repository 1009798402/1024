package com.highqi.spit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import com.highqi.common.util.IdWorker;

/**
 * @Author: 陈建春
 * @Date: 2018-12-18 20:13
 * @Description:
 */
@EnableEurekaClient
@SpringBootApplication
public class SpitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpitApplication.class);
    }

    @Bean
    public static IdWorker getIdWorker() {
        return new IdWorker();
    }
}
