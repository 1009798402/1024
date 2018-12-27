package com.highqi.article.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.highqi.article.constant.ArticleConstant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.highqi.common.util.IdWorker;

import com.highqi.article.dao.ArticleDao;
import com.highqi.article.pojo.Article;

/**
 * @Author: 陈建春
 * @Date: 2018-12-15 14:58
 * @Description: 服务层
 */
@Service
@Transactional
public class ArticleService {

    @Resource
    private ArticleDao articleDao;

    @Resource
    private IdWorker idWorker;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Article> findAll() {
        return articleDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findSearch(Map whereMap, int page, int size) {
        Specification<Article> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return articleDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Article> findSearch(Map whereMap) {
        Specification<Article> specification = createSpecification(whereMap);
        return articleDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Article findById(String id) {

        //先从缓存中拿
        Article article = (Article) redisTemplate.opsForValue().get(ArticleConstant.ARTICLEPRE + id);

        if (article == null) {
            article = articleDao.findById(id).get();
            //存入缓存
            redisTemplate.opsForValue().set(ArticleConstant.ARTICLEPRE + id, article, 10, TimeUnit.SECONDS);
        }
        return article;
    }

    /**
     * 增加
     *
     * @param article
     */
    public void add(Article article) {
        article.setId(idWorker.nextId() + "");
        articleDao.save(article);
    }

    /**
     * 修改
     *
     * @param article
     */
    public void update(Article article) {
        articleDao.save(article);
        redisTemplate.delete(ArticleConstant.ARTICLEPRE + article.getId());
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        articleDao.deleteById(id);
        redisTemplate.delete(ArticleConstant.ARTICLEPRE + id);
    }


    /**
     * 点赞
     *
     * @param articleId 文章ID
     */
    public void doGood(String articleId) {
        articleDao.doGood(articleId);
    }

    /**
     * 审核
     *
     * @param articleId 文章ID
     */
    public void examine(String articleId) {
        articleDao.examine(articleId);
    }


    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Article> createSpecification(Map searchMap) {


        return (Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (!StringUtils.isEmpty(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + searchMap.get("id") + "%"));
            }
            // 专栏ID
            if (!StringUtils.isEmpty(searchMap.get("columnid"))) {
                predicateList.add(cb.like(root.get("columnid").as(String.class), "%" + searchMap.get("columnid") + "%"));
            }
            // 用户ID
            if (!StringUtils.isEmpty(searchMap.get("userid"))) {
                predicateList.add(cb.like(root.get("userid").as(String.class), "%" + searchMap.get("userid") + "%"));
            }
            // 标题
            if (!StringUtils.isEmpty(searchMap.get("title"))) {
                predicateList.add(cb.like(root.get("title").as(String.class), "%" + searchMap.get("title") + "%"));
            }
            // 文章正文
            if (!StringUtils.isEmpty(searchMap.get("com/highqi/common/contents"))) {
                predicateList.add(cb.like(root.get("com/highqi/common/contents").as(String.class), "%" + searchMap.get("com/highqi/common/contents") + "%"));
            }
            // 文章封面
            if (!StringUtils.isEmpty(searchMap.get("image"))) {
                predicateList.add(cb.like(root.get("image").as(String.class), "%" + searchMap.get("image") + "%"));
            }
            // 是否公开
            if (!StringUtils.isEmpty(searchMap.get("ispublic"))) {
                predicateList.add(cb.like(root.get("ispublic").as(String.class), "%" + searchMap.get("ispublic") + "%"));
            }
            // 是否置顶
            if (!StringUtils.isEmpty(searchMap.get("istop"))) {
                predicateList.add(cb.like(root.get("istop").as(String.class), "%" + searchMap.get("istop") + "%"));
            }
            // 审核状态
            if (!StringUtils.isEmpty(searchMap.get("state"))) {
                predicateList.add(cb.like(root.get("state").as(String.class), "%" + searchMap.get("state") + "%"));
            }
            // 所属频道
            if (!StringUtils.isEmpty(searchMap.get("channelid"))) {
                predicateList.add(cb.like(root.get("channelid").as(String.class), "%" + searchMap.get("channelid") + "%"));
            }
            // URL
            if (!StringUtils.isEmpty(searchMap.get("url"))) {
                predicateList.add(cb.like(root.get("url").as(String.class), "%" + searchMap.get("url") + "%"));
            }
            // 类型
            if (!StringUtils.isEmpty(searchMap.get("type"))) {
                predicateList.add(cb.like(root.get("type").as(String.class), "%" + searchMap.get("type") + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }

}
