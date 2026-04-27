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
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/admin/echo")
public class AdminEchoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Map<String, Object> result = new HashMap<>();
        EchoDao dao = new EchoDao();

        try {
            if ("add".equals(action)) {
                EchoInfo e = parseRequest(req);
                boolean success = dao.addEcho(e);
                if (success) {
                    result.put("code", 200);
                    result.put("message", "声骸添加成功");
                } else {
                    result.put("code", 500);
                    result.put("message", "声骸添加失败");
                }
            } else if ("update".equals(action)) {
                EchoInfo e = parseRequest(req);
                String idStr = req.getParameter("id");
                if (idStr != null && !idStr.isEmpty()) {
                    e.setId(Integer.parseInt(idStr));
                }
                boolean success = dao.updateEcho(e);
                if (success) {
                    result.put("code", 200);
                    result.put("message", "声骸修改成功");
                } else {
                    result.put("code", 500);
                    result.put("message", "声骸修改失败");
                }
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                boolean success = dao.deleteEcho(id);
                if (success) {
                    result.put("code", 200);
                    result.put("message", "声骸删除成功");
                } else {
                    result.put("code", 500);
                    result.put("message", "声骸删除失败");
                }
            } else {
                result.put("code", 400);
                result.put("message", "未知的 action 参数");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", "系统异常: " + e.getMessage());
        }

        PrintWriter out = resp.getWriter();
        out.print(JSON.toJSONString(result));
        out.flush();
    }

    private EchoInfo parseRequest(HttpServletRequest req) {
        EchoInfo e = new EchoInfo();
        e.setName(req.getParameter("name"));

        String costStr = req.getParameter("cost");
        e.setCost(costStr != null && !costStr.isEmpty() ? Integer.parseInt(costStr) : 1);

        e.setSonataEffect(req.getParameter("sonataEffect"));
        e.setSkillDesc(req.getParameter("skillDesc"));
        e.setImageUrl(req.getParameter("imageUrl"));

        return e;
    }
}
