package com.highqi.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.highqi.recruit.pojo.Recruit;

import java.util.List;

/**
 * @Author: 陈建春
 * @Date: 2018-12-16 12:30:43
 * @Description: Recruit Repository数据接口
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

    /** 查询满足state条件的职位 .*/
    List<Recruit> findByStateOrderByCreatetimeDesc(String state);

    /** 查询不满足state条件的职位 .*/
    List<Recruit> findByStateNotOrderByCreatetimeDesc(String state);
}
