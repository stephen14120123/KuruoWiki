package com.wiki.interceptor;

import com.wiki.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) return true; // 放行预检请求

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Claims claims = JwtUtil.parseToken(token);
            if (claims != null) {
                request.setAttribute("currentUserId", claims.get("userId"));
                return true; // 验证通过，放行
            }
        }

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print("{\"code\": 401, \"message\": \"【终端拦截】验证过期，请重新登录！\", \"data\": null}");
        return false;
    }
}