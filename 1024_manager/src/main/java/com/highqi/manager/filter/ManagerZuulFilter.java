package com.highqi.manager.filter;

import com.highqi.common.contents.CommonContent;
import com.highqi.common.util.JwtUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sun.org.apache.regexp.internal.RE;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 陈建春
 * @Date: 2018-12-30 13:23
 * @Description:
 */

@Component
public class ManagerZuulFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    //指定 路由请求时前：pre  路由请求时后：post   路由请求时：route   路由请求错误：error
    @Override
    public String filterType() {
        return "pre";
    }


    //多个filter的执行优先级
    @Override
    public int filterOrder() {
        return 0;
    }


    //是否开启这个filter
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //filter的内容
    @Override
    public Object run() throws ZuulException {


        RequestContext requestContext = RequestContext.getCurrentContext();

        HttpServletRequest request = requestContext.getRequest();

        //查看经过网关的请求是否有  AuthorizationToken 头
        String header = request.getHeader(CommonContent.AUTHORIZATION_TOKEN);

        //如果是OPTIONS请求和Admin的Login请求不进行拦截
        if (request.getMethod().equals("OPTIONS")){
            return null;
        }

        if (request.getRequestURL().indexOf("/admin/login") > 1){
            return null;
        }



        if ( !StringUtils.isEmpty(header)  && header.startsWith(CommonContent.TOKEN_STARTSWITH)){

            //验证是否是admin角色
            final String TOKEN = header.substring(7);

            try {
                Claims claims = jwtUtil.parseJWT(TOKEN);

                if (claims != null) {
                    if (claims.get("role").equals("admin")) {
                        //向下传递头
                        requestContext.addZuulRequestHeader(CommonContent.AUTHORIZATION_TOKEN,header);
                        return null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                //停止运行
                requestContext.setSendZuulResponse(false);
            }

        }
        //停止运行
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(403);
        requestContext.setResponseBody("权限不足");
        requestContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
