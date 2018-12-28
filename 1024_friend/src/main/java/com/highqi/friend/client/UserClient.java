package com.highqi.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: 陈建春
 * @Date: 2018-12-28 22:38
 * @Description:
 */

@FeignClient("1024-user")
@RequestMapping("/user")
public interface UserClient {

    @PutMapping(value = "/{flag}/{userid}/{friendid}")
    void updateUserFollowCountAndFriendFansCount(@PathVariable(value = "flag") int flag,
                                                 @PathVariable(value = "userid") String userid,
                                                 @PathVariable(value = "friendid") String friendid);

}
