package com.highqi.user.constants;

/**
 * @Author: 陈建春
 * @Date: 2018-12-24 22:27
 * @Description:
 */
public interface UserConstant {

    //Redis 存放短信验证码的 前缀
    String REDIS_CODE = "checkcode_";

    //rabbit mq 的队列名
    String RABBIT_QUEUES = "checkcode";

}
