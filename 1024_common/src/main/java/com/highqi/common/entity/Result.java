package com.highqi.common.entity;

import com.highqi.common.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 陈建春
 * @Date: 2018-12-15 14:13
 * @Description: 返回结果的类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Boolean flag;
    private Integer code;
    private String message;
    private Object data;

    private Result(Boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }


    public static Result OK() {
        return new Result(true, StatusCodeEnum.OK.getStatusCode(), "success");
    }

    public static Result OK(Object data) {
        return new Result(true, StatusCodeEnum.OK.getStatusCode(), "success", data);
    }

    public static Result error() {
        return new Result(false, StatusCodeEnum.ERROR.getStatusCode(), "error");

    }

    public static Result error(String data) {
        return new Result(false, StatusCodeEnum.ERROR.getStatusCode(), "error", data);
    }

    public static Result error(String msg,Object data) {
        return new Result(false, StatusCodeEnum.ERROR.getStatusCode(), msg, data);
    }

    public static Result loginError() {
        return new Result(false, StatusCodeEnum.LOGIN_ERROR.getStatusCode(), "login error");
    }

    public static Result loginError(Object data) {
        return new Result(false, StatusCodeEnum.LOGIN_ERROR.getStatusCode(), "login error", data);
    }


}