package com.highqi.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.highqi.user.pojo.Admin;
import com.highqi.user.service.AdminService;

import com.highqi.common.entity.PageResult;
import com.highqi.common.entity.Result;
import com.highqi.common.util.JwtUtil;

import javax.annotation.Resource;

/**
 * @Author: 陈建春
 * @Date: 2018-12-24 21:38:55
 * @Description: Admin操作的Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return Result.OK(adminService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping(value = "/{id}")
    public Result findById(@PathVariable String id) {
        return Result.OK(adminService.findById(id));
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
        Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
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
        return Result.OK(adminService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param admin
     */
    @PostMapping
    public Result add(@RequestBody Admin admin) {
        adminService.add(admin);
        return Result.OK();
    }

    /**
     * 修改
     *
     * @param admin
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Admin admin, @PathVariable String id) {
        admin.setId(id);
        adminService.update(admin);
        return Result.OK();
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        adminService.deleteById(id);
        return Result.OK();
    }


    @PostMapping(value = "/login")
    public Result login(@RequestBody Map<String, String> loginMsg) {

        Admin admin = adminService.login(loginMsg);

        //持久化登录
        if (admin != null) {

            String token = jwtUtil.createJWT(admin.getId(), admin.getLoginname(), "admin");

            Map<String, String> result = new HashMap<>();
            result.put("token", token);
            result.put("role", "admin");

            return Result.OK(result);
        }
        return Result.loginError();
    }
}
