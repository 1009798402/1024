package com.highqi.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import com.highqi.user.constants.UserConstant;
import com.highqi.common.contents.CommonContent;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.highqi.common.util.IdWorker;

import com.highqi.user.dao.UserDao;
import com.highqi.user.pojo.User;
import com.highqi.common.util.JwtUtil;

/**
 * @Author: 陈建春
 * @Date: 2018-12-24 21:39:51
 * @Description: 服务层
 */
@Service
@Transactional
public class UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private IdWorker idWorker;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<User> findAll() {
        return userDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<User> findSearch(Map whereMap, int page, int size) {
        Specification<User> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<User> findSearch(Map whereMap) {
        Specification<User> specification = createSpecification(whereMap);
        return userDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public User findById(String id) {
        return userDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param user
     */
    public void add(User user) {
        user.setId(idWorker.nextId() + "");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    /**
     * 修改
     *
     * @param user
     */
    public void update(User user) {
        userDao.save(user);
    }

    /**
     * 删除
     *
     * @param id 要删除的用户id
     */
    public void deleteById(String id) {

        Claims claims = (Claims) request.getAttribute(CommonContent.ADMIN_PERMISSIONS);

        if (claims == null) {
            throw new RuntimeException("not enough permissions!");
        }
        if (!claims.get("role").equals("admin")) {
            throw new RuntimeException("not enough permissions!");
        }

        userDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecification(Map searchMap) {


        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (!StringUtils.isEmpty(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 手机号码
            if (!StringUtils.isEmpty(searchMap.get("mobile"))) {
                predicateList.add(cb.like(root.get("mobile").as(String.class), "%" + searchMap.get("mobile") + "%"));
            }
            // 密码
            if (!StringUtils.isEmpty(searchMap.get("password"))) {
                predicateList.add(cb.like(root.get("password").as(String.class), "%" + searchMap.get("password") + "%"));
            }
            // 昵称
            if (!StringUtils.isEmpty(searchMap.get("nickname"))) {
                predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + searchMap.get("nickname") + "%"));
            }
            // 性别
            if (!StringUtils.isEmpty(searchMap.get("sex"))) {
                predicateList.add(cb.like(root.get("sex").as(String.class), "%" + searchMap.get("sex") + "%"));
            }
            // 头像
            if (!StringUtils.isEmpty(searchMap.get("avatar"))) {
                predicateList.add(cb.like(root.get("avatar").as(String.class), "%" + searchMap.get("avatar") + "%"));
            }
            // E-Mail
            if (!StringUtils.isEmpty(searchMap.get("email"))) {
                predicateList.add(cb.like(root.get("email").as(String.class), "%" + searchMap.get("email") + "%"));
            }
            // 兴趣
            if (!StringUtils.isEmpty(searchMap.get("interest"))) {
                predicateList.add(cb.like(root.get("interest").as(String.class), "%" + searchMap.get("interest") + "%"));
            }
            // 个性
            if (!StringUtils.isEmpty(searchMap.get("personality"))) {
                predicateList.add(cb.like(root.get("personality").as(String.class), "%" + searchMap.get("personality") + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }


    public void sendCheckCode(String mobile) {

        //生成6位验证码
        String checkCode = RandomStringUtils.randomNumeric(6);

        Map<String, String> map = new HashMap<>();

        map.put("mobile", mobile);
        map.put("checkcode", checkCode);

        //存入缓存  10分钟
        redisTemplate.opsForValue().set(UserConstant.REDIS_CODE + mobile, checkCode, 10, TimeUnit.MINUTES);

        //发送消息队列
        rabbitTemplate.convertAndSend(UserConstant.RABBIT_QUEUES, map);

        System.out.println("验证码: " + checkCode);
    }

    /**
     * 校验验证码是否正确
     *
     * @param code   验证码
     * @param mobile 手机号
     * @return 校验成功return true  失败 false
     */
    public Boolean checkRegister(String code, String mobile) {

        //从根据手机 从redis取出来
        String codeByRedis = redisTemplate.opsForValue().get(UserConstant.REDIS_CODE + mobile);

        //相等 且不为空 返回true
        return !StringUtils.isEmpty(codeByRedis) && codeByRedis.equals(code);
    }

    public User login(User user) {

        User byMobile = userDao.findByMobile(user.getMobile());

        if (byMobile != null && bCryptPasswordEncoder.matches(user.getPassword(), byMobile.getPassword())) {
            return byMobile;
        }
        return null;
    }
}
