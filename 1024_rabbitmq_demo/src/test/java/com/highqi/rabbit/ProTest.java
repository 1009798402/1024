package com.highqi.rabbit;

import com.highqi.rabbit.customer.contents.RabbitContent;
import com.highqi.rabbit.pojo.Girl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: 陈建春
 * @Date: 2018-12-24 20:24
 * @Description:
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /** 直接发送模式 .*/
    @Test
    public void sendMsg() {

        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("highqi_demo", "直接连接模式");
            log.info("发送：" + i + "次消息");
        }
    }

    /** 分裂模式 .*/
    @Test
    public void sendMsgF() {
        rabbitTemplate.convertAndSend(RabbitContent.EXCHAGE_NAME_FENLIE, "", new Girl(17, "lily", 'c').toString());
    }

    /** 主题模式 .*/
    @Test
    public void sendMsgZ() {
        //  匹配high.#
//        rabbitTemplate.convertAndSend(RabbitContent.EXCHAGE_NAME_TOPIC, "high.a", "Merry Christmas");
        //  匹配high.#  high.qi  #.qi
//        rabbitTemplate.convertAndSend(RabbitContent.EXCHAGE_NAME_TOPIC, "high.qi", "Merry Christmas");
        //  匹配#.qi
//        rabbitTemplate.convertAndSend(RabbitContent.EXCHAGE_NAME_TOPIC, "a.qi", "Merry Christmas");
        //  匹配high.#  #.qi
        rabbitTemplate.convertAndSend(RabbitContent.EXCHAGE_NAME_TOPIC, "high.a.qi", "Merry Christmas");
    }

}
