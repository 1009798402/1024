package com.highqi.search.controller;

import com.highqi.search.pojo.Article;
import com.highqi.search.service.SearchService;
import entity.PageResult;
import entity.Result;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 陈建春
 * @Date: 2018-12-20 20:20
 * @Description:
 */
@CrossOrigin
@RestController
@RequestMapping("/article")
public class SearchController {

    @Resource
    private SearchService searchService;

    /** 存. */
    @PostMapping()
    public Result saveArticle(@RequestBody Article article){
        searchService.save(article);
        return Result.OK();
    }

    /** 关键字搜索. */
    @GetMapping("/search/{key}/{page}/{size}")
    public Result keySearch(@PathVariable String key,
                            @PathVariable int page,
                            @PathVariable int size){

        Page<Article> articles = searchService.keySearch(key, page, size);
        return Result.OK(new PageResult<>(articles.getTotalElements(),articles.getContent()));
    }

    @GetMapping("/{page}/{size}")
    public Result getAll(@PathVariable int page,
                         @PathVariable int size){
        Page<Article> articles = searchService.findAll(page, size);
        return Result.OK(new PageResult<>(articles.getTotalElements(),articles.getContent()));
    }

}
