package com.highqi.qa.service;

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

import com.highqi.common.util.IdWorker;

import com.highqi.qa.dao.ReplyDao;
import com.highqi.qa.pojo.Reply;

/**
 * @Author: 陈建春
 * @Date: 2018-12-15 14:58
 * @Description: 服务层
 */
@Service
@Transactional
public class ReplyService {

    @Resource
    private ReplyDao replyDao;

    @Resource
    private IdWorker idWorker;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Reply> findAll() {
        return replyDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Reply> findSearch(Map whereMap, int page, int size) {
        Specification<Reply> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return replyDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Reply> findSearch(Map whereMap) {
        Specification<Reply> specification = createSpecification(whereMap);
        return replyDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Reply findById(String id) {
        return replyDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param reply
     */
    public void add(Reply reply) {
        reply.setId(idWorker.nextId() + "");
        replyDao.save(reply);
    }

    /**
     * 修改
     *
     * @param reply
     */
    public void update(Reply reply) {
        replyDao.save(reply);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        replyDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Reply> createSpecification(Map searchMap) {


        return (Root<Reply> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // 编号
            if (!StringUtils.isEmpty(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 问题ID
            if (!StringUtils.isEmpty(searchMap.get("problemid"))) {
                predicateList.add(cb.like(root.get("problemid").as(String.class), "%" + searchMap.get("problemid") + "%"));
            }
            // 回答内容
            if (!StringUtils.isEmpty(searchMap.get("com/highqi/common/contents"))) {
                predicateList.add(cb.like(root.get("com/highqi/common/contents").as(String.class), "%" + searchMap.get("com/highqi/common/contents") + "%"));
            }
            // 回答人ID
            if (!StringUtils.isEmpty(searchMap.get("userid"))) {
                predicateList.add(cb.like(root.get("userid").as(String.class), "%" + searchMap.get("userid") + "%"));
            }
            // 回答人昵称
            if (!StringUtils.isEmpty(searchMap.get("nikename"))) {
                predicateList.add(cb.like(root.get("nikename").as(String.class), "%" + searchMap.get("nikename") + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }

}
