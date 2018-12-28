package com.highqi.friend.dao;

import com.highqi.friend.pojo.Friend;
import com.highqi.friend.pojo.NotFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: 陈建春
 * @Date: 2018-12-28 21:19
 * @Description:
 */
public interface NotFriendDao extends JpaRepository<NotFriend,String> {

    /** 单向not like. */
    NotFriend getByUseridAndFriendid(String userId, String friendid);

}
