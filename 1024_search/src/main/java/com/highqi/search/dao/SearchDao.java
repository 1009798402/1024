package com.highqi.search.dao;

import com.highqi.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author: 陈建春
 * @Date: 2018-12-20 20:15
 * @Description:
 */
public interface SearchDao extends ElasticsearchRepository<Article,String> {

    Page<Article> findByTitleLikeOrContentLike(String title, String content, Pageable pageable);

}
