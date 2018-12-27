package com.highqi.recruit.service;

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

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import util.IdWorker;

import com.highqi.recruit.dao.EnterpriseDao;
import com.highqi.recruit.pojo.Enterprise;

/**
 * @Author: 陈建春
 * @Date: 2018-12-15 14:58
 * @Description: 企业相关服务层
 */
@Service
@Transactional
public class EnterpriseService {

    @Resource
    private EnterpriseDao enterpriseDao;

    @Resource
    private IdWorker idWorker;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Enterprise> findAll() {
        return enterpriseDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Enterprise> findSearch(Map whereMap, int page, int size) {
        Specification<Enterprise> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return enterpriseDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Enterprise> findSearch(Map whereMap) {
        Specification<Enterprise> specification = createSpecification(whereMap);
        return enterpriseDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Enterprise findById(String id) {
        return enterpriseDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param enterprise
     */
    public void add(Enterprise enterprise) {
        enterprise.setId(idWorker.nextId() + "");
        enterpriseDao.save(enterprise);
    }

    /**
     * 修改
     *
     * @param enterprise
     */
    public void update(Enterprise enterprise) {
        enterpriseDao.save(enterprise);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        enterpriseDao.deleteById(id);
    }


    /**
     * 查询是否热门企业
     *
     * @param isHot 是否热门 0否 1是
     */
    public List<Enterprise> findByIsHot(String isHot) {
        return enterpriseDao.findByIshot(isHot);
    }


    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Enterprise> createSpecification(Map searchMap) {

        //return 一个匿名内部类
        return (Root<Enterprise> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (!StringUtils.isEmpty(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }

            // 企业名称
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                predicateList.add(cb.like(root.get("name").as(String.class), "%" + searchMap.get("name") + "%"));
            }
            // 企业简介
            if (searchMap.get("summary") != null && !"".equals(searchMap.get("summary"))) {
                predicateList.add(cb.like(root.get("summary").as(String.class), "%" + searchMap.get("summary") + "%"));
            }
            // 企业地址
            if (searchMap.get("address") != null && !"".equals(searchMap.get("address"))) {
                predicateList.add(cb.like(root.get("address").as(String.class), "%" + searchMap.get("address") + "%"));
            }
            // 标签列表
            if (searchMap.get("labels") != null && !"".equals(searchMap.get("labels"))) {
                predicateList.add(cb.like(root.get("labels").as(String.class), "%" + searchMap.get("labels") + "%"));
            }
            // 坐标
            if (searchMap.get("coordinate") != null && !"".equals(searchMap.get("coordinate"))) {
                predicateList.add(cb.like(root.get("coordinate").as(String.class), "%" + searchMap.get("coordinate") + "%"));
            }
            // 是否热门
            if (searchMap.get("ishot") != null && !"".equals(searchMap.get("ishot"))) {
                predicateList.add(cb.like(root.get("ishot").as(String.class), "%" + searchMap.get("ishot") + "%"));
            }
            // LOGO
            if (searchMap.get("logo") != null && !"".equals(searchMap.get("logo"))) {
                predicateList.add(cb.like(root.get("logo").as(String.class), "%" + searchMap.get("logo") + "%"));
            }
            // URL
            if (searchMap.get("url") != null && !"".equals(searchMap.get("url"))) {
                predicateList.add(cb.like(root.get("url").as(String.class), "%" + searchMap.get("url") + "%"));
            }
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };
    }

}
