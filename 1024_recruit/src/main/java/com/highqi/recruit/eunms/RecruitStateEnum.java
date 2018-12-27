package com.highqi.recruit.eunms;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: 陈建春
 * @Date: 2018-12-16 13:54
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum RecruitStateEnum {

    CLOSE("0", "关闭"),
    OPEN("1", "打开"),
    RECOMMEND("2", "推荐");

    private String state;
    private String msg;
}
