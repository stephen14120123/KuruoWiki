package com.wiki.controller;

import com.alibaba.fastjson.JSON;
import com.wiki.dao.CharacterDao;
import com.wiki.entity.CharacterInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

// 这个注解非常牛，它定义了浏览器访问这个接口的网址路径
@WebServlet("/api/characters")
public class CharacterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 设置响应的格式为 JSON，并解决中文乱码问题
        response.setContentType("application/json;charset=utf-8");

        // 2. 召唤我们刚才写的 Dao，去数据库里拿角色列表
        CharacterDao dao = new CharacterDao();
        List<CharacterInfo> list = dao.getAllCharacters();

        // 3. 使用 FastJSON 神器，把 Java 的 List 集合瞬间变成前端最爱的 JSON 字符串
        String jsonString = JSON.toJSONString(list);

        // 4. 把数据通过网络推送到浏览器
        PrintWriter out = response.getWriter();
        out.print(jsonString);
        out.flush();
        out.close();
    }
}