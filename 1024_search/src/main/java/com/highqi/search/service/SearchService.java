package com.highqi.search.service;

import com.highqi.search.dao.SearchDao;
import com.highqi.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.highqi.common.util.IdWorker;

import javax.annotation.Resource;

/**
 * @Author: 陈建春
 * @Date: 2018-12-20 20:18
 * @Description:
 */
@Service
@Transactional
public class SearchService {

    @Resource
    private SearchDao searchDao;
    @Resource
    private IdWorker idWorker;

    public void save(Article article) {
        article.setId(idWorker.nextId() + "");
        searchDao.save(article);
    }

    public Page<Article> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return searchDao.findAll(pageable);
    }

    public void delete(String id) {
        searchDao.deleteById(id);
    }

    public Page<Article> keySearch(String key, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return searchDao.findByTitleLikeOrContentLike(key, key, pageable);
    }
}
