package com.highqi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: 陈建春
 * @Date: 2018-12-15 14:18
 * @Description: 状态码的枚举类
 */
@Getter
@AllArgsConstructor
public enum StatusCodeEnum {

    /**
     * 成功.
     */
    OK(20000, "成功"),

    /**
     * 失败.
     */
    ERROR(20001, "失败"),

    /**
     * 用户名或密码错误.
     */
    LOGIN_ERROR(20002, "用户名或密码错误"),

    /**
     * 权限不足.
     */
    ACCESS_ERROR(20003, "权限不足"),

    /**
     * 远程调用失败.
     */
    REMOTE_ERROR(20004, "远程调用失败"),

    /**
     * 重复操作.
     */
    REP_ERROR(20004, "重复操作"),

    /**
     * 未登录.
     */
    NOT_LOGIN(20005, "please login!"),

    /**
     * 参数异常.
     */
    PARAMETER_ERROR(20006, "parameter error!"),

    /**
     * 重复添加好友.
     */
    REPEAT_ADD_FRIEND(20007, "repeat add friend!"),

    /**
     * 重复添加非好友.
     */
    REPEAT_ADD_NOTFRIEND(20008, "repeat add notfriend!");

    private Integer statusCode;
    private String msg;
}
