package com.highqi.spit.dao;

import com.highqi.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: 陈建春
 * @Date: 2018-12-18 20:16
 * @Description:
 */
public interface SpitDao extends MongoRepository<Spit, String> {

    Page<Spit> findByParentid(String parentid, Pageable pageable);
}
