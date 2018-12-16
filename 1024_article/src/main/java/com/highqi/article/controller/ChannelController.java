package com.highqi.article.controller;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.highqi.article.pojo.Channel;
import com.highqi.article.service.ChannelService;

import entity.PageResult;
import entity.Result;

import javax.annotation.Resource;

/**
 * @Author: 陈建春
 * @Date: 
 * @Description: Channel操作的Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/channel")
public class ChannelController {

	@Resource
	private ChannelService channelService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping
	public Result findAll(){
		return Result.OK(channelService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping(value = "/{id}")
	public Result findById(@PathVariable String id){
		return Result.OK(channelService.findById(id));
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
		Page<Channel> pageList = channelService.findSearch(searchMap, page, size);
		return Result.OK( new PageResult<>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping(value = "/search")
    public Result findSearch( @RequestBody Map searchMap){
        return Result.OK(channelService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param channel
	 */
	@PostMapping
	public Result add(@RequestBody Channel channel  ){
		channelService.add(channel);
		return Result.OK();
	}
	
	/**
	 * 修改
	 * @param channel
	 */
	@PutMapping(value = "/{id}")
	public Result update(@RequestBody Channel channel, @PathVariable String id ){
		channel.setId(id);
		channelService.update(channel);		
		return Result.OK();
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping(value = "/{id}")
	public Result delete(@PathVariable String id ){
		channelService.deleteById(id);
		return Result.OK();
	}
	
}
