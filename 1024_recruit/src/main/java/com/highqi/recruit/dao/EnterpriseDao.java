package com.highqi.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.highqi.recruit.pojo.Enterprise;

import java.util.List;

/**
 * @Author: 陈建春
 * @Date: 2018-12-16 12:30:43
 * @Description: Enterprise Repository数据接口
 */
public interface EnterpriseDao extends JpaRepository<Enterprise,String>,JpaSpecificationExecutor<Enterprise>{


    /** isHot条件查询 .*/
	List<Enterprise> findByIshot(String isHot);
}
