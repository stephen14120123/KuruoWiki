package com.wiki.controller;

import com.wiki.dao.StrategyDao;
import com.wiki.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/api/strategy/delete")
public class DeleteStrategyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 第一关：检查是否登录
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.getWriter().print("nologin");
            return;
        }

        // 2. 第二关：执行带有权限校验的删除
        String idStr = request.getParameter("id");
        if (idStr != null) {
            int strategyId = Integer.parseInt(idStr);
            StrategyDao dao = new StrategyDao();

            // 🚨 关键：把要删的帖子ID 和 当前登录人的ID 一起交进去
            boolean success = dao.deleteStrategy(strategyId, currentUser.getId());

            if (success) {
                response.getWriter().print("success"); // 删除了自己的帖子
            } else {
                response.getWriter().print("unauthorized"); // 没删掉（帖子不存在，或者是别人的帖子）
            }
        }
    }
}