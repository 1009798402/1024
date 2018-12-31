package com.highqi.qa.client.impl;

import com.highqi.common.entity.Result;
import com.highqi.qa.client.LabelClient;
import org.springframework.stereotype.Component;

/**
 * @Author: 陈建春
 * @Date: 2018-12-29 21:10
 * @Description:
 */

@Component
public class LabelClientImpl implements LabelClient {

    @Override
    public Result getLabel(String labelId) {
//        System.out.println("getLabel触发熔断器");
        return Result.error();
    }

    @Override
    public Result getAll() {
//        System.out.println("getAll触发熔断器");
        return Result.error();
    }
}
