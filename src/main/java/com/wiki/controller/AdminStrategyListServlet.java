package com.wiki.controller;

import com.alibaba.fastjson.JSON;
import com.wiki.dao.StrategyDao;
import com.wiki.entity.StrategyGuide;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员获取所有攻略列表
 */
@WebServlet("/api/admin/strategies")
public class AdminStrategyListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StrategyDao strategyDao = new StrategyDao();
        List<StrategyGuide> list = strategyDao.getAllStrategies();

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        result.put("data", list);

        PrintWriter out = resp.getWriter();
        out.print(JSON.toJSONString(result));
        out.flush();
    }
}
