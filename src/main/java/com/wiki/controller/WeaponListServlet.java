package com.wiki.controller;

import com.alibaba.fastjson.JSON;
import com.wiki.dao.WeaponDao;
import com.wiki.entity.WeaponInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@WebServlet("/api/weapons/list")
public class WeaponListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        WeaponDao dao = new WeaponDao();
        List<WeaponInfo> list = dao.getAllWeapons();

        response.getWriter().print(JSON.toJSONString(list));
    }
}