package com.wiki.filter;

import com.wiki.entity.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 管理员权限过滤器
 * 拦截所有以 /api/admin/ 开头的请求
 */
@WebFilter("/api/admin/*")
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 过滤器初始化
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        // 设置统一的编码
        req.setCharacterEncoding("UTF-8");
        res.setContentType("application/json;charset=utf-8");
        
        HttpSession session = req.getSession(false);
        
        // 1. 检查是否登录
        if (session == null || session.getAttribute("currentUser") == null) {
            sendErrorResponse(res, "401", "未登录或登录已过期");
            return;
        }

        // 2. 检查是否是管理员
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser.getRole() == null || currentUser.getRole() != 1) {
            sendErrorResponse(res, "403", "权限不足，非管理员禁止访问");
            return;
        }

        // 3. 校验通过，放行请求到具体的 Servlet
        chain.doFilter(request, response);
    }

    /**
     * 辅助方法：返回统一的 JSON 错误信息
     */
    private void sendErrorResponse(HttpServletResponse response, String code, String message) throws IOException {
        PrintWriter out = response.getWriter();
        // 简单拼接 JSON，后续如果引入统一响应类可以使用 FastJSON 转换
        String json = String.format("{\"code\":\"%s\", \"message\":\"%s\", \"data\":null}", code, message);
        out.print(json);
        out.flush();
    }

    @Override
    public void destroy() {
        // 过滤器销毁
    }
}
