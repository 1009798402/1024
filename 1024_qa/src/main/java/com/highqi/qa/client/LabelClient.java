package com.highqi.qa.client;

import com.highqi.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: 陈建春
 * @Date: 2018-12-27 22:41
 * @Description:
 */

@FeignClient("1024-base")
@RequestMapping("/label")
public interface LabelClient {

    @GetMapping("/{labelId}")
    Result getLabel(@PathVariable(value = "labelId") String labelId);

    @GetMapping
    Result getAll();
}
