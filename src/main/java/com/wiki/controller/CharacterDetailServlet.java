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

@WebServlet("/api/character/detail")
public class CharacterDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");

        // 1. 获取前端传来的 id 参数 (比如 ?id=1)
        String idStr = request.getParameter("id");

        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);

            // 2. 调用 DAO 查询单个角色
            CharacterDao dao = new CharacterDao();
            CharacterInfo character = dao.getCharacterById(id);

            // 3. 将对象转为 JSON 并输出
            if (character != null) {
                response.getWriter().print(JSON.toJSONString(character));
            } else {
                response.getWriter().print("{\"error\": \"未找到该角色\"}");
            }
        }
    }
}