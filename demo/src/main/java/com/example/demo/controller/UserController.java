package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * 用户登录接口，返回 JWT Token
     */
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginRequest) {
        // 从数据库查询用户
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(401).body(null); // 用户名不存在
        }

        User user = optionalUser.get();
        // 验证密码
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body(null); // 密码错误
        }

        // 生成 Token 并设置到 User 对象中
        String token = JwtUtil.createToken(user);
        user.setToken(token); // 设置 token

        // 返回用户信息（不包含密码）
        user.setPassword(null);
        return ResponseEntity.ok(user); // 返回包含 token 的用户对象
    }

    /**
     * 普通用户访问页面
     */
    @GetMapping("/view")
    public ResponseEntity<String> viewPage(HttpServletRequest request) {
        String token = extractToken(request);
        if (JwtUtil.hasRole(token, "USER") || JwtUtil.hasRole(token, "ADMIN")) {
            return ResponseEntity.ok("欢迎访问用户页面！");
        }
        return ResponseEntity.status(403).body("权限不足");
    }

    /**
     * 管理员管理页面：删除用户
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        String token = extractToken(request);
        if (JwtUtil.hasRole(token, "ADMIN")) {
            Optional<User> userToDelete = userRepository.findById(id);
            if (userToDelete.isPresent()) {
                userRepository.deleteById(id);
                return ResponseEntity.ok("用户已删除，ID：" + id);
            } else {
                return ResponseEntity.status(404).body("用户未找到，ID：" + id);
            }
        }
        return ResponseEntity.status(403).body("仅管理员可执行此操作");
    }

    /**
     * 管理员管理页面：添加用户
     */
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User newUser, HttpServletRequest request) {
        String token = extractToken(request);
        if (JwtUtil.hasRole(token, "ADMIN")) {
            // 检查用户名是否已存在
            if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
                return ResponseEntity.status(400).body("用户名已存在");
            }
            userRepository.save(newUser);
            return ResponseEntity.ok("用户已添加：" + newUser.getUsername());
        }
        return ResponseEntity.status(403).body("仅管理员可执行此操作");
    }

    /**
     * 提取请求头中的 JWT Token
     */
    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // 去掉 "Bearer " 前缀
        }
        throw new IllegalArgumentException("Token 不存在或格式错误");
    }
}
