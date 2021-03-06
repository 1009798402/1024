package com.highqi.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.highqi.article.pojo.Column;

/**
 * @Author: 陈建春
 * @Date:
 * @Description: Repository数据接口
 */
public interface ColumnDao extends JpaRepository<Column, String>, JpaSpecificationExecutor<Column> {

}
