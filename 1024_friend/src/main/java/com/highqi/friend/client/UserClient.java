package com.highqi.friend.client;

import com.highqi.friend.client.impl.UserClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @Author: 陈建春
 * @Date: 2018-12-28 22:38
 * @Description:
 */

@FeignClient(value = "1024-user",fallback = UserClientImpl.class)
public interface UserClient {

    @PutMapping(value = "/user/{flag}/{userid}/{friendid}")
    void updateUserFollowCountAndFriendFansCount(@PathVariable(value = "flag") int flag,
                                                 @PathVariable(value = "userid") String userid,
                                                 @PathVariable(value = "friendid") String friendid);

}
