package com.highqi.article.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import util.IdWorker;

import com.highqi.article.dao.ColumnDao;
import com.highqi.article.pojo.Column;

/**
 * @Author: 陈建春
 * @Date: 2018-12-15 14:58
 * @Description: 服务层
 */
@Service
@Transactional
public class ColumnService {

    @Resource
    private ColumnDao columnDao;

    @Resource
    private IdWorker idWorker;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Column> findAll() {
        return columnDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Column> findSearch(Map whereMap, int page, int size) {
        Specification<Column> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return columnDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Column> findSearch(Map whereMap) {
        Specification<Column> specification = createSpecification(whereMap);
        return columnDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Column findById(String id) {
        return columnDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param column
     */
    public void add(Column column) {
        column.setId(idWorker.nextId() + "");
        columnDao.save(column);
    }

    /**
     * 修改
     *
     * @param column
     */
    public void update(Column column) {
        columnDao.save(column);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        columnDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Column> createSpecification(Map searchMap) {


        return (Root<Column> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (!StringUtils.isEmpty(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 专栏名称
            if (!StringUtils.isEmpty(searchMap.get("name"))) {
                predicateList.add(cb.like(root.get("name").as(String.class), "%" + searchMap.get("name") + "%"));
            }
            // 专栏简介
            if (!StringUtils.isEmpty(searchMap.get("summary"))) {
                predicateList.add(cb.like(root.get("summary").as(String.class), "%" + searchMap.get("summary") + "%"));
            }
            // 用户ID
            if (!StringUtils.isEmpty(searchMap.get("userid"))) {
                predicateList.add(cb.like(root.get("userid").as(String.class), "%" + searchMap.get("userid") + "%"));
            }
            // 状态
            if (!StringUtils.isEmpty(searchMap.get("state"))) {
                predicateList.add(cb.like(root.get("state").as(String.class), "%" + searchMap.get("state") + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }

}
