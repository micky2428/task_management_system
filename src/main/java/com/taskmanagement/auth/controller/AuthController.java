package com.taskmanagement.auth.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanagement.auth.security.AuthResponse;
import com.taskmanagement.auth.service.AuthService;
import com.taskmanagement.dto.LoginRequest;
import com.taskmanagement.dto.RegisterRequest;

import jakarta.validation.Valid;


@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //將請求的 JSON 資料轉換為 Register 物件，並進行驗證。@Valid 確保 Register 物件中的字段符合驗證約束（例如必填、長度限制等）。
    //調用 AuthService 中的註冊方法，並將 register 物件傳遞給它。這個方法處理用戶註冊的邏輯，並返回 AuthResponse 物件
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest register){
        AuthResponse authResponse = authService.register(register);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest login){
        
        //map 是 Optional 類中的一個函數式方法，作用是對 Optional 中的值進行操作，並返回一個新的 Optional。
        return authService.login(login)
            //如果 login 成功，則將用戶資訊映射為 AuthResponse 物件，包含用戶的 id、username 和 role
            //dependency有無Lombok會影響功能有沒有被獨到
            .map(user -> new AuthResponse(user.getId(), user.getUsername(), List.of(user.getRole())))
            .map(authResponse -> ResponseEntity.ok(authResponse)) // 若登入成功返回 200
            .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()); // 若登入失敗返回 401
    }

}