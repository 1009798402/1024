package com.highqi.article.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.highqi.article.pojo.Article;
import com.highqi.article.service.ArticleService;

import com.highqi.common.entity.PageResult;
import com.highqi.common.entity.Result;


/**
 * @Author: 陈建春
 * @Date: 2018-12-16 19:08:12
 * @Description: Article操作的Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return Result.OK(articleService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping(value = "/{id}")
    public Result findById(@PathVariable String id) {
        return Result.OK(articleService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @PostMapping(value = "/search/{page}/{size}")
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Article> pageList = articleService.findSearch(searchMap, page, size);
        return Result.OK(new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping(value = "/search")
    public Result findSearch(@RequestBody Map searchMap) {
        return Result.OK(articleService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param article
     */
    @PostMapping
    public Result add(@RequestBody Article article) {
        articleService.add(article);
        return Result.OK();
    }

    /**
     * 修改
     *
     * @param article
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Article article, @PathVariable String id) {
        article.setId(id);
        articleService.update(article);
        return Result.OK();
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        articleService.deleteById(id);
        return Result.OK();
    }

    /**
     * 点赞
     *
     * @param articleId 文章ID
     */
    @PutMapping(value = "/thumbup/{articleId}")
    public Result doGood(@PathVariable String articleId) {
        articleService.doGood(articleId);
        return Result.OK();
    }

    /**
     * 审核
     *
     * @param articleId 文章ID
     */
    @PutMapping(value = "/examine/{articleId}")
    public Result examine(@PathVariable String articleId) {
        articleService.examine(articleId);
        return Result.OK();
    }
}
