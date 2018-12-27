package com.highqi.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author: 陈建春
 * @Date: 2018-12-24 22:27
 * @Description: 安全配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                //所有Security全注解形式配置的开端  表示说明需要的权限
                .authorizeRequests()

                //需要的权限分两部分： 拦截的路径    访问该路径的权限
                // antMatchers表示拦截的路径  permitAll表示任何权限都可以访问
                .antMatchers("/**").permitAll()

                //anyRequest表示任何的请求  authenticated表示认证后才能访问
                .anyRequest().authenticated()

                //固定写法 表示是csrf拦截失效
                .and().csrf().disable();
    }
}
