package com.wiki.filter;

import com.wiki.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 修复版管理员权限过滤器：支持 JWT 校验
 */
@WebFilter("/api/admin/*")
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        res.setContentType("application/json;charset=utf-8");

        // 🚨 改为从 Header 中获取 JWT
        String token = req.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Claims claims = JwtUtil.parseToken(token);

            if (claims != null) {
                // 检查是否是管理员 (role 为 1)
                Integer role = claims.get("role", Integer.class);
                if (role != null && role == 1) {
                    chain.doFilter(request, response);
                    return;
                } else {
                    sendErrorResponse(res, "403", "权限不足：非管理员禁止进入后台");
                    return;
                }
            }
        }

        sendErrorResponse(res, "401", "身份验证失败，请重新登录管理员账号");
    }

    private void sendErrorResponse(HttpServletResponse response, String code, String message) throws IOException {
        PrintWriter out = response.getWriter();
        String json = String.format("{\"code\":%s, \"message\":\"%s\", \"data\":null}", code, message);
        out.print(json);
        out.flush();
    }

    @Override public void init(FilterConfig f) {}
    @Override public void destroy() {}
}