package com.highqi.friend.dao;

import com.highqi.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: 陈建春
 * @Date: 2018-12-28 21:19
 * @Description:
 */
public interface FriendDao extends JpaRepository<Friend, String> {

    /**
     * 单向like.
     */
    Friend getByUseridAndFriendid(String userId, String friendid);

    /**
     * 双向like.
     */
    @Modifying
    @Query(value = "update tb_friend set islike = '1' where userid = ? and friendid = ?   or  userid = ? and friendid = ?", nativeQuery = true)
    void bothLike(String userid1, String friendid1, String friendid2, String userid2);

    /**
     * 删除好友.
     */
    @Modifying
    @Query(value = "delete from tb_friend where userid = ? and friendid = ? ", nativeQuery = true)
    void deleteFriend(String userId, String friendid);

    /**
     * 修改islike .
     */
    @Modifying
    @Query(value = "update tb_friend set islike = ? where userid = ? and friendid = ? ", nativeQuery = true)
    void updateIsLike(String islike, String userid, String friendid);

}
