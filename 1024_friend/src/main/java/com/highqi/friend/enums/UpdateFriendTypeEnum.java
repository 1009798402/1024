package com.highqi.friend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: 陈建春
 * @Date: 2018-12-28 21:05
 * @Description:  添加好友或者非好友时候的状态枚举
 */
@Getter
@AllArgsConstructor
public enum UpdateFriendTypeEnum {

    /**
     * 添加好友.
     */
    ADD_FRIEND("1", "添加好友"),

    /**
     * 添加非好友.
     */
    ADD_NOT_FRIEND("2", "添加非好友");

    private String statusCode;
    private String msg;
}
