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
import java.util.List;

@WebServlet("/api/strategy/list")
public class GetStrategyListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");

        int charId = Integer.parseInt(request.getParameter("characterId"));
        StrategyDao dao = new StrategyDao();
        List<StrategyGuide> list = dao.getStrategiesByCharId(charId);

        response.getWriter().print(JSON.toJSONString(list));
    }
}