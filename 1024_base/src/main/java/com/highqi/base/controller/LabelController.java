package com.highqi.base.controller;

import com.highqi.base.pojo.Label;
import com.highqi.base.service.LabelService;
import com.highqi.common.entity.PageResult;
import com.highqi.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.highqi.common.util.IdWorker;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 陈建春
 * @Date: 2018-12-15 15:11
 * @Description: 用户标签Controller
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/label")
public class LabelController {

    @Resource
    private LabelService labelService;
    @Resource
    private IdWorker idWorker;

    /**
     * 添加标签
     *
     * @param label 需要添加的标签
     * @return Result结果
     */
    @PostMapping
    public Result addLabel(@RequestBody Label label) {

        label.setId(idWorker.nextId() + "");
        labelService.save(label);
        log.info("【增加标签】 label = {}", label);
        return Result.OK();
    }

    /**
     * 获取全部标签
     *
     * @return Result结果
     */
    @GetMapping
    public Result getAll() {

        List<Label> labelList = labelService.getAll();
        log.info("【标签全部列表】 labelList = {}", labelList);
        return Result.OK(labelList);
    }

    /**
     * 获取推荐标签列表
     *
     * @return Result结果
     * <p>
     * todo
     */
    @GetMapping("/toplist")
    public Result getTopList() {

        log.info("【推荐标签列表】 ...");
        return new Result();
    }

    /**
     * 获取有效标签列表
     *
     * @return Result结果
     * <p>
     * todo
     */
    @GetMapping("/list")
    public Result getList() {

        log.info("【有效标签列表】 ...");
        return new Result();
    }

    /**
     * 根据ID查询标签
     *
     * @param labelId 标签id
     * @return Result结果
     */
    @GetMapping("/{labelId}")
    public Result getLabel(@PathVariable String labelId) {

        Label label = labelService.getLabelById(labelId);
        log.info("【ID查询标签】 ID = {} , result = {}", labelId, label);
        return Result.OK(label);
    }

    /**
     * 修改标签
     *
     * @param labelId 标签ID
     * @param label   标签内容
     * @return Result结果
     */
    @PutMapping("/{labelId}")
    public Result editLabel(@PathVariable String labelId,
                            @RequestBody Label label) {

        label.setId(labelId);
        labelService.save(label);
        log.info("【修改标签】 label = {}", label);
        return Result.OK();
    }

    /**
     * 删除标签
     *
     * @param labelId 标签ID
     * @return Result结果
     */
    @DeleteMapping("/{labelId}")
    public Result deleteLabel(@PathVariable String labelId) {

        labelService.deleteLabelById(labelId);
        log.info("【ID删除标签】 ...");
        return Result.OK();
    }

    /**
     * 条件搜索
     *
     * @param page  页码
     * @param size  每页条数
     * @param label 搜索条件
     * @return Result结果
     */
    @PostMapping("/search/{page}/{size}")
    public Result searchLabel(@PathVariable Integer page,
                              @PathVariable Integer size,
                              @RequestBody Label label) {

        Page<Label> labelList = labelService.pageSearchLabel(page, size, label);
        log.info("【分页查询标签】 ...");
        return Result.OK(new PageResult<>(labelList.getTotalElements(), labelList.getContent()));
    }

}
