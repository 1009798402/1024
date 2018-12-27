package com.highqi.gathering.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.highqi.gathering.pojo.Gathering;
import com.highqi.gathering.service.GatheringService;

import com.highqi.common.entity.PageResult;
import com.highqi.common.entity.Result;

import javax.annotation.Resource;

/**
 * @Author: 陈建春
 * @Date:
 * @Description: Gathering操作的Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/gathering")
public class GatheringController {

    @Resource
    private GatheringService gatheringService;


    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return Result.OK(gatheringService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping(value = "/{id}")
    public Result findById(@PathVariable String id) {
        return Result.OK(gatheringService.findById(id));
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
        Page<Gathering> pageList = gatheringService.findSearch(searchMap, page, size);
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
        return Result.OK(gatheringService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param gathering
     */
    @PostMapping
    public Result add(@RequestBody Gathering gathering) {
        gatheringService.add(gathering);
        return Result.OK();
    }

    /**
     * 修改
     *
     * @param gathering
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Gathering gathering, @PathVariable String id) {
        gathering.setId(id);
        gatheringService.update(gathering);
        return Result.OK();
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        gatheringService.deleteById(id);
        return Result.OK();
    }

}
