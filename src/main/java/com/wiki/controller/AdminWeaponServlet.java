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
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/admin/weapon")
public class AdminWeaponServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Map<String, Object> result = new HashMap<>();
        WeaponDao dao = new WeaponDao();

        try {
            if ("add".equals(action)) {
                WeaponInfo w = parseRequest(req);
                boolean success = dao.addWeapon(w);
                if (success) {
                    result.put("code", 200);
                    result.put("message", "武器添加成功");
                } else {
                    result.put("code", 500);
                    result.put("message", "武器添加失败");
                }
            } else if ("update".equals(action)) {
                WeaponInfo w = parseRequest(req);
                String idStr = req.getParameter("id");
                if (idStr != null && !idStr.isEmpty()) {
                    w.setId(Integer.parseInt(idStr));
                }
                boolean success = dao.updateWeapon(w);
                if (success) {
                    result.put("code", 200);
                    result.put("message", "武器修改成功");
                } else {
                    result.put("code", 500);
                    result.put("message", "武器修改失败");
                }
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                boolean success = dao.deleteWeapon(id);
                if (success) {
                    result.put("code", 200);
                    result.put("message", "武器删除成功");
                } else {
                    result.put("code", 500);
                    result.put("message", "武器删除失败");
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

    private WeaponInfo parseRequest(HttpServletRequest req) {
        WeaponInfo w = new WeaponInfo();
        w.setName(req.getParameter("name"));

        String rarityStr = req.getParameter("rarity");
        w.setRarity(rarityStr != null && !rarityStr.isEmpty() ? Integer.parseInt(rarityStr) : 4);

        w.setWeaponType(req.getParameter("weaponType"));

        String baseAtkStr = req.getParameter("baseAtk");
        w.setBaseAtk(baseAtkStr != null && !baseAtkStr.isEmpty() ? Integer.parseInt(baseAtkStr) : 0);

        w.setSubStatType(req.getParameter("subStatType"));
        w.setSubStatValue(req.getParameter("subStatValue"));
        w.setSkillName(req.getParameter("skillName"));
        w.setSkillDesc(req.getParameter("skillDesc"));
        w.setImageUrl(req.getParameter("imageUrl"));

        return w;
    }
}
