package com.taskmanagement.auth.service;

import com.taskmanagement.auth.security.AuthResponse;
import com.taskmanagement.dto.LoginRequest;
import com.taskmanagement.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest register);
    AuthResponse login(LoginRequest login);  // 更新為返回 AuthResponse 而非 Optional<User>
}