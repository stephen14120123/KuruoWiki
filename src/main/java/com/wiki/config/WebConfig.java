package com.wiki.config;

import com.wiki.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                // 保护所有需要登录的接口
                .addPathPatterns("/api/strategies/**")
                .addPathPatterns("/api/admin/**")
                // 放行登录和查询接口
                .excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/user/logout")
                .excludePathPatterns("/api/characters")
                .excludePathPatterns("/api/characters/*")
                .excludePathPatterns("/api/weapons")
                .excludePathPatterns("/api/weapons/*")
                .excludePathPatterns("/api/echoes")
                .excludePathPatterns("/api/echoes/*")
                .excludePathPatterns("/api/weapon/list")
                .excludePathPatterns("/api/echo/list");
    }
}
