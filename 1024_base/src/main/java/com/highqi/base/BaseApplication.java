package com.highqi.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @Author: 陈建春
 * @Date: 2018-12-15 14:58
 * @Description:
 */
@SpringBootApplication
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }

    @Bean
    public IdWorker getIdWork() {
        return new IdWorker();
    }
}
