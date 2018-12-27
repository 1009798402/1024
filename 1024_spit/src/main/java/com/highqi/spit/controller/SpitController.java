package com.highqi.spit.controller;

import com.highqi.spit.pojo.Spit;
import com.highqi.spit.service.SpitService;
import com.highqi.common.entity.PageResult;
import com.highqi.common.entity.Result;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.highqi.common.util.IdWorker;

import javax.annotation.Resource;

/**
 * @Author: 陈建春
 * @Date: 2018-12-18 20:15
 * @Description:
 */

@CrossOrigin
@RestController
@RequestMapping("/spit")
public class SpitController {

    @Resource
    private SpitService spitService;
    @Resource
    private IdWorker idWorker;

    /**
     * findAll .
     **/
    @GetMapping
    public Result findAll() {
        return Result.OK(spitService.findAll());
    }

    /**
     * 根据ID找 .
     */
    @GetMapping("/{spitId}")
    public Result findById(@PathVariable String spitId) {
        return Result.OK(spitService.findById(spitId));
    }

    /**
     * 添加 .
     */
    @PostMapping
    public Result save(@RequestBody Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        spitService.save(spit);
        return Result.OK();
    }

    /**
     * 更新 .
     */
    @PutMapping("/{spitId}")
    public Result save(@PathVariable String spitId,
                       @RequestBody Spit spit) {
        spit.set_id(spitId);
        spitService.save(spit);
        return Result.OK();
    }

    /**
     * 删除 .
     */
    @DeleteMapping("/{spitId}")
    public Result delete(@PathVariable String spitId) {
        spitService.deleteById(spitId);
        return Result.OK();
    }


    /**
     * 父节点ID查询
     *
     * @param parentid 父ID
     */
    @GetMapping("/comment/{parentid}/{page}/{size}")
    public Result findByParentId(@PathVariable String parentid,
                                 @PathVariable Integer page,
                                 @PathVariable Integer size) {

        Page<Spit> spits = spitService.findByParentId(parentid, page, size);
        return Result.OK(new PageResult<>(spits.getTotalElements(), spits.getContent()));
    }


    /**
     * 点赞
     */
    @PutMapping(value = "/thumbup/{id}")
    public Result doGood(@PathVariable String id) {
        //先写死用户标识
        String userID = "test2";
        return spitService.doGood2(id, userID) ? Result.OK() : Result.error("未查询到这条吐槽或者已经点过赞...");
    }


}
