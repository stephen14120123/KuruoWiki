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
        // 注册拦截器，并告诉它拦截哪些路径 (这里假设拦截 /api/strategy/add 发布攻略的接口)
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/strategy/add");
    }
}