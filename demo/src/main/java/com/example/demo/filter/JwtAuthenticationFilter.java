package com.example.demo.filter;

import com.example.demo.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 1. 打印请求信息
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Request Method: " + request.getMethod());
        System.out.println("Authorization Header: " + request.getHeader("Authorization"));

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                // 2. 解析 Token
                System.out.println("Extracting information from token...");
                String username = JwtUtil.extractUsername(token);
                String role = JwtUtil.extractRole(token);

                // 3. 打印解析结果
                System.out.println("Extracted Username: " + username);
                System.out.println("Extracted Role: " + role);

                // 4. 验证用户是否已经设置认证信息
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = User.builder()
                            .username(username)
                            .password("") // 密码为空，因为认证已通过
                            .authorities(role) // 使用角色作为权限
                            .build();

                    // 5. 创建认证对象并存入上下文
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    // 6. 打印认证成功日志
                    System.out.println("User authenticated: " + username + " with role: " + role);
                }
            } catch (Exception e) {
                // 7. 捕获异常并返回 401 错误
                System.out.println("Invalid token: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("无效的 Token");
                return;
            }
        } else {
            // 8. 如果没有 Token 或 Token 格式不正确
            System.out.println("No valid Authorization header found.");
        }

        // 继续过滤链
        chain.doFilter(request, response);
    }
}
