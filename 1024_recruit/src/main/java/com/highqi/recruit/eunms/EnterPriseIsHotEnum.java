package com.highqi.recruit.eunms;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: 陈建春
 * @Date: 2018-12-16 13:27
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum EnterPriseIsHotEnum {

    NOT_HOT("0", "非热门"),
    HOT("1", "热门");

    private String isHot;
    private String msg;
}
