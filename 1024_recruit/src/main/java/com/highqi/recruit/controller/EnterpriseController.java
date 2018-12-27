package com.highqi.recruit.controller;

import java.util.Map;

import com.highqi.recruit.eunms.EnterPriseIsHotEnum;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.highqi.recruit.pojo.Enterprise;
import com.highqi.recruit.service.EnterpriseService;

import com.highqi.common.entity.PageResult;
import com.highqi.common.entity.Result;

import javax.annotation.Resource;

/**
 * @Author: 陈建春
 * @Date: 2018-12-16 12:29:04
 * @Description: Enterprise操作的Controller
 */
@RestController
@CrossOrigin
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Resource
    private EnterpriseService enterpriseService;


    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return Result.OK(enterpriseService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping(value = "/{id}")
    public Result findById(@PathVariable String id) {
        return Result.OK(enterpriseService.findById(id));
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
        Page<Enterprise> pageList = enterpriseService.findSearch(searchMap, page, size);
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
        return Result.OK(enterpriseService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param enterprise
     */
    @PostMapping
    public Result add(@RequestBody Enterprise enterprise) {
        enterpriseService.add(enterprise);
        return Result.OK();
    }

    /**
     * 修改
     *
     * @param enterprise
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Enterprise enterprise, @PathVariable String id) {
        enterprise.setId(id);
        enterpriseService.update(enterprise);
        return Result.OK();
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        enterpriseService.deleteById(id);
        return Result.OK();
    }

    /**
     * 查询热门企业
     */
    @GetMapping("/search/hotlist")
    public Result getHotEnterPrise() {
        return Result.OK(enterpriseService.findByIsHot(EnterPriseIsHotEnum.HOT.getIsHot()));
    }

}
