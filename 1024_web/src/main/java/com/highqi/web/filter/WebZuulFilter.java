package com.highqi.web.filter;

import com.highqi.common.contents.CommonContent;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

/**
 * @Author: 陈建春
 * @Date: 2018-12-30 13:23
 * @Description:
 */

@Component
public class WebZuulFilter extends ZuulFilter {

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

        //查看经过网关的请求是否有  AuthorizationToken 头
        String header = requestContext.getRequest().getHeader(CommonContent.AUTHORIZATION_TOKEN);

        if (header != null){
            //向下传递头
            requestContext.addZuulRequestHeader(CommonContent.AUTHORIZATION_TOKEN,header);
        }
        return null;
    }
}
