package com.highqi.friend.service;

import com.highqi.friend.dao.FriendDao;
import com.highqi.friend.dao.NotFriendDao;
import com.highqi.friend.pojo.Friend;
import com.highqi.friend.pojo.NotFriend;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: 陈建春
 * @Date: 2018-12-28 21:18
 * @Description:
 */

@Service
@Transactional
public class FriendService {


    @Autowired
    private FriendDao friendDao;
    @Autowired
    private NotFriendDao notFriendDao;


    /**
     * 添加好友
     *
     * @param userId   用户id
     * @param friendid 需要添加好友的id
     * @return 0. 重复添加  1. 添加成功
     */
    public int addFriend(String userId, String friendid) {

        //查询是否已经是单项的好友
        if (friendDao.getByUseridAndFriendid(userId, friendid) != null) {
            return 0;
        } else {
            //添加一条单向like的数据
            Friend friend = new Friend();
            friend.setUserid(userId);
            friend.setFriendid(friendid);
            friend.setIslike("0");
            friendDao.save(friend);
        }

        //添加完判断是否是双向like了
        if (friendDao.getByUseridAndFriendid(friendid, userId) != null) {
            friendDao.bothLike(userId, friendid, friendid, userId);
        }
        return 1;
    }

    /**
     * 添加not like
     *
     * @param userId   用户id
     * @param friendid 需要添加not like 的user id
     * @return 0. 重复添加  1. 添加成功
     */
    public int addNotFriend(String userId, String friendid) {

        //查询是否已经是单项的非好友
        if (notFriendDao.getByUseridAndFriendid(userId, friendid) != null) {
            return 0;
        } else {
            //添加一条单向like的数据
            NotFriend friend = new NotFriend();
            friend.setUserid(userId);
            friend.setFriendid(friendid);
            notFriendDao.save(friend);
            return 1;
        }
    }

    public void deleteFriend(String userId, String friendid) {

        //删除自己对friend的喜欢
        friendDao.deleteFriend(userId,friendid);

        //对方的互相喜欢变成单向喜欢
        friendDao.updateIsLike("0",friendid,userId);
    }
}
