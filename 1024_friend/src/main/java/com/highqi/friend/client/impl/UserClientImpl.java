package com.highqi.friend.client.impl;

import com.highqi.friend.client.UserClient;
import org.springframework.stereotype.Component;

/**
 * @Author: 陈建春
 * @Date: 2018-12-29 21:32
 * @Description:
 */
@Component
public class UserClientImpl implements UserClient {
    @Override
    public void updateUserFollowCountAndFriendFansCount(int flag, String userid, String friendid) {
        System.out.println("熔断器触发");
    }
}
