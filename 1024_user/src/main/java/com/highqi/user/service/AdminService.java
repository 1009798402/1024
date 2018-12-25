package com.highqi.user.service;

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

import com.highqi.user.dao.AdminDao;
import com.highqi.user.pojo.Admin;

/**
 * @Author: 陈建春
 * @Date: 2018-12-24 21:39:44
 * @Description: 服务层
 */
@Service
@Transactional
public class AdminService {

	@Resource
	private AdminDao adminDao;
	
	@Resource
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Admin> findAll() {
		return adminDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Admin> findSearch(Map whereMap, int page, int size) {
		Specification<Admin> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return adminDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Admin> findSearch(Map whereMap) {
		Specification<Admin> specification = createSpecification(whereMap);
		return adminDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Admin findById(String id) {
		return adminDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param admin
	 */
	public void add(Admin admin) {
		admin.setId( idWorker.nextId()+"" );
		adminDao.save(admin);
	}

	/**
	 * 修改
	 * @param admin
	 */
	public void update(Admin admin) {
		adminDao.save(admin);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		adminDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Admin> createSpecification(Map searchMap) {


		return (Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
				List<Predicate> predicateList = new ArrayList<>();
                // ID
                if (!StringUtils.isEmpty(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+searchMap.get("id")+"%"));
                }
                // 登陆名称
                if (!StringUtils.isEmpty(searchMap.get("loginname"))) {
                	predicateList.add(cb.like(root.get("loginname").as(String.class), "%"+searchMap.get("loginname")+"%"));
                }
                // 密码
                if (!StringUtils.isEmpty(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+searchMap.get("password")+"%"));
                }
                // 状态
                if (!StringUtils.isEmpty(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+searchMap.get("state")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

		};

	}

}
