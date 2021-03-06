package com.highqi.article.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.highqi.article.pojo.Column;
import com.highqi.article.service.ColumnService;

import com.highqi.common.entity.PageResult;
import com.highqi.common.entity.Result;

import javax.annotation.Resource;

/**
 * @Author: 陈建春
 * @Date:
 * @Description: Column操作的Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/column")
public class ColumnController {

    @Resource
    private ColumnService columnService;


    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return Result.OK(columnService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping(value = "/{id}")
    public Result findById(@PathVariable String id) {
        return Result.OK(columnService.findById(id));
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
        Page<Column> pageList = columnService.findSearch(searchMap, page, size);
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
        return Result.OK(columnService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param column
     */
    @PostMapping
    public Result add(@RequestBody Column column) {
        columnService.add(column);
        return Result.OK();
    }

    /**
     * 修改
     *
     * @param column
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Column column, @PathVariable String id) {
        column.setId(id);
        columnService.update(column);
        return Result.OK();
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        columnService.deleteById(id);
        return Result.OK();
    }

}
