package com.highqi.qa.controller;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.highqi.qa.pojo.Problem;
import com.highqi.qa.service.ProblemService;

import entity.PageResult;
import entity.Result;

import javax.annotation.Resource;

/**
 * @Author: 陈建春
 * @Date: 
 * @Description: Problem操作的Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/problem")
public class ProblemController {

	@Resource
	private ProblemService problemService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping
	public Result findAll(){
		return Result.OK(problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		return Result.OK(problemService.findById(id));
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
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return Result.OK( new PageResult<>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping(value = "/search")
    public Result findSearch( @RequestBody Map searchMap){
        return Result.OK(problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param problem
	 */
	@PostMapping
	public Result add(@RequestBody Problem problem  ){
		problemService.add(problem);
		return Result.OK();
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@PutMapping(value = "/{id}")
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return Result.OK();
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping(value = "/{id}")
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return Result.OK();
	}


	/**
	 * 最新问答列表
	 * @param label lavel ID
	 */
	@GetMapping(value = "/newlist/{label}/{page}/{size}")
	public Result getNewList(@PathVariable String label,
							 @PathVariable Integer page,
							 @PathVariable Integer size){
		Page<Problem> newList = problemService.getNewList(label, page, size);
		return Result.OK(new PageResult<>(newList.getTotalElements(),newList.getContent()));
	}

	/**
	 * 热门问答列表
	 * @param label lavel ID
	 */
	@GetMapping(value = "/hotlist/{label}/{page}/{size}")
	public Result getHotList(@PathVariable String label,
							 @PathVariable Integer page,
							 @PathVariable Integer size){
		Page<Problem> hotList = problemService.getHotList(label, page, size);
		return Result.OK(new PageResult<>(hotList.getTotalElements(),hotList.getContent()));
	}

	/**
	 * 等待回答列表
	 * @param label lavel ID
	 */
	@GetMapping(value = "/waitlist/{label}/{page}/{size}")
	public Result getWaitList(@PathVariable String label,
							 @PathVariable Integer page,
							 @PathVariable Integer size){
		Page<Problem> waitList = problemService.getWaitList(label, page, size);
		return Result.OK(new PageResult<>(waitList.getTotalElements(),waitList.getContent()));
	}


}
