package com.highqi.rabbit.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: 陈建春
 * @Date: 2018-12-24 20:30
 * @Description:
 */
@Component
@RabbitListener(queues = "orange")
public class Customer3 {

    @RabbitHandler
    public void getMsg(String msg){
        System.out.println("orange: " + msg);
    }
}
