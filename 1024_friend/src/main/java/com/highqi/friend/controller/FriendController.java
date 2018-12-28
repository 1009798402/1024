package com.highqi.friend.controller;

import com.highqi.common.contents.CommonContent;
import com.highqi.common.entity.Result;
import com.highqi.common.enums.StatusCodeEnum;
import com.highqi.friend.client.UserClient;
import com.highqi.friend.enums.UpdateFriendTypeEnum;
import com.highqi.friend.service.FriendService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 陈建春
 * @Date: 2018-12-28 20:56
 * @Description: 交友相关的controller
 */
@RestController
@RequestMapping(value = "/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserClient userClient;


    /**
     * 添加好友或者添加非好友
     *
     * @param friendid 需要添加的好友ID
     * @param type     1. 添加好友   2. 添加非好友
     */
    @PutMapping(value = "/like/{friendid}/{type}")
    public Result updateFriend(@PathVariable String friendid,
                               @PathVariable String type) {

        //验证登录
        Claims claims = (Claims) request.getAttribute(CommonContent.USER_PERMISSIONS);

        if (claims == null) {
            return new Result(false, StatusCodeEnum.NOT_LOGIN.getStatusCode(), StatusCodeEnum.NOT_LOGIN.getMsg(), null);
        }

        String userId = claims.getId();

        if (type != null) {

            //如果type=1  是添加好友操作
            if (UpdateFriendTypeEnum.ADD_FRIEND.getStatusCode().equals(type)) {

                int flag = friendService.addFriend(userId, friendid);
                if (flag == 0){
                    return new Result(false, StatusCodeEnum.REPEAT_ADD_FRIEND.getStatusCode(), StatusCodeEnum.REPEAT_ADD_FRIEND.getMsg(), null);
                }
                if (flag == 1){
                    userClient.updateUserFollowCountAndFriendFansCount(1,userId,friendid);
                    return Result.OK();
                }

            }

            //如果type=2  是添加not like操作
            else if (UpdateFriendTypeEnum.ADD_NOT_FRIEND.getStatusCode().equals(type)) {

                int flag = friendService.addNotFriend(userId, friendid);

                if (flag == 0){
                    return new Result(false, StatusCodeEnum.REPEAT_ADD_NOTFRIEND.getStatusCode(), StatusCodeEnum.REPEAT_ADD_NOTFRIEND.getMsg(), null);
                }
                if (flag == 1){
                    return Result.OK();
                }
            }

            return Result.error("return flag error",null);
        } else {
            return new Result(false, StatusCodeEnum.PARAMETER_ERROR.getStatusCode(), StatusCodeEnum.PARAMETER_ERROR.getMsg(), null);
        }

    }


    /**
     * 删除好友关系
     * @param friendid  要删除的好友id
     */
    @DeleteMapping(value = "/{friendid}")
    public Result deleteFriend(@PathVariable String friendid){

        //验证登录
        Claims claims = (Claims) request.getAttribute(CommonContent.USER_PERMISSIONS);

        if (claims == null) {
            return new Result(false, StatusCodeEnum.NOT_LOGIN.getStatusCode(), StatusCodeEnum.NOT_LOGIN.getMsg(), null);
        }

        String userId = claims.getId();

        friendService.deleteFriend(userId,friendid);
        userClient.updateUserFollowCountAndFriendFansCount(-1,userId,friendid);

        return Result.OK();
    }


}
