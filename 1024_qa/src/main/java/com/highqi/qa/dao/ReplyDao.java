package com.highqi.qa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.highqi.qa.pojo.Reply;

/**
 * @Author: 陈建春
 * @Date: 
 * @Description: Repository数据接口
 */
public interface ReplyDao extends JpaRepository<Reply,String>,JpaSpecificationExecutor<Reply>{
	
}
