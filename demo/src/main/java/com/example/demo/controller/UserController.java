package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final String USERNAME = "admin";
    private final String PASSWORD = "123123";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        if (USERNAME.equals(user.getUsername()) && PASSWORD.equals(user.getPassword())) {
            user.setToken(JwtUtil.createToken()); // 创建并设置 token
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
    }
}

