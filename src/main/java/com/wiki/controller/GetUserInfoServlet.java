package com.wiki.controller;

import com.alibaba.fastjson.JSON;
import com.wiki.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/user/info")
public class GetUserInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");

        // 从 Session 拿人
        User user = (User) request.getSession().getAttribute("currentUser");

        if (user != null) {
            response.getWriter().print(JSON.toJSONString(user));
        } else {
            response.getWriter().print("null");
        }
    }
}