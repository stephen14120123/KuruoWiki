package com.wiki.interceptor;

import com.wiki.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 实验十一：Spring MVC 拦截器
 * 用于检查用户是否登录
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");

        // 如果没登录，拦截请求，返回错误提示
        if (user == null) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print("{\"error\":\"【拦截器生效】您还未登录，请先登录！\"}");
            return false; // false 代表拦截，不放行
        }
        return true; // true 代表放行
    }
}