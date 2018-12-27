package com.highqi.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.highqi.user.pojo.Admin;

/**
 * @Author: 陈建春
 * @Date: 2018-12-24 21:39:12
 * @Description: Repository数据接口
 */
public interface AdminDao extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {

    Admin findByLoginname(String loginname);

}
