package com.highqi.common.contents;

/**
 * @Author: 陈建春
 * @Date: 2018-12-26 21:13
 * @Description:
 */
public interface CommonContent {

    //与前端统一的  请求头的key  value里面有 "Bearer" + "_" + token
    String AUTHORIZATION_TOKEN = "AuthorizationToken";
    String TOKEN_STARTSWITH = "Bearer ";

    //存放request域中的 用户权限的key    value <claims>
    String ADMIN_PERMISSIONS = "admin_permissions";
    String USER_PERMISSIONS = "user_permissions";
}

