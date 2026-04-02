package com.wiki.controller;

import com.alibaba.fastjson.JSON;
import com.wiki.dao.EchoDao;
import com.wiki.entity.EchoInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/echo/list")
public class EchoListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        EchoDao dao = new EchoDao();
        List<EchoInfo> list = dao.getAllEchoes();

        response.getWriter().print(JSON.toJSONString(list));
    }
}