package com.highqi.qa.client;

import com.highqi.common.entity.Result;
import com.highqi.qa.client.impl.LabelClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: 陈建春
 * @Date: 2018-12-27 22:41
 * @Description:
 */

@FeignClient(value = "1024-base",fallback = LabelClientImpl.class)
public interface LabelClient {

    @GetMapping("/label/{labelId}")
    Result getLabel(@PathVariable(value = "labelId") String labelId);

    @GetMapping("/label")
    Result getAll();
}
