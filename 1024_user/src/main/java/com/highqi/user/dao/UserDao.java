package com.highqi.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.highqi.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: 陈建春
 * @Date: 2018-12-24 21:39:16
 * @Description: Repository数据接口
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    User findByMobile(String mobile);

    @Modifying
    @Query(value = "update tb_user set followcount = followcount + ? where id = ?",nativeQuery = true)
    void updateUserFollowCount(int flag, String userid);

    @Modifying
    @Query(value = "update tb_user set fanscount = fanscount + ? where id = ?",nativeQuery = true)
    void updateFriendFansCount(int flag, String friendid);
}
