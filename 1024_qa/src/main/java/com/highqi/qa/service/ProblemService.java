package com.highqi.qa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import contents.CommonContent;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import util.IdWorker;

import com.highqi.qa.dao.ProblemDao;
import com.highqi.qa.pojo.Problem;

/**
 * @Author: 陈建春
 * @Date: 2018-12-15 14:58
 * @Description: 服务层
 */
@Service
@Transactional
public class ProblemService {

    @Resource
    private ProblemDao problemDao;

    @Resource
    private IdWorker idWorker;

    @Autowired
    private HttpServletRequest request;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Problem> findAll() {
        return problemDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findSearch(Map whereMap, int page, int size) {
        Specification<Problem> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Problem> findSearch(Map whereMap) {
        Specification<Problem> specification = createSpecification(whereMap);
        return problemDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Problem findById(String id) {
        return problemDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param problem
     */
    public void add(Problem problem) {

        Claims claimsUser = (Claims) request.getAttribute(CommonContent.USER_PERMISSIONS);
        Claims claimsAdmin = (Claims) request.getAttribute(CommonContent.ADMIN_PERMISSIONS);

        if (claimsUser != null && claimsUser.get("role").equals("user")) {
            problem.setId(idWorker.nextId() + "");
            problem.setUserid(claimsUser.getId());
            problemDao.save(problem);
        } else if (claimsAdmin != null && claimsAdmin.get("role").equals("admin")) {
            problem.setId(idWorker.nextId() + "");
            problem.setUserid(claimsAdmin.getId());
            problemDao.save(problem);
        } else {
            throw new RuntimeException("time out!");
        }

    }

    /**
     * 修改
     *
     * @param problem
     */
    public void update(Problem problem) {
        problemDao.save(problem);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        problemDao.deleteById(id);
    }


    /**
     * 获取最新的问题
     *
     * @param labelid label Id
     */
    public Page<Problem> getNewList(String labelid, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return problemDao.getNewList(labelid, pageable);
    }

    /**
     * 获取最多回答问题
     *
     * @param labelid label Id
     */
    public Page<Problem> getHotList(String labelid, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return problemDao.getHotList(labelid, pageable);
    }

    /**
     * 获取等待回答问题
     *
     * @param labelid label Id
     */
    public Page<Problem> getWaitList(String labelid, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return problemDao.getWaitList(labelid, pageable);
    }


    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Problem> createSpecification(Map searchMap) {


        return (Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (!StringUtils.isEmpty(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 标题
            if (!StringUtils.isEmpty(searchMap.get("title"))) {
                predicateList.add(cb.like(root.get("title").as(String.class), "%" + searchMap.get("title") + "%"));
            }
            // 内容
            if (!StringUtils.isEmpty(searchMap.get("contents"))) {
                predicateList.add(cb.like(root.get("contents").as(String.class), "%" + searchMap.get("contents") + "%"));
            }
            // 用户ID
            if (!StringUtils.isEmpty(searchMap.get("userid"))) {
                predicateList.add(cb.like(root.get("userid").as(String.class), "%" + searchMap.get("userid") + "%"));
            }
            // 昵称
            if (!StringUtils.isEmpty(searchMap.get("nickname"))) {
                predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + searchMap.get("nickname") + "%"));
            }
            // 是否解决
            if (!StringUtils.isEmpty(searchMap.get("solve"))) {
                predicateList.add(cb.like(root.get("solve").as(String.class), "%" + searchMap.get("solve") + "%"));
            }
            // 回复人昵称
            if (!StringUtils.isEmpty(searchMap.get("replyname"))) {
                predicateList.add(cb.like(root.get("replyname").as(String.class), "%" + searchMap.get("replyname") + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }

}
