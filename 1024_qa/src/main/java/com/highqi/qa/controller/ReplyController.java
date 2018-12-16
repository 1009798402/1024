package com.highqi.qa.controller;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.highqi.qa.pojo.Reply;
import com.highqi.qa.service.ReplyService;

import entity.PageResult;
import entity.Result;

import javax.annotation.Resource;

/**
 * @Author: 陈建春
 * @Date: 
 * @Description: Reply操作的Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/reply")
public class ReplyController {

	@Resource
	private ReplyService replyService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping
	public Result findAll(){
		return Result.OK(replyService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		return Result.OK(replyService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@PostMapping(value = "/search/{page}/{size}")
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Reply> pageList = replyService.findSearch(searchMap, page, size);
		return Result.OK( new PageResult<>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping(value = "/search")
    public Result findSearch( @RequestBody Map searchMap){
        return Result.OK(replyService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param reply
	 */
	@PostMapping
	public Result add(@RequestBody Reply reply  ){
		replyService.add(reply);
		return Result.OK();
	}
	
	/**
	 * 修改
	 * @param reply
	 */
	@PutMapping(value = "/{id}")
	public Result update(@RequestBody Reply reply, @PathVariable String id ){
		reply.setId(id);
		replyService.update(reply);		
		return Result.OK();
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping(value = "/{id}")
	public Result delete(@PathVariable String id ){
		replyService.deleteById(id);
		return Result.OK();
	}
	
}
