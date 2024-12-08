package com.example.demo.util;

import com.example.demo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.UUID;
import java.util.function.Function;
import javax.crypto.SecretKey;

import static io.jsonwebtoken.Jwts.*;

public class JwtUtil {

    // Token 有效期：1 天
    private static final long TOKEN_VALIDITY = 1000 * 60 * 60 * 24; // 24小时
    // 生成安全的签名密钥
    private static final SecretKey SIGNATURE_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 生成 JWT Token
     * @param user 用户对象
     * @return 生成的 JWT Token 字符串
     */
    public static String createToken(User user) {
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("username", user.getUsername())
                .claim("role", user.getRole())
                .setSubject("user-authentication")
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .setId(user.getId().toString())
                .signWith(SIGNATURE_KEY)
                .compact();

        System.out.println("Generated token: " + token); // 输出生成的 token
        return token;
    }

    /**
     * 提取用户信息（用户名）从 JWT Token
     * @param token JWT Token
     * @return 提取的用户名
     */
    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 提取角色信息从 JWT Token
     * @param token JWT Token
     * @return 提取的角色
     */
    public static String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    /**
     * 提取 Token 中的声明（claim）信息
     * @param token JWT Token
     * @param claimsResolver 声明解析器函数
     * @param <T> 声明的类型
     * @return 提取的声明
     */
    private static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 解析 Token，获取所有声明信息
     * @param token JWT Token
     * @return Claims
     */
    private static Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNATURE_KEY) // 使用签名密钥解析
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 检查 Token 是否过期
     * @param token JWT Token
     * @return Token 是否过期
     */
    private static Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * 提取 Token 中的过期时间
     * @param token JWT Token
     * @return 提取的过期时间
     */
    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 验证 JWT Token 是否有效
     * @param token JWT Token
     * @param user 用户对象
     * @return Token 是否有效
     */
    public static Boolean validateToken(String token, User user) {
        final String username = extractUsername(token);
        final String role = extractRole(token);
        return (username != null && username.equals(user.getUsername()) &&
                role != null && role.equals(user.getRole()) && !isTokenExpired(token));
    }


    /**
     * 检查 Token 是否具有指定角色
     * @param token JWT Token
     * @param role 需要检查的角色
     * @return 如果 Token 包含指定角色，则返回 true，否则返回 false
     */
    public static boolean hasRole(String token, String role) {
        try {
            String userRole = extractRole(token);
            return role.equals(userRole);
        } catch (Exception e) {
            return false; // 如果解析失败，返回 false
        }
    }

}