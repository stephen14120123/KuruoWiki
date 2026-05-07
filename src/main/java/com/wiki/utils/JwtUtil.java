package com.wiki.utils;

import com.wiki.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtUtil {
    // 🚨 服务器私钥（数字印章）
    private static final String SECRET_KEY = "KuruoWiki_Super_Secret_Key";
    // ⏳ Token 有效期：7天
    private static final long EXPIRATION = 7 * 24 * 60 * 60 * 1000L;

    public static String generateToken(User user) {
        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("username", user.getUsername())
                .claim("nickname", user.getNickname())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Claims parseToken(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null; // Token 过期或被篡改
        }
    }
}