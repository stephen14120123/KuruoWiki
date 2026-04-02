package com.wiki.controller;

import com.wiki.dao.UserDao;
import com.wiki.entity.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/api/user/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        UserDao dao = new UserDao();
        User loginUser = dao.login(user, pass);

        if (loginUser != null) {
            // 🚨 核心逻辑：登录成功，将用户信息存入 Session
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", loginUser);
            response.getWriter().print("success");
        } else {
            response.getWriter().print("fail");
        }
    }
}