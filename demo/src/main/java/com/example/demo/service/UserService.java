package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 验证用户登录，并生成 JWT Token
     * @param username 用户名
     * @param password 密码
     * @return 包含 Token 的用户对象，或者 null（登录失败）
     */
    public User authenticateUser(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                // 登录成功，生成 Token
                String token = JwtUtil.createToken(user);
                user.setToken(token); // 设置 Token
                return user;
            }
        }

        return null; // 登录失败
    }

    /**
     * 保存新用户
     * @param user 用户对象
     */
    public void saveUser(User user) {
        userRepository.save(user);
    }

    /**
     * 检查用户名是否已被占用
     * @param username 用户名
     * @return true 如果用户名已存在，否则 false
     */
    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * 验证用户是否具有指定角色
     * @param token JWT Token
     * @param role 目标角色
     * @return true 如果用户角色匹配，否则 false
     */
    public boolean hasRole(String token, String role) {
        return JwtUtil.hasRole(token, role);
    }
}
