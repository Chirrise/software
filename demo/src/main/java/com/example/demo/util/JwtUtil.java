package com.example.demo.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;

public class JwtUtil {

    // Token 有效期：1 天
    private static final long TOKEN_VALIDITY = 1000 * 60 * 60 * 24;

    // 生成安全的签名密钥
    private static final SecretKey SIGNATURE_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 生成 JWT Token
     *
     * @return 生成的 JWT Token 字符串
     */
    public static String createToken() {
        // 使用 Jwts.builder() 创建 token
        String jwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT") // JWT 类型
                .setHeaderParam("alg", "HS256") // 使用的算法
                .claim("username", "admin") // 自定义声明：用户名
                .claim("role", "admin") // 自定义声明：角色
                .setSubject("admin-test") // 主题
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY)) // 设置过期时间
                .setId(UUID.randomUUID().toString()) // 设置 JWT ID
                .signWith(SIGNATURE_KEY) // 使用生成的安全密钥
                .compact(); // 构建并返回最终的 Token
        return jwtToken;
    }
}
