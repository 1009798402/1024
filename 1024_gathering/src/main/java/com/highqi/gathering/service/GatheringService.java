package com.highqi.gathering.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import util.IdWorker;

import com.highqi.gathering.dao.GatheringDao;
import com.highqi.gathering.pojo.Gathering;

/**
 * @Author: 陈建春
 * @Date: 2018-12-15 14:58
 * @Description: 服务层
 */
@Service
@Transactional
public class GatheringService {

    @Resource
    private GatheringDao gatheringDao;

    @Resource
    private IdWorker idWorker;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Gathering> findAll() {
        return gatheringDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Gathering> findSearch(Map whereMap, int page, int size) {
        Specification<Gathering> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return gatheringDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Gathering> findSearch(Map whereMap) {
        Specification<Gathering> specification = createSpecification(whereMap);
        return gatheringDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id 活动ID
     * @return
     */
    @Cacheable(value = "gathering", key = "#id")
    public Gathering findById(String id) {
        return gatheringDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param gathering
     */
    public void add(Gathering gathering) {
        gathering.setId(idWorker.nextId() + "");
        gatheringDao.save(gathering);
    }

    /**
     * 修改
     *
     * @param gathering
     */
    @CacheEvict(value = "gathering", key = "#gathering.id")
    public void update(Gathering gathering) {
        gatheringDao.save(gathering);
    }

    /**
     * 删除
     *
     * @param id
     */
    @CacheEvict(value = "gathering", key = "#id")
    public void deleteById(String id) {
        gatheringDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Gathering> createSpecification(Map searchMap) {


        return (Root<Gathering> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // 编号
            if (!StringUtils.isEmpty(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 活动名称
            if (!StringUtils.isEmpty(searchMap.get("name"))) {
                predicateList.add(cb.like(root.get("name").as(String.class), "%" + searchMap.get("name") + "%"));
            }
            // 大会简介
            if (!StringUtils.isEmpty(searchMap.get("summary"))) {
                predicateList.add(cb.like(root.get("summary").as(String.class), "%" + searchMap.get("summary") + "%"));
            }
            // 详细说明
            if (!StringUtils.isEmpty(searchMap.get("detail"))) {
                predicateList.add(cb.like(root.get("detail").as(String.class), "%" + searchMap.get("detail") + "%"));
            }
            // 主办方
            if (!StringUtils.isEmpty(searchMap.get("sponsor"))) {
                predicateList.add(cb.like(root.get("sponsor").as(String.class), "%" + searchMap.get("sponsor") + "%"));
            }
            // 活动图片
            if (!StringUtils.isEmpty(searchMap.get("image"))) {
                predicateList.add(cb.like(root.get("image").as(String.class), "%" + searchMap.get("image") + "%"));
            }
            // 举办地点
            if (!StringUtils.isEmpty(searchMap.get("address"))) {
                predicateList.add(cb.like(root.get("address").as(String.class), "%" + searchMap.get("address") + "%"));
            }
            // 是否可见
            if (!StringUtils.isEmpty(searchMap.get("state"))) {
                predicateList.add(cb.like(root.get("state").as(String.class), "%" + searchMap.get("state") + "%"));
            }
            // 城市
            if (!StringUtils.isEmpty(searchMap.get("city"))) {
                predicateList.add(cb.like(root.get("city").as(String.class), "%" + searchMap.get("city") + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }

}
