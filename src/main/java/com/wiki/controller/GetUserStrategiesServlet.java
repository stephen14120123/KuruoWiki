package com.wiki.controller;

import com.alibaba.fastjson.JSON;
import com.wiki.dao.StrategyDao;
import com.wiki.entity.StrategyGuide;
import com.wiki.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/user/myStrategies")
public class GetUserStrategiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        // 如果没登录，直接返回未登录标记
        if (currentUser == null) {
            response.getWriter().print("{\"error\": \"nologin\"}");
            return;
        }

        StrategyDao dao = new StrategyDao();
        // 调用我们刚刚写好的 DAO 方法
        List<StrategyGuide> list = dao.getStrategiesByUserId(currentUser.getId());

        response.getWriter().print(JSON.toJSONString(list));
    }
}