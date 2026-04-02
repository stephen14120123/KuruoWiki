package com.wiki.controller;

import com.wiki.dao.StrategyDao;
import com.wiki.entity.StrategyGuide;
import com.wiki.entity.User; // 🚨 记得导入 User 实体类
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; // 🚨 导入 Session
import java.io.IOException;

@WebServlet("/api/strategy/add")
public class AddStrategyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=utf-8");

        // 🚨 核心改动：检查登录状态
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            // 如果没登录，直接返回特定的错误标记
            response.getWriter().print("nologin");
            return;
        }

        String charIdStr = request.getParameter("characterId");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        StrategyGuide guide = new StrategyGuide();
        guide.setCharacterId(Integer.parseInt(charIdStr));
        guide.setTitle(title);
        guide.setContent(content);

        // 🚨 核心改动：不再写死 1，而是使用当前登录用户的 ID
        guide.setUserId(currentUser.getId());

        StrategyDao dao = new StrategyDao();
        boolean success = dao.addStrategy(guide);

        if (success) {
            response.getWriter().print("success");
        } else {
            response.getWriter().print("fail");
        }
    }
}