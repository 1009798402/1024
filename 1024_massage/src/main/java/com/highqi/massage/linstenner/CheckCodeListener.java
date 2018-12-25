package com.highqi.massage.linstenner;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: 陈建春
 * @Date: 2018-12-25 20:37
 * @Description:
 */
@Component
@RabbitListener(queues = "checkcode")
public class CheckCodeListener {

    @RabbitHandler
    public void getMsg(Map<String,String> map){
        System.out.println("手机号: " + map.get("mobile"));
        System.out.println("验证码: " + map.get("checkcode"));
    }
}
