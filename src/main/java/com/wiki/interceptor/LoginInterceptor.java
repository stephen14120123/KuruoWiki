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

        // GET 请求到 /api/strategies 不需要登录（只是查看攻略）
        if ("GET".equalsIgnoreCase(request.getMethod()) && request.getRequestURI().startsWith("/api/strategies")) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Claims claims = JwtUtil.parseToken(token);
            if (claims != null) {
                request.setAttribute("currentUserId", claims.get("userId"));
                request.setAttribute("userRole", claims.get("role")); // 存储用户角色
                return true; // 验证通过，放行
            }
        }

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print("{\"code\": 401, \"message\": \"【终端拦截】验证过期，请重新登录！\", \"data\": null}");
        return false;
    }
}