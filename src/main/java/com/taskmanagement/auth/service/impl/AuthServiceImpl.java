package com.taskmanagement.auth.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.taskmanagement.dto.LoginRequest;
import com.taskmanagement.dto.RegisterRequest;
import com.taskmanagement.exception.ApiException;
import com.taskmanagement.auth.model.User;
import com.taskmanagement.repository.UserRepository;
import com.taskmanagement.auth.security.AuthResponse;
import com.taskmanagement.auth.service.AuthService;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse register(Register register) {
        // 簡化重複的存在檢查邏輯
        validateUserExists(register);

        User user = buildUser(register);
        User savedUser = userRepository.save(user);

        return createAuthResponse(savedUser);
    }

    @Override
    public AuthResponse login(Login login) {
        User user = userRepository.findByUsernameOrEmail(login.getUsername(), login.getUsername())
                .filter(u -> passwordEncoder.matches(login.getPassword(), u.getPassword()))
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));

        return createAuthResponse(user);
    }

    // 用於封裝檢查用戶是否已存在的邏輯
    private void validateUserExists(Register register) {
        if (userRepository.existsByEmail(register.getEmail())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Email has already been taken.");
        }
        if (userRepository.existsByUsername(register.getUsername())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Username has already been taken.");
        }
    }

    // 用於生成 AuthResponse 的統一方法
    private AuthResponse createAuthResponse(User user) {
        return new AuthResponse(user.getId(), user.getUsername(), user.getRole());
    }

    // 用於建構 User 物件，並加密密碼
    private User buildUser(Register register) {
        User user = new User();
        user.setEmail(register.getEmail());
        user.setUsername(register.getUsername());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setRole("USER");
        return user;
    }
}