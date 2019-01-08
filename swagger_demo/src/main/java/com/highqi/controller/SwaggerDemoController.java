package com.highqi.controller;

import com.highqi.common.entity.Result;
import com.highqi.pojo.User;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Hello world!
 */
@Api(description = "用户接口")
@RestController
@RequestMapping("/swagger")
public class SwaggerDemoController {

    @ApiOperation(value = "新增用户")
    @PostMapping(value = "/createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result createUser(@RequestBody User user) {
        System.out.println("createUser:::" + user.toString());
        return Result.OK("新增成功.");
    }

    @ApiOperation(value = "修改用户", notes = "修改用户")
    @PostMapping(value = "/updateUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result updateUser(@RequestBody User user) {
        System.out.println("updateUser:::" + user.toString());
        return Result.OK("修改成功.");
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token信息", required = true),
            @ApiImplicitParam(name = "userId", value = "用户标识", required = true , dataType = "String" ,paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "userName", required = false , dataType = "String",paramType = "query" )
    })
    @DeleteMapping(value = "/deleteUser")
    public Result deleteUser(String userId,
                             @RequestParam(required = false , defaultValue = "aaa") String userName,
                             HttpServletRequest request) {
        String token = request.getHeader("token");
        System.out.println("【deleteUser】 : " + userId);
        System.out.println("【deleteUser】 : " + userName);
        System.out.println("【deleteUser】 : " + token);
        return Result.OK("删除成功.");
    }

    @ApiOperation(value = "查询用户", notes = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识", required = true, paramType = "query", dataType = "String")
    })
    @GetMapping(value = "/queryUser")
    public Result queryUser(@RequestParam("userId") String userId) {
        System.out.println("queryUser:::" + userId);
        User user = new User(userId, "张三", "******", "mao2080@sina.com");
        return Result.OK(user);
    }

}