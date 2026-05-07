package com.wiki.controller;

import com.wiki.common.Result;
import com.wiki.entity.User;
import com.wiki.service.UserService;
import com.wiki.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(String username, String password) {
        User user = userService.login(username, password);
        if (user != null) {
            String token = JwtUtil.generateToken(user); // 发放 JWT
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("user", user);
            return Result.success(data);
        }
        return Result.error("终端验证拒绝：用户名或密码错误");
    }

    @GetMapping("/info")
    public Result<User> getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Claims claims = JwtUtil.parseToken(token);
            if (claims != null) {
                User user = new User();
                user.setId(claims.get("userId", Integer.class));
                user.setUsername(claims.get("username", String.class));
                user.setNickname(claims.get("nickname", String.class));
                user.setRole(claims.get("role", Integer.class));
                return Result.success(user);
            }
        }
        return Result.error("终端连接已断开，请重新验证");
    }

    @GetMapping("/logout")
    public Result<String> logout() { return Result.success("已断开"); }
}