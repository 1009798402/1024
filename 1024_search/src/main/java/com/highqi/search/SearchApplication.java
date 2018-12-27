package com.highqi.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import com.highqi.common.util.IdWorker;

/**
 * @Author: 陈建春
 * @Date: 2018-12-19 21:39
 * @Description:
 */
@EnableEurekaClient
@SpringBootApplication
public class SearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class);
    }

    @Bean
    public IdWorker getIdWorker() {
        return new IdWorker();
    }
}
