package com.highqi.user.config;

import com.highqi.user.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author: 陈建春
 * @Date: 2018-12-26 22:45
 * @Description:
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    protected void addInterceptors(InterceptorRegistry registry) {
        //声明拦截器对象
        registry.addInterceptor(jwtInterceptor)
                //拦截的url
                .addPathPatterns("/**")
                //不拦截的路径
                .excludePathPatterns("/**/login/**");
    }
}
