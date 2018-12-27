package com.highqi.recruit.controller;

import java.util.List;
import java.util.Map;

import com.highqi.recruit.eunms.RecruitStateEnum;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.highqi.recruit.pojo.Recruit;
import com.highqi.recruit.service.RecruitService;

import entity.PageResult;
import entity.Result;

import javax.annotation.Resource;

/**
 * @Author: 陈建春
 * @Date: 2018-12-16 12:30:43
 * @Description: 招募相关的Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/recruit")
public class RecruitController {

    @Resource
    private RecruitService recruitService;


    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return Result.OK(recruitService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping(value = "/{id}")
    public Result findById(@PathVariable String id) {
        return Result.OK(recruitService.findById(id));
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
        Page<Recruit> pageList = recruitService.findSearch(searchMap, page, size);
        return Result.OK(new PageResult<Recruit>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping(value = "/search")
    public Result findSearch(@RequestBody Map searchMap) {
        return Result.OK(recruitService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param recruit
     */
    @PostMapping
    public Result add(@RequestBody Recruit recruit) {
        recruitService.add(recruit);
        return Result.OK();
    }

    /**
     * 修改
     *
     * @param recruit
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Recruit recruit, @PathVariable String id) {
        recruit.setId(id);
        recruitService.update(recruit);
        return Result.OK();
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        recruitService.deleteById(id);
        return Result.OK();
    }

    /**
     * 查询推荐职位
     */
    @GetMapping("/search/recommend")
    public Result getRecommendRecruit() {
        return Result.OK(recruitService.findRecriotByState(RecruitStateEnum.RECOMMEND.getState()));
    }

    /**
     * 查询最新职位
     */
    @GetMapping("/search/newlist")
    public Result getNewRecruit() {
        List<Recruit> recruits = recruitService.findRecriotByNotState(RecruitStateEnum.CLOSE.getState());
        //如果没有state != "0" 的职位 返回null
        return recruits.size() == 0 ? Result.OK() : Result.OK(recruits.get(0));
    }

}
