package com.highqi.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.highqi.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: 陈建春
 * @Date:
 * @Description: Repository数据接口
 */
public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {

    @Modifying
    @Query(value = "UPDATE tb_article SET thumbup = thumbup + 1  WHERE id = ?", nativeQuery = true)
    void doGood(String articleId);

    @Modifying
    @Query(value = "UPDATE tb_article SET state = 1 WHERE id = ?", nativeQuery = true)
    void examine(String articleId);
}
