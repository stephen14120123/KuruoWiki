package com.wiki.controller;

import com.alibaba.fastjson.JSON;
import com.wiki.dao.StrategyDao;

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
 * 管理员强制删除攻略
 */
@WebServlet("/api/admin/strategy/delete")
public class AdminDeleteStrategyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String strategyIdStr = req.getParameter("strategyId");
        Map<String, Object> result = new HashMap<>();

        try {
            int strategyId = Integer.parseInt(strategyIdStr);
            StrategyDao strategyDao = new StrategyDao();
            // 调用管理员专用的删除方法
            boolean success = strategyDao.adminDeleteStrategy(strategyId);

            if (success) {
                result.put("code", 200);
                result.put("message", "攻略删除成功");
            } else {
                result.put("code", 500);
                result.put("message", "删除失败或攻略不存在");
            }
        } catch (NumberFormatException e) {
            result.put("code", 400);
            result.put("message", "攻略 ID 格式错误");
        }

        PrintWriter out = resp.getWriter();
        out.print(JSON.toJSONString(result));
        out.flush();
    }
}
