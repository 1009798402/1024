package com.highqi.base.controller;

import entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: 陈建春
 * @Date: 2018-12-15 18:36
 * @Description: 统一异常拦截
 */
@RestControllerAdvice
public class BaseExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result interceptException(Exception e) {
        return Result.error(e.getMessage());
    }
}
