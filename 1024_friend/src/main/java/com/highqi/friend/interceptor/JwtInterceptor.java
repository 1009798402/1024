package com.highqi.friend.interceptor;

import com.highqi.common.contents.CommonContent;
import com.highqi.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 陈建春
 * @Date: 2018-12-26 22:43
 * @Description:
 */

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    //拦截之前的方法
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("login...");

        final String AUTH_HEADER = request.getHeader(CommonContent.AUTHORIZATION_TOKEN);


        try {
            if (!StringUtils.isEmpty(AUTH_HEADER) && AUTH_HEADER.startsWith(CommonContent.TOKEN_STARTSWITH)) {

                final String TOKEN = AUTH_HEADER.substring(7);

                Claims claims = jwtUtil.parseJWT(TOKEN);

                if (claims != null) {
                    if (claims.get("role").equals("admin")) {
                        request.setAttribute(CommonContent.ADMIN_PERMISSIONS, claims);
                    }
                    if (claims.get("role").equals("user")) {
                        request.setAttribute(CommonContent.USER_PERMISSIONS, claims);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("time out!");
        }


        return true;
    }
}
