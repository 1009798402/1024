package com.highqi.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.highqi.user.pojo.User;
import com.highqi.user.service.UserService;

import com.highqi.common.entity.PageResult;
import com.highqi.common.entity.Result;
import com.highqi.common.util.IdWorker;
import com.highqi.common.util.JwtUtil;

import javax.annotation.Resource;

/**
 * @Author: 陈建春
 * @Date: 2018-12-24 21:39:06
 * @Description: User操作的Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return Result.OK(userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping(value = "/{id}")
    public Result findById(@PathVariable String id) {
        return Result.OK(userService.findById(id));
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
        Page<User> pageList = userService.findSearch(searchMap, page, size);
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
        return Result.OK(userService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param user
     */
    @PostMapping
    public Result add(@RequestBody User user) {
        userService.add(user);
        return Result.OK();
    }

    /**
     * 修改
     *
     * @param user
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return Result.OK();
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {

        userService.deleteById(id);
        return Result.OK();
    }


    /**
     * 发送验证码
     *
     * @param mobile 用户手机号
     * @return
     */
    @PostMapping(value = "/sendsms/{mobile}")
    public Result sendCheckCode(@PathVariable String mobile) {
        userService.sendCheckCode(mobile);
        return Result.OK();
    }


    /**
     * 用户注册
     */
    @PostMapping(value = "register/{code}")
    public Result register(@RequestBody User user,
                           @PathVariable String code) {

        if (userService.checkRegister(code, user.getMobile())) {

            Date date = new Date();
            //通过校验  存数据库
            user.setId(idWorker.nextId() + "");
            user.setFollowcount(0);
            user.setFanscount(0);
            user.setRegdate(date);
            user.setUpdatedate(date);
            user.setLastdate(date);
            userService.add(user);

            return Result.OK();
        } else {
            return Result.error("验证码错误");
        }
    }

    /**
     * 用户登录
     */
    @PostMapping(value = "/login")
    public Result login(@RequestBody User user) {

        user = userService.login(user);

        if (user != null) {

            String token = jwtUtil.createJWT(user.getId(), user.getNickname(), "user");

            Map<String, String> result = new HashMap<>();

            result.put("token", token);
            result.put("role", "user");
            return Result.OK(result);
        }
        return Result.loginError();
    }

}
