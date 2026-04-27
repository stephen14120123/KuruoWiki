package com.wiki.controller;

import com.alibaba.fastjson.JSON;
import com.wiki.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员删除（封禁）用户
 */
@WebServlet("/api/admin/user/delete")
public class AdminDeleteUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdStr = req.getParameter("userId");
        Map<String, Object> result = new HashMap<>();

        try {
            int userId = Integer.parseInt(userIdStr);
            UserDao userDao = new UserDao();
            boolean success = userDao.deleteUser(userId);

            if (success) {
                result.put("code", 200);
                result.put("message", "用户删除成功");
            } else {
                result.put("code", 500);
                result.put("message", "用户删除失败或用户不存在");
            }
        } catch (NumberFormatException e) {
            result.put("code", 400);
            result.put("message", "用户 ID 格式错误");
        }

        PrintWriter out = resp.getWriter();
        out.print(JSON.toJSONString(result));
        out.flush();
    }
}
