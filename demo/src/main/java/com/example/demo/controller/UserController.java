package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    // 登录方法
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        // 认证用户
        User authenticatedUser = userService.authenticateUser(user.getUsername(), user.getPassword());
        if (authenticatedUser != null) {
            authenticatedUser.setToken(JwtUtil.createToken(authenticatedUser.getUsername(), authenticatedUser.getRole()));
            return ResponseEntity.ok(authenticatedUser);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
    }

    // 注册方法
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // 防止注册时用户名为 admin
        if ("admin".equals(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户名不能为 admin");
        }

        // 检查用户名是否已存在
        if (userService.isUsernameTaken(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户名已存在");
        }

        // 默认用户角色为 USER
        user.setRole("USER");
        // 保存新用户
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("用户注册成功");
    }
}
