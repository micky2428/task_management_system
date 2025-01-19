package com.taskmanagement.auth.service;

import java.util.Optional;
import com.taskmanagement.auth.security.AuthResponse;
import com.taskmanagement.dto.LoginRequest;
import com.taskmanagement.dto.RegisterRequest;
import com.taskmanagement.auth.model.User;

// public interface AuthService {
//     AuthResponse register(RegisterRequest register);
//     AuthResponse login(LoginRequest login);  // 更新為返回 AuthResponse 而非 Optional<User>
// }

public interface AuthService {
    AuthResponse register(RegisterRequest register);
    Optional<User> login(LoginRequest login);  
}