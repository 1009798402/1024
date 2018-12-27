package com.highqi.base.repository;

import com.highqi.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 * @Author: 陈建春
 * @Date: 2018-12-15 16:05
 * @Description: Label的Repository
 */
public interface LabelRepository extends JpaRepository<Label, String>, JpaSpecificationExecutor<Label> {
}
