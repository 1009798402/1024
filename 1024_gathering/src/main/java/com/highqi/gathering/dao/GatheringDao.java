package com.highqi.gathering.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.highqi.gathering.pojo.Gathering;

/**
 * @Author: 陈建春
 * @Date: 
 * @Description: Repository数据接口
 */
public interface GatheringDao extends JpaRepository<Gathering,String>,JpaSpecificationExecutor<Gathering>{
	
}
