package com.highqi.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.highqi.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: 陈建春
 * @Date:
 * @Description: Repository数据接口
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

    @Query(value = "SELECT * FROM tb_problem , tb_pl WHERE id = problemid AND labelid = ? ORDER BY createtime DESC", nativeQuery = true)
    Page<Problem> getNewList(String labelid, Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem , tb_pl WHERE id = problemid AND labelid = ? ORDER BY reply DESC", nativeQuery = true)
    Page<Problem> getHotList(String labelid, Pageable pageRequest);

    @Query(value = "SELECT * FROM tb_problem , tb_pl WHERE id = problemid AND labelid = ? AND reply = 0 ORDER BY replytime DESC", nativeQuery = true)
    Page<Problem> getWaitList(String labelid, Pageable pageRequest);
}
