package com.wiki.controller;

import com.wiki.dao.CharacterDao;
import com.wiki.dao.EchoDao;
import com.wiki.dao.UserDao;
import com.wiki.dao.WeaponDao;
import com.wiki.entity.CharacterInfo;
import com.wiki.entity.EchoInfo;
import com.wiki.entity.User;
import com.wiki.entity.WeaponInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实验三核心任务：使用 Spring MVC 替代传统的 Servlet
 * @RestController 会自动将返回的 Java 对象转换成 JSON 并发送给前端浏览器
 */
@RestController
@RequestMapping("/api") // 统一接口前缀
public class WikiController {

    // ==========================================
    // 1. 图鉴数据查询接口 (替代原来的 CharacterServlet, WeaponListServlet, EchoListServlet)
    // ==========================================

    @GetMapping("/characters")
    public List<CharacterInfo> getCharacters() {
        CharacterDao dao = new CharacterDao();
        return dao.getAllCharacters();
    }

    @GetMapping("/weapon/list")
    public List<WeaponInfo> getWeapons() {
        WeaponDao dao = new WeaponDao();
        return dao.getAllWeapons();
    }

    @GetMapping("/echo/list")
    public List<EchoInfo> getEchoes() {
        EchoDao dao = new EchoDao();
        return dao.getAllEchoes();
    }

    // ==========================================
    // 2. 用户登录状态与权限接口 (替代原来的 GetUserInfoServlet, LoginServlet, LogoutServlet)
    // ==========================================

    @GetMapping("/user/info")
    public Object getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user != null) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", 200);
            result.put("data", user);
            return result;
        }
        // 如果没登录，返回字符串 "null"，兼容前端的判断逻辑
        return "null";
    }

    @PostMapping("/user/login")
    public String login(String username, String password, HttpSession session) {
        UserDao dao = new UserDao();
        User loginUser = dao.login(username, password);

        if (loginUser != null) {
            session.setAttribute("currentUser", loginUser);
            return "success";
        }
        return "fail";
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 清除 Session
        return "success";
    }
}
