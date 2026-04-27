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
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员角色增删改接口
 */
@WebServlet("/api/admin/character")
public class AdminCharacterServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Map<String, Object> result = new HashMap<>();
        CharacterDao dao = new CharacterDao();
        
        try {
            if ("add".equals(action)) {
                CharacterInfo c = parseRequest(req);
                boolean success = dao.addCharacter(c);
                if (success) {
                    result.put("code", 200);
                    result.put("message", "添加成功");
                } else {
                    result.put("code", 500);
                    result.put("message", "添加失败");
                }
            } else if ("update".equals(action)) {
                CharacterInfo c = parseRequest(req);
                String idStr = req.getParameter("id");
                if(idStr != null && !idStr.isEmpty()) {
                    c.setId(Integer.parseInt(idStr));
                }
                boolean success = dao.updateCharacter(c);
                if (success) {
                    result.put("code", 200);
                    result.put("message", "修改成功");
                } else {
                    result.put("code", 500);
                    result.put("message", "修改失败");
                }
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                boolean success = dao.deleteCharacter(id);
                if (success) {
                    result.put("code", 200);
                    result.put("message", "删除成功");
                } else {
                    result.put("code", 500);
                    result.put("message", "删除失败");
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
    
    private CharacterInfo parseRequest(HttpServletRequest req) {
        CharacterInfo c = new CharacterInfo();
        c.setName(req.getParameter("name"));
        
        String rarityStr = req.getParameter("rarity");
        c.setRarity(rarityStr != null && !rarityStr.isEmpty() ? Integer.parseInt(rarityStr) : 4);
        
        c.setElement(req.getParameter("element"));
        c.setWeaponType(req.getParameter("weaponType"));
        c.setDescription(req.getParameter("description"));
        c.setImageUrl(req.getParameter("imageUrl"));
        
        String hpStr = req.getParameter("hp");
        c.setHp(hpStr != null && !hpStr.isEmpty() ? Integer.parseInt(hpStr) : 0);
        
        String atkStr = req.getParameter("atk");
        c.setAtk(atkStr != null && !atkStr.isEmpty() ? Integer.parseInt(atkStr) : 0);
        
        String defStr = req.getParameter("def");
        c.setDef(defStr != null && !defStr.isEmpty() ? Integer.parseInt(defStr) : 0);
        
        String critStr = req.getParameter("crit");
        c.setCrit(critStr != null && !critStr.isEmpty() ? Integer.parseInt(critStr) : 0);
        
        String energyStr = req.getParameter("energy");
        c.setEnergy(energyStr != null && !energyStr.isEmpty() ? Integer.parseInt(energyStr) : 0);
        
        return c;
    }
}
